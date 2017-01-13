package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.CustomerDTO;
import cz.muni.fi.pa165.dto.OrderDTO;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.facade.CustomerFacade;
import cz.muni.fi.pa165.facade.OrderFacade;
import cz.muni.fi.pa165.mvc.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Dominik Gmiterko
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerFacade customerFacade;
    private final OrderFacade orderFacade;

    public CustomerController(CustomerFacade customerFacade, OrderFacade orderFacade) {
        if (customerFacade == null)
            throw new IllegalArgumentException("CustomerFacade is null");

        this.customerFacade = customerFacade;
        this.orderFacade = orderFacade;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) throws FacadeException {
        model.addAttribute("customers", customerFacade.getAll());
        return "customer/list";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) throws FacadeException {
        CustomerDTO customer = customerFacade.getById(id);
        List<OrderDTO> orders = orderFacade.getByCustomer(customer);

        if (customer == null)
            throw new ResourceNotFoundException("Customer not found!");

        model.addAttribute("customer", customer);
        model.addAttribute("orders", orders);
        return "customer/detail";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newCustomer(Model model) throws FacadeException {
        model.addAttribute("customerCreate", new CustomerDTO());
        return "customer/new";
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("customerCreate") CustomerDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) throws FacadeException {
        // in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }

            return "customer/new";
        }

        // create employee
        customerFacade.create(formBean);

        // report success
        redirectAttributes.addFlashAttribute("alert_success", "Customer " + formBean.getId() + " was created");
        return "redirect:" + uriBuilder.path("/customer/list").toUriString();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) throws FacadeException {
        CustomerDTO customer = customerFacade.getById(id);

        if(customer == null) {
            throw new ResourceNotFoundException("Customer not found!");
        }

        customerFacade.delete(customer);
        redirectAttributes.addFlashAttribute("alert_success", "Customer \"" + customer.getId() + "\" was deleted.");
        return "redirect:" + uriBuilder.path("/customer/list").toUriString();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) throws FacadeException {
        CustomerDTO customer = customerFacade.getById(id);

        if(customer == null) {
            throw new ResourceNotFoundException("Customer not found!");
        }

        customerFacade.update(customer);
        redirectAttributes.addFlashAttribute("alert_success", "Customer \"" + customer.getId() + "\" was updated.");
        return "redirect:" + uriBuilder.path("/customer/list").toUriString();
    }
}

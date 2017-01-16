package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.*;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.facade.*;
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
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Denis Richtarik
 * @version 16.12.2016 19:40
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderFacade orderFacade;
    private final DogFacade dogFacade;
    private final ServiceFacade serviceFacade;
    private final EmployeeFacade employeeFacade;
    private final CustomerFacade customerFacade;

    public OrderController(OrderFacade orderFacade,
                           DogFacade dogFacade,
                           ServiceFacade serviceFacade,
                           EmployeeFacade employeeFacade,
                           CustomerFacade customerFacade) {
        if (orderFacade == null)
            throw new IllegalArgumentException("OrderFacade is null");
        if (dogFacade == null)
            throw new IllegalArgumentException("DogFacade is null");
        if (serviceFacade == null)
            throw new IllegalArgumentException("ServiceFacade is null");
        if (employeeFacade == null)
            throw new IllegalArgumentException("EmployeeFacade is null");
        if (customerFacade == null)
            throw new IllegalArgumentException("CustomerFacade is null");

        this.orderFacade = orderFacade;
        this.dogFacade = dogFacade;
        this.serviceFacade = serviceFacade;
        this.employeeFacade = employeeFacade;
        this.customerFacade = customerFacade;
    }

    @RequestMapping(value = "/list/{filter}", method = RequestMethod.GET)
    public String list(Model model, @PathVariable("filter") String filter) throws FacadeException {
        List<OrderDTO> orders;
        switch (filter) {
            case "all":
                orders = orderFacade.getAll();
                break;
            case "done":
                orders = orderFacade.getByState(true);
                break;
            case "waiting":
                orders = orderFacade.getByState(false);
                break;
            default:
                orders = new ArrayList<>();
                model.addAttribute("alert_danger", "Unknown filter " + filter);
        }
        model.addAttribute("orders", orders);
        return "order/list";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) throws FacadeException {
        OrderDTO order = orderFacade.getById(id);
        List<DogDTO> dogs = dogFacade.getAll();
        List<EmployeeDTO> employees = employeeFacade.getAll();
        List<ServiceDTO> services = serviceFacade.getAll();

        if  (order == null)
            throw new ResourceNotFoundException("Order not found!");

        model.addAttribute("order", order);
        model.addAttribute("dogs", dogs);
        model.addAttribute("employees", employees);
        model.addAttribute("services", services);
        return "order/detail";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newOrderPage(Model model, Principal principal) throws FacadeException{
        CustomerDTO customerDTO = customerFacade.getByUsername(principal.getName());

        model.addAttribute("orderCreate", new OrderDTO());
        model.addAttribute("dogs", customerDTO.getDogs());
        model.addAttribute("services", serviceFacade.getAll());
        model.addAttribute("employees", employeeFacade.getAll());

        return "order/new";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) throws FacadeException {
        OrderDTO order = orderFacade.getById(id);

        if(order == null) {
            throw new ResourceNotFoundException("Order not found!");
        }

        orderFacade.delete(order);
        redirectAttributes.addFlashAttribute("alert_success", "Order \"" + order.getId() + "\" was deleted.");
        return "redirect:" + uriBuilder.path("/order/list").toUriString();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("orderCreate") OrderDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) throws FacadeException {
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }
            return "order/new";
        }
        //create product
        orderFacade.create(formBean);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Order " + formBean.getId() + " was created");
        return "redirect:" + uriBuilder.path("/order/list").toUriString();
    }

    @RequestMapping(value = "/markDone/{id}", method = RequestMethod.POST)
    public String finish(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) throws FacadeException {
        try {
            OrderDTO order = orderFacade.getById(id);
            orderFacade.orderCompleted(order);
            redirectAttributes.addFlashAttribute("alert_success", "Order number "+id+" was marked done.");
        } catch (FacadeException ex) {
            redirectAttributes.addFlashAttribute("alert_danger", "Order number "+id+" was not marked as done. "+ex.getMessage());
        }
        return "redirect:" + uriBuilder.path("/order/detail/{id}").buildAndExpand(id).encode().toUriString();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable long id, Model model) throws FacadeException {
        model.addAttribute("order", orderFacade.getById(id));

        return "order/update";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) throws FacadeException {
        OrderDTO order = orderFacade.getById(id);

        if(order == null) {
            throw new ResourceNotFoundException("Order not found!");
        }

        orderFacade.update(order);
        redirectAttributes.addFlashAttribute("alert_success", "Order " + order.getId() + " was updated.");
        return "redirect:" + uriBuilder.path("/order/list").toUriString();
    }
}

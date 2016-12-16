package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.OrderDTO;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.facade.OrderFacade;
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

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Denis Richtarik
 * @version 16.12.2016 19:40
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Inject
    private OrderFacade orderFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, @PathVariable String filter) throws FacadeException {
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
        model.addAttribute("order", orderFacade.getById(id));
        return "order/detail";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newOrder(Model model) throws FacadeException{
        model.addAttribute("dogCreate", new OrderDTO());
        return "order/new";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) throws FacadeException {
        OrderDTO orderDTO = orderFacade.getById(id);
        orderFacade.delete(orderDTO);
        redirectAttributes.addFlashAttribute("alert_success", "Order \"" + orderDTO.getId() + "\" was deleted.");
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
}

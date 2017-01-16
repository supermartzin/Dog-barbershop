package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.CustomerDTO;
import cz.muni.fi.pa165.dto.DogDTO;
import cz.muni.fi.pa165.dto.OrderDTO;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.facade.CustomerFacade;
import cz.muni.fi.pa165.facade.DogFacade;
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
 * @author Denis Richtarik
 * @version 16.12.2016 19:40
 */
@Controller
@RequestMapping("/dog")
public class DogController {

    private final DogFacade dogFacade;
    private final CustomerFacade customerFacade;
    private final OrderFacade orderFacade;

    public DogController(DogFacade dogFacade, CustomerFacade customerFacade, OrderFacade orderFacade) {
        if (dogFacade == null)
            throw new IllegalArgumentException("DogFacade is null");

        this.dogFacade = dogFacade;
        this.customerFacade = customerFacade;
        this.orderFacade = orderFacade;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) throws FacadeException {
        model.addAttribute("dogs", dogFacade.getAll());
        return "dog/list";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newDog(Model model) throws FacadeException{
        model.addAttribute("dogCreate", new DogDTO());
        return "dog/new";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) throws FacadeException {
        DogDTO dog = dogFacade.getById(id);
        List<OrderDTO> orders = orderFacade.getByDog(dog);

        if(dog == null) {
            throw new ResourceNotFoundException("Dog not found!");
        }

        model.addAttribute("dog", dog);
        model.addAttribute("orders", orders);
        return "dog/detail";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) throws FacadeException {
        DogDTO dog = dogFacade.getById(id);

        if (dog == null)
            throw new ResourceNotFoundException("Dog not found!");

        // delete dog
        dogFacade.delete(dog);

        // return result
        redirectAttributes.addFlashAttribute("alert_success", "Dog " + dog.getName() + " was deleted.");
        return "redirect:" + uriBuilder.path("/dog/list").toUriString();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("dogCreate") DogDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) throws FacadeException {
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }
            return "dog/new";
        }
        //create product
        dogFacade.create(formBean);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Dog " + formBean.getId() + " was created");
        return "redirect:" + uriBuilder.path("/dog/list").toUriString();
    }

    @ModelAttribute("customers")
    public List<CustomerDTO> customers() throws FacadeException {
        return customerFacade.getAll();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) throws FacadeException {
        DogDTO dog = dogFacade.getById(id);

        if(dog == null) {
            throw new ResourceNotFoundException("Dog not found!");
        }

        dogFacade.update(dog);
        redirectAttributes.addFlashAttribute("alert_success", "Dog " + dog.getId() + " was updated.");
        return "redirect:" + uriBuilder.path("/dog/list").toUriString();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(Model model, @PathVariable long id) throws FacadeException {
        model.addAttribute("dogUpdate", dogFacade.getById(id));
        return "dog/update";
    }
}

package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.facade.CustomerFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/**
 * @author Dominik Gmiterko
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Inject
    private CustomerFacade customerFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) throws FacadeException {
        model.addAttribute("customers", customerFacade.getAll());
        return "customer/list";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) throws FacadeException {
        model.addAttribute("customer", customerFacade.getById(id));
        return "customer/detail";
    }
}

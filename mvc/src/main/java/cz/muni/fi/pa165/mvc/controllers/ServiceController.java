package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.facade.ServiceFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Dominik Gmiterko
 */
@Controller
@RequestMapping("/services")
public class ServiceController {

    private final ServiceFacade serviceFacade;

    public ServiceController(ServiceFacade serviceFacade) {
        if (serviceFacade == null)
            throw new IllegalArgumentException("ServiceFacade is null");

        this.serviceFacade = serviceFacade;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) throws FacadeException {
        model.addAttribute("services", serviceFacade.getAll());
        return "service/list";
    }
}

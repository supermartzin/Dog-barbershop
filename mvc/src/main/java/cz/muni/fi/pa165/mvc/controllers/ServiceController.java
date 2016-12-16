package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.exceptions.DAOException;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.facade.CustomerFacade;
import cz.muni.fi.pa165.facade.ServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Dominik Gmiterko
 */
@Controller
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private ServiceFacade serviceFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) throws FacadeException {
        model.addAttribute("services", serviceFacade.getAll());
        return "service/list";
    }
}

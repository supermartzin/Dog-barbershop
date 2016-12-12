package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.exceptions.DAOException;
import cz.muni.fi.pa165.facade.CustomerFacade;
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
@RequestMapping("/customer")
public class CustomerController {

    final static Logger log = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerFacade customerFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        try {
            model.addAttribute("customers", customerFacade.getAll());
        } catch (DAOException e) {
            log.warn("Problem in customerFacade", e);
        }
        return "customer/list";
    }
}

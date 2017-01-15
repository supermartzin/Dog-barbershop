package cz.muni.fi.pa165.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for logging users into the system
 *
 * @author Martin Vrábel
 * @version 13.01.2017 12:49
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping(method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }
}

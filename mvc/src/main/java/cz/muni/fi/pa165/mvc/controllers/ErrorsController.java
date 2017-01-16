package cz.muni.fi.pa165.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for displaying custom error views of errors
 * that cannot be catched by standard {@link ControllerAdvice} mechanism.
 *
 * @author Martin Vrábel
 * @version 16.01.2017 15:44
 */
@Controller
public class ErrorsController {

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accesssDenied() {
        return "errors/403";
    }
}

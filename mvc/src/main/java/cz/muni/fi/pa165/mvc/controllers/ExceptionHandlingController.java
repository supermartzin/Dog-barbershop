package cz.muni.fi.pa165.mvc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller for handling exceptions thrown within the system
 * and processing them to return correspnding error .jsp view page.
 *
 * @author Martin Vrábel
 * @version 10.01.2017 23:44
 */
@ControllerAdvice
public class ExceptionHandlingController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView noHandlerFoundException(HttpServletRequest request) {
        String errorURL = request.getRequestURL().toString();

        LOGGER.error("Resource not found -> " + errorURL);

        ModelAndView mav = new ModelAndView("errors/404");
        mav.addObject("url", errorURL);

        return mav;
    }
}

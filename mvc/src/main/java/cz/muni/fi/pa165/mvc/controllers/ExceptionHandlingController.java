package cz.muni.fi.pa165.mvc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    public String noHandlerFoundException(HttpServletRequest request) {
        String errorURL = request.getRequestURL().toString();

        LOGGER.error("Resource not found -> " + errorURL);

        return "errors/404";
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public String httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        String method = exception.getMethod();

        LOGGER.error("Request method not supported -> " + method);

        return "errors/405";
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String internalException(Exception exception) {
        LOGGER.error("Error processing request -> " + exception.getMessage(), exception);

        return "errors/500";
    }
}

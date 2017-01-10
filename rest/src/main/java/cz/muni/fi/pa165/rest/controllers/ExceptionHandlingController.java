package cz.muni.fi.pa165.rest.controllers;

import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.rest.exceptions.EntityAlreadyExistsException;
import cz.muni.fi.pa165.rest.exceptions.EntityNotFoundException;
import cz.muni.fi.pa165.rest.models.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller for handling exceptions thrown from underlaying layers
 * and processing them to JSON response format.
 *
 * @author Martin Vrábel
 * @version 09.01.2017 23:19
 */
@ControllerAdvice
@RestController
public class ExceptionHandlingController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @ExceptionHandler(value = FacadeException.class)
    public Response<Object> facadeException(FacadeException exception) {
        LOGGER.error("Error occured on Facade layer.", exception);

        return new Response<>(null, "error processing request: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public Response<Object> illegalArgumentException(IllegalArgumentException exception) {
        LOGGER.error("Error processing request: " + exception.getMessage(), exception);

        return new Response<>(null, "error processing request: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    public Response<Object> noHandlerFoundException(HttpServletRequest request) {
        String errorURL = request.getRequestURL().toString();

        LOGGER.error("Resource not found -> " + errorURL);

        return new Response<>(null, "resource not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = EntityAlreadyExistsException.class)
    public Response<Object> entityAlreadyExistsException(EntityAlreadyExistsException exception) {
        LOGGER.error("Entity '" + exception.getEntityName() + "' already exists", exception);

        return new Response<>(null, "entity '" + exception.getEntityName() + "' already exists", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public Response<Object> entityNotFoundException(EntityNotFoundException exception) {
        LOGGER.error("Entity '" + exception.getEntityName() + "' has not been found", exception);

        return new Response<>(null, "entity '" + exception.getEntityName() + "' not found", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public Response<Object> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        String method = exception.getMethod();

        LOGGER.error("Request method not supported -> " + method);

        return new Response<>(null, "method '" + method + "' not supported", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public Response<Object> httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        LOGGER.error("Incorrect format of received data", exception);

        return new Response<>(null, "incorrect format of data", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public Response<Object> unhandledException(Exception exception) {
        LOGGER.error("Unhandled exception from REST -> " + exception.getClass().getSimpleName() + ": " + exception.getMessage(), exception);

        return new Response<>(null, "unhandled exception on server: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

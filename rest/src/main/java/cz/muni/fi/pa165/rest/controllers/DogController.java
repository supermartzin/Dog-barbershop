package cz.muni.fi.pa165.rest.controllers;

import cz.muni.fi.pa165.dto.DogDTO;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.facade.DogFacade;
import cz.muni.fi.pa165.rest.exceptions.EntityAlreadyExistsException;
import cz.muni.fi.pa165.rest.exceptions.EntityNotFoundException;
import cz.muni.fi.pa165.rest.models.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @author Dominik Gmiterko
 */
@RestController
@RequestMapping("/dogs")
public class DogController {

    private final static Logger LOGGER = LoggerFactory.getLogger(DogController.class);

    private final DogFacade dogFacade;

    public DogController(DogFacade dogFacade) {
        if (dogFacade == null)
            throw new IllegalArgumentException("DogFacade is null");

        this.dogFacade = dogFacade;
    }

    /**
     * returns all dogs
     *
     * @return list of UserDTOs
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Response<Collection<DogDTO>> getAll() throws FacadeException {
        LOGGER.debug("Get all Dogs");

        return new Response<>(dogFacade.getAll(), "success", HttpStatus.OK);
    }

    /**
     * getting user according to id
     *
     * @param id user identifier
     * @return UserDTO
     * @throws EntityNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Response<DogDTO> getById(@PathVariable("id") long id) throws FacadeException {
        LOGGER.debug("Get Dog by ID {}", id);

        DogDTO dogDTO = dogFacade.getById(id);

        if (dogDTO == null)
            throw new EntityNotFoundException("Dog");

        return new Response<>(dogDTO, "success", HttpStatus.OK);
    }

    /**
     * Create new dog for given data
     *
     * @param dog
     * @return
     * @throws FacadeException
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Response<DogDTO> create(@RequestBody DogDTO dog) {
        LOGGER.debug("Create new Dog");

        try {
            dogFacade.create(dog);

            DogDTO createdDogDTO = dogFacade.getById(dog.getId());

            return new Response<>(createdDogDTO, "success", HttpStatus.OK);
        } catch (FacadeException fEx) {
            throw new EntityAlreadyExistsException("Dog", fEx);
        }
    }

    /**
     * Update information about dog
     *
     * @param id  Id of dog
     * @param dog Dog
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Response<DogDTO> update(@PathVariable("id") long id, @RequestBody DogDTO dog) {
        LOGGER.debug("Update Dog with ID {}", id);

        try {
            dog.setId(id);
            dogFacade.update(dog);

            return new Response<>(dogFacade.getById(id), "success", HttpStatus.OK);
        } catch (FacadeException fEx) {
            throw new EntityNotFoundException("Dog", fEx);
        }
    }

    /**
     * Delete dog
     *
     * @param id
     * @throws FacadeException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Response<Boolean> delete(@PathVariable("id") long id) throws FacadeException {
        LOGGER.debug("Delete Dog with ID {}", id);

        DogDTO dog = dogFacade.getById(id);

        if (dog == null)
            return new Response<>(false, "dog " + id + " not found", HttpStatus.BAD_REQUEST);

        dogFacade.delete(dog);

        return new Response<>(true, "success", HttpStatus.OK);
    }
}

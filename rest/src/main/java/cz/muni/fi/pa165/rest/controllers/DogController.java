package cz.muni.fi.pa165.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.muni.fi.pa165.dto.DogDTO;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.facade.DogFacade;
import cz.muni.fi.pa165.rest.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.rest.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Collection;

/**
 * @author Dominik Gmiterko
 */
@RestController
@RequestMapping("/dog")
public class DogController {
    
    final static Logger logger = LoggerFactory.getLogger(DogController.class);

    @Inject
    private DogFacade dogFacade;

    /**
     * returns all dogs
     *
     * @return list of UserDTOs
     * @throws JsonProcessingException
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<DogDTO> getUsers() throws JsonProcessingException, FacadeException {
        return dogFacade.getAll();
    }

    /**
     *
     * getting user according to id
     * 
     * @param id user identifier
     * @return UserDTO
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final DogDTO getUser(@PathVariable("id") long id) throws Exception {
         DogDTO dog = dogFacade.getById(id);
         if (dog == null){
            throw new ResourceNotFoundException();
         }
         return dog;
    }


    /**
     * Create new dog for given data
     *
     * @param dog
     * @return
     * @throws FacadeException
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final DogDTO create(@RequestBody DogDTO dog) throws FacadeException {

        logger.debug("REST Dog create");

        try {
            dogFacade.create(dog);
        } catch (FacadeException e) {
            throw new ResourceAlreadyExistingException();
        }

        return dogFacade.getById(dog.getId());
    }

    /**
     * Delete dog
     *
     * @param id
     * @throws FacadeException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void delete(@PathVariable("id") long id) throws FacadeException {

        logger.debug("REST Dog delete {}", id);

        DogDTO dog = dogFacade.getById(id);

        if(dog == null) {
            throw new ResourceNotFoundException();
        }

        dogFacade.delete(dog);
    }

    /**
     * Update information about dog
     *
     * @param id Id of dog
     * @param dog Dog
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final DogDTO update(@PathVariable("id") long id, @RequestBody DogDTO dog) throws Exception {

        logger.debug("REST Dog update {}", id);

        dog.setId(id);
        dogFacade.update(dog);

        return dogFacade.getById(id);
    }
}

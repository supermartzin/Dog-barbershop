package cz.muni.fi.pa165.rest.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cz.muni.fi.pa165.dto.CustomerDTO;

/**
 * @author Dominik Gmiterko
 */
public abstract class DogDTOMixin {

    @JsonIgnoreProperties({"username", "password", "firstName", "lastName", "address", "email", "phone", "dogs"})
    public CustomerDTO customerDTO;
}

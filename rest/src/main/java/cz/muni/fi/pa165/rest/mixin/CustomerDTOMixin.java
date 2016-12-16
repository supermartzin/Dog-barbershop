package cz.muni.fi.pa165.rest.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Dominik Gmiterko
 */
@JsonIgnoreProperties({ "password"})
public abstract class CustomerDTOMixin {
}

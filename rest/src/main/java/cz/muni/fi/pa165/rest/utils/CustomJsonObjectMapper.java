package cz.muni.fi.pa165.rest.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import cz.muni.fi.pa165.dto.CustomerDTO;
import cz.muni.fi.pa165.dto.DogDTO;
import cz.muni.fi.pa165.dto.EmployeeDTO;
import cz.muni.fi.pa165.rest.mixin.CustomerDTOMixin;
import cz.muni.fi.pa165.rest.mixin.DogDTOMixin;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * JSON object mapper implementation for including custom mapper settings
 *
 * @author Martin Vrábel
 * @version 08.01.2017 15:05
 */
public class CustomJsonObjectMapper extends ObjectMapper {

    public CustomJsonObjectMapper() {
        super();

        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()));

        // add custom mixins
        addMixIn(CustomerDTO.class, CustomerDTOMixin.class);
        addMixIn(DogDTO.class, DogDTOMixin.class);
        addMixIn(EmployeeDTO.class, EmployeeDTO.class);
    }
}

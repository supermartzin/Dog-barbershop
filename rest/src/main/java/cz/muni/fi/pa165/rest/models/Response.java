package cz.muni.fi.pa165.rest.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

/**
 * Response model for uniform REST controller responses.
 *
 * @author Martin Vrábel
 * @version 10.01.2017 0:20
 */
public class Response<TData> {

    @JsonProperty("data")
    private TData data;

    @JsonProperty("message")
    private String message;

    private HttpStatus status;

    public Response(TData data, String message, HttpStatus status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    @JsonGetter("status")
    private int getStatusCode() {
        return status.value();
    }

    public TData getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}

package cz.muni.fi.pa165.dto;

import cz.muni.fi.pa165.utils.Constants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Order entity representing a facade provision to customer's dog.
 *
 * @author Dominik Gmiterko
 */
public class OrderDTO {

    private long id;

    @NotNull
    private LocalDateTime time;

    @NotNull
    private DogDTO dog;

    @NotNull
    private ServiceDTO service;

    private EmployeeDTO employee;

    @NotNull
    private boolean status;

    public OrderDTO() {
    }

    public OrderDTO(LocalDateTime time, DogDTO dog, ServiceDTO service) {
        this.time = time;
        this.dog = dog;
        this.service = service;
        this.status = false;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public DogDTO getDog() {
        return dog;
    }

    public void setDog(DogDTO dog) {
        this.dog = dog;
    }

    public ServiceDTO getService() {
        return service;
    }

    public void setService(ServiceDTO service) {
        this.service = service;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        if (!(object instanceof OrderDTO))
            return true;
        if (getDog() == null || getService() == null)
            return false;

        OrderDTO order = (OrderDTO) object;

        return getDog().equals(order.getDog())
                && getService().equals(order.getService())
                && getTime().equals(order.getTime());
    }

    @Override
    public int hashCode() {
        int result;

        result = getTime() != null ? getTime().hashCode() : 0;
        result = 31 * result + (getDog() != null ? getDog().hashCode() : 0);
        result = 31 * result + (getService() != null ? getService().hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Order ID: " + getId() + " from " + getTime().format(Constants.DEFAULT_DATE_TIME_FORMAT);
    }
}

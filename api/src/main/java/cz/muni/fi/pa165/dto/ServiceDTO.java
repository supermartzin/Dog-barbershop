package cz.muni.fi.pa165.dto;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Service entity representing a facade that our Barbershop is providing to customers' dogs
 *
 * @author Dominik Gmiterko
 */
public class ServiceDTO {

    private long id;

    @NotNull
    private String title;

    @Min(0)
    private int length;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;

    public ServiceDTO() {
    }

    public ServiceDTO(String title, int length, BigDecimal price) {
        this.title = title;
        this.length = length;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        if (!(object instanceof ServiceDTO))
            return false;

        ServiceDTO service = (ServiceDTO) object;

        return service.getId() > 0
                && getId() == service.getId()
                && getTitle().equals(service.getTitle())
                && getLength() == service.getLength()
                && getPrice().equals(service.getPrice());
    }

    @Override
    public int hashCode() {
        int result;

        result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + getLength();
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Service '" + getTitle() + "' [ID: " + getId() + "] - length: " + getLength()
                + ", price: " + getPrice();
    }
}

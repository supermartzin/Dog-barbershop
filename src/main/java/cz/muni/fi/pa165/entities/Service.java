package cz.muni.fi.pa165.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Service entity representing a service that our Barbershop is providing to customers' dogs
 *
 * @author Martin Vrábel
 * @version 23.10.2016 20:36
 */
@Entity
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotNull
    private String title;

    @Column(length = 10)
    private int length;

    private BigDecimal price;

    public Service() {
    }

    public Service(String title, int length, BigDecimal price) {
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
}

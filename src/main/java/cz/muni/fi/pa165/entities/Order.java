package cz.muni.fi.pa165.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Order entity representing a service provision to customer's dog.
 *
 * @author Martin Vrábel
 * @version 23.10.2016 20:40
 */
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Dog.class)
    private Dog dog;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Service.class)
    private Service service;

    public Order() {
    }

    public Order(LocalDateTime time, Dog dog, Service service) {
        this.time = time;
        this.dog = dog;
        this.service = service;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}

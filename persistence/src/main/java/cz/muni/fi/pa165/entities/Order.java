package cz.muni.fi.pa165.entities;

import cz.muni.fi.pa165.utils.Constants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Order entity representing a facade provision to customer's dog.
 *
 * @author Martin Vrábel
 * @version 23.10.2016 20:40
 */
@Entity
@Table(name = "`Order`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime time;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dog_id")
    private Dog dog;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    private Service service;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        if (!(object instanceof Order))
            return true;
        if (getDog() == null || getService() == null)
            return false;

        Order order = (Order) object;

        return getDog().equals(order.getDog())
                && getService().equals(order.getService())
                && getTime() == order.getTime();
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

package cz.muni.fi.pa165.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Dog entity representing dog to which our company is providing a service
 *
 * @author Martin Vrábel
 * @version 23.10.2016 20:29
 */
@Entity
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String breed;

    @Min(0)
    @Column(length = 5)
    private int age;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Dog() {
    }

    public Dog(String name, String breed, int age, Customer customer) {
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.customer = customer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        if (!(object instanceof Dog))
            return false;
        if (getId() == 0
                || getName() == null
                || getBreed() == null
                || getCustomer() == null)
            return false;

        Dog dog = (Dog) object;

        return dog.getId() > 0
                && getId() == dog.getId()
                && getName().equals(dog.getName())
                && getBreed().equals(dog.getBreed())
                && getCustomer().equals(dog.getCustomer());
    }

    @Override
    public int hashCode() {
        int result;

        result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getBreed() != null ? getBreed().hashCode() : 0);
        result = 31 * result + (getCustomer() != null ? getCustomer().hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Dog '" + getName() + "' (" + getBreed() + "): age " + getAge();
    }
}

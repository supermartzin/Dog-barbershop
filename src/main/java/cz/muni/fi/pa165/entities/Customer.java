package cz.muni.fi.pa165.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Customer entity representing a user of system that is owner of dogs and is making an order of our services.
 *
 * @author Martin Vrábel
 * @version 23.10.2016 20:27
 */
@Entity
public class Customer extends User {

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Dog> dogs;

    public Customer() {
        this.dogs = new HashSet<>();
    }

    public Customer(String username, String password, String firstName,
                    String lastName, Address address, String email, String phone) {
        super(username, password, firstName, lastName, address, email, phone);
        this.dogs = new HashSet<>();
    }

    public Set<Dog> getDogs() {
        return Collections.unmodifiableSet(dogs);
    }

    public void addDog(Dog dog) {
        if (dog == null)
            throw new IllegalArgumentException("dog cannot be null");

        dogs.add(dog);

        // delete dog from previous owner's collection
        Customer previousOwner = dog.getCustomer();
        if (previousOwner != null)
            previousOwner.removeDog(dog);

        // set current customer as an owner
        dog.setCustomer(this);
    }

    public void removeDog(Dog dog) {
        if (dog == null)
            throw new IllegalArgumentException("dog cannot be null");

        if (dogs.contains(dog))
        {
            dogs.remove(dog);
            dog.setCustomer(null);
        }
    }

    @Override
    public String toString() {
        return "Customer '" + this.getUsername() + "' [ID: " + this.getId() + "]";
    }
}

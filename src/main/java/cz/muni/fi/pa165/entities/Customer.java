package cz.muni.fi.pa165.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * Customer entity representing a user of system that is owner of dogs and is making an order of our services.
 *
 * @author Martin Vrábel
 * @version 23.10.2016 20:27
 */
@Entity
public class Customer extends User {

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "id")
    private Set<Dog> dogs;
}

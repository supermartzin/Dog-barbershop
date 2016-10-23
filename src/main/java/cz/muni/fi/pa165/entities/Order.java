package cz.muni.fi.pa165.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
}

package cz.muni.fi.pa165.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
}

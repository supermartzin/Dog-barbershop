package cz.muni.fi.pa165.entities;

import javax.persistence.*;

/**
 * Dog entity representing dog to which our company is providing a service
 *
 * @author Martin Vrábel
 * @version 23.10.2016 20:29
 */
@Entity
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;
}

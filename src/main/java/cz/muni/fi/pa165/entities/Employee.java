package cz.muni.fi.pa165.entities;

import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * Employee entity representing user of system that is employed in our Barbershop
 *
 * @author Martin Vrábel
 * @version 23.10.2016 20:28
 */
@Entity
public class Employee extends User {

    private BigDecimal salary;
}

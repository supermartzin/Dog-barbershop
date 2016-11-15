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

    public Employee() {
    }

    public Employee(String username, String password, String firstName, String lastName,
                    Address address, String email, String phone, BigDecimal salary) {
        super(username, password, firstName, lastName, address, email, phone);
        this.salary = salary;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object == null || getClass() != object.getClass())
            return false;

        Employee employee = (Employee) object;

        return employee.getId() > 0 && getId() == employee.getId();
    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }

    @Override
    public String toString() {
        return "Employee '" + this.getUsername() + "' [ID: " + this.getId() + "]";
    }
}

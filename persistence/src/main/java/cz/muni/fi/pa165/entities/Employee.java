package cz.muni.fi.pa165.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Employee entity representing user of system that is employed in our Barbershop
 *
 * @author Martin Vrábel
 * @version 23.10.2016 20:28
 */
@Entity
public class Employee extends User {

    @NotNull
    @DecimalMin("0.0")
    @Column(nullable = false)
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
        if (object == null)
            return false;
        if (!(object instanceof Employee))
            return false;

        Employee employee = (Employee) object;

        return employee.getId() > 0
                && getId() == employee.getId()
                && getUsername().equals(employee.getUsername());
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));

        return 31 * result + getUsername().hashCode();
    }

    @Override
    public String toString() {
        return "Employee '" + this.getUsername() + "' [ID: " + this.getId() + "]";
    }
}

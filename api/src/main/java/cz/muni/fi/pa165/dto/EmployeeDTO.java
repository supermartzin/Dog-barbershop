package cz.muni.fi.pa165.dto;

import cz.muni.fi.pa165.utils.Constants;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Employee entity representing user of system that is employed in our Barbershop
 *
 * @author Dominik Gmiterko
 */
public class EmployeeDTO {

    private long id;

    @NotNull
    private String username;

    @NotNull
    @Size(min = 6, max = 256)
    private String password;

    private String firstName;

    private String lastName;

    private AddressDTO address;

    @Email
    private String email;

    @Pattern(regexp = Constants.PHONE_NUMBER_REGEX_PATTERN)
    private String phone;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal salary;

    public EmployeeDTO() {
    }

    public EmployeeDTO(String username, String password, String firstName, String lastName,
                    AddressDTO address, String email, String phone, BigDecimal salary) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.salary = salary;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        if (!(object instanceof EmployeeDTO))
            return false;

        EmployeeDTO employee = (EmployeeDTO) object;

        return employee.getId() > 0
                && getId() == employee.getId()
                && getUsername().equals(employee.getUsername());
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));

        if (getUsername() != null)
            result = 31 * result + getUsername().hashCode();

        return result;
    }

    @Override
    public String toString() {
        return "Employee '" + this.getUsername() + "' [ID: " + this.getId() + "]";
    }
}

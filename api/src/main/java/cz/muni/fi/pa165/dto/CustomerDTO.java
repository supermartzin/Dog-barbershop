package cz.muni.fi.pa165.dto;

import cz.muni.fi.pa165.utils.Constants;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Customer entity representing a user of system that is owner of dogs and is making an order of our services.
 *
 * @author Dominik Gmiterko
 */
public class CustomerDTO {

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

    private Set<DogDTO> dogs;

    public CustomerDTO() {
        this.dogs = new HashSet<>();
    }

    public CustomerDTO(String username, String password, String firstName,
                    String lastName, AddressDTO address, String email, String phone) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.dogs = new HashSet<>();
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

    public Set<DogDTO> getDogs() {
        return dogs;
    }

    public void addDog(DogDTO dog) {
        if (dog == null)
            throw new IllegalArgumentException("dog cannot be null");

        dogs.add(dog);

        // delete dog from previous owner's collection
        CustomerDTO previousOwner = dog.getCustomer();
        if (previousOwner != null)
            previousOwner.removeDog(dog);

        // set current customer as an owner
        dog.setCustomer(this);
    }

    public void removeDog(DogDTO dog) {
        if (dog == null)
            throw new IllegalArgumentException("dog cannot be null");

        if (dogs.contains(dog))
        {
            dogs.remove(dog);
            dog.setCustomer(null);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        if (!(object instanceof CustomerDTO))
            return false;
        if (getId() == 0 || getUsername() == null)
            return false;

        CustomerDTO customer = (CustomerDTO) object;

        return customer.getId() > 0
                && getId() == customer.getId()
                && getUsername().equals(customer.getUsername());
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
        return "Customer '" + this.getUsername() + "' [ID: " + this.getId() + "]";
    }
}

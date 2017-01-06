package cz.muni.fi.pa165.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * DogDTO entity representing dog to which our company is providing a facade
 *
 * @author Dominik Gmiterko
 */
public class DogDTO {

    private long id;

    @NotNull
    private String name;

    @NotNull
    private String breed;

    @Min(0)
    private int age;

    private CustomerDTO customer;

    public DogDTO() {
    }

    public DogDTO(String name, String breed, int age) {
        this.name = name;
        this.breed = breed;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        if (!(object instanceof DogDTO))
            return false;
        if (getId() == 0
                || getName() == null
                || getBreed() == null)
            return false;

        DogDTO dog = (DogDTO) object;

        return dog.getId() > 0
                && getId() == dog.getId()
                && getName().equals(dog.getName())
                && getBreed().equals(dog.getBreed());
    }

    @Override
    public int hashCode() {
        int result;

        result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getBreed() != null ? getBreed().hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "DogDTO '" + getName() + "' (" + getBreed() + "): age " + getAge();
    }
}

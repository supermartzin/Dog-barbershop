package cz.muni.fi.pa165.dto;

import javax.persistence.*;

/**
 * Address entity
 *
 * @author Dominik Gmiterko
 */
public class AddressDTO {

    private long id;

    private String street;

    private int number;

    private String city;

    private int postalCode;

    private String country;

    public AddressDTO() {
    }

    public long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        if (!(object instanceof AddressDTO))
            return false;

        AddressDTO address = (AddressDTO) object;

        return getStreet().equals(address.getStreet())
                && getNumber() == address.getNumber()
                && getCity().equals(address.getCity())
                && getPostalCode() == address.getPostalCode()
                && getCountry().equals(address.getCountry());

    }

    @Override
    public int hashCode() {
        int result;

        result = getStreet() != null ? getStreet().hashCode() : 0;
        result = 31 * result + getNumber();
        result = 31 * result + (getCity() != null ? getCity().hashCode() : 0);
        result = 31 * result + getPostalCode();
        result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Address: " + getStreet() + " " + getNumber() +
                ", " + getPostalCode() + " " + getCity() + ", " + getCountry();
    }
}

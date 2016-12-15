package cz.muni.fi.pa165.entities;

import javax.persistence.*;

/**
 * Address entity
 *
 * @author Martin Vrábel
 * @version 23.10.2016 20:01
 */
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String street;

    private int number;

    private String city;

    private int postalCode;

    private String country;

    public Address() {
    }

    public Address(long id) {
        this.id = id;
    }

    public Address(String street, int number, String city, int postalCode, String country) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        if (!(object instanceof Address))
            return false;

        Address address = (Address) object;

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

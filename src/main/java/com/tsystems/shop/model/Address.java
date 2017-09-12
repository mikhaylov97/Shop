package com.tsystems.shop.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Address entity model. This class maps on addresses Table in our Database.
 * There we put all necessary options in fields. Hibernate forces us to make class
 * with fields and getters and setters for all of them, Serializable interface(optional),
 * and empty constructor if we define custom one.
 */
@Entity
@Table(name = "addresses")
public class Address implements Serializable{

    /**
     * Address ID. It generates by hibernate while inserting.
     * This filed connects with address_id column in addresses table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    private long id;

    /**
     * Address city name.
     * This field connects with city column in addresses table.
     * May be null, may be repeated.
     */
    @Column(name = "city")
    private String city = "";

    /**
     * Address country name.
     * This field connects with country column in addresses table.
     * May be null, may be repeated.
     */
    @Column(name = "country")
    private String country = "";

    /**
     * Address postcode number.
     * This field connects with postcode column in addresses table.
     * May be null, may be repeated.
     */
    @Column(name = "postcode")
    private String postcode = "";

    /**
     * Address street name.
     * This field connects with street column in addresses table.
     * May be null, may be repeated.
     */
    @Column(name = "street")
    private String street = "";

    /**
     * Address house name.
     * This field connects with house column in addresses table.
     * May be null, may be repeated.
     */
    @Column(name = "house")
    private String house = "";

    /**
     * Address apartment number.
     * This field connects with apartment column in addresses table.
     * May be null, may be repeated.
     */
    @Column(name = "apartment")
    private String apartment = "";

    /**
     * Empty constructor for hibernate.
     */
    public Address() {
    }

    /**
     * Our custom constructor
     * @param city name to set
     * @param country name to set
     * @param postcode number to set
     * @param street name to set
     * @param house number to set
     * @param apartment number to set
     */
    public Address(String city, String country, String postcode,
                   String street, String house, String apartment) {
        this.city = city;
        this.country = country;
        this.postcode = postcode;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
    }

    /**
     * Simple getter
     * @return Address id
     */
    public long getId() {
        return id;
    }

    /**
     * Simple setter
     * @param id value to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Simple getter
     * @return Address city name
     */
    public String getCity() {
        return city;
    }

    /**
     * Simple setter
     * @param city value to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Simple getter
     * @return Address country name
     */
    public String getCountry() {
        return country;
    }

    /**
     * Simple setter
     * @param country value to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Simple getter
     * @return Address postcode number
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * Simple setter
     * @param postcode value to set
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * Simple getter
     * @return Address street name
     */
    public String getStreet() {
        return street;
    }

    /**
     * Simple setter
     * @param street value to set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Simple getter
     * @return Address house number
     */
    public String getHouse() {
        return house;
    }

    /**
     * Simple setter
     * @param house value to set
     */
    public void setHouse(String house) {
        this.house = house;
    }

    /**
     * Simple getter
     * @return Address apartment number
     */
    public String getApartment() {
        return apartment;
    }

    /**
     * Simple setter
     * @param apartment value to set
     */
    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     * In our case in next format: Country, City (postcode), Street House, Apartment.
     * Some parameters may be null.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean countryExpr = country.length() != 0;
        boolean cityExpr = city.length() != 0;
        boolean postcodeExpr = postcode.length() != 0;
        boolean streetExpr = street.length() != 0;
        boolean houseExpr = house.length() != 0;
        boolean apartmentExpr = apartment.length() != 0;

        if (countryExpr) sb.append(country);
        if (sb.length() > 0 && cityExpr) sb.append(", ");
        if (cityExpr) sb.append(city);
        if (sb.length() > 0 && postcodeExpr) sb.append(" ");
        if (postcodeExpr) sb.append("(").append(postcode).append(")");
        if (sb.length() > 0 && streetExpr) sb.append(", ");
        if (streetExpr) sb.append(street);
        if (streetExpr && houseExpr) sb.append(" ").append(house);
        if (streetExpr && houseExpr && apartmentExpr) sb.append(", ").append(apartment);

        return sb.toString();
    }
}

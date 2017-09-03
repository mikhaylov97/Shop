package com.tsystems.shop.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "addresses")
public class Address implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    private long id;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "street")
    private String street;

    @Column(name = "house")
    private String house;

    @Column(name = "apartment")
    private String apartment;

    public Address() {
    }

    public Address(String city, String country, String postcode,
                   String street, String house, String apartment) {
        this.city = city;
        this.country = country;
        this.postcode = postcode;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean countryExpr = country != null && country.length() != 0;
        boolean cityExpr = city != null && city.length() != 0;
        boolean postcodeExpr = postcode != null && postcode.length() != 0;
        boolean streetExpr = street != null && street.length() != 0;
        boolean houseExpr = house != null && house.length() != 0;
        boolean apartmentExpr = apartment != null && apartment.length() != 0;

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

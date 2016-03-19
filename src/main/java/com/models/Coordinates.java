package com.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table
public class Coordinates {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    private String uuid;

    private String vertical;

    private String horizontal;

    private String country;

    private String city;

    private String street;

    private String streetNumber;

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVertical() {
        return vertical;
    }

    public void setVertical(String vertical) {
        this.vertical = vertical;
    }

    public String getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(String horizontal) {
        this.horizontal = horizontal;
    }

    @Override
    public String toString() {
        return "{" +
                "\"uuid\":\"" + uuid + '\"' +
                ", \"country\":\"" + country + '\"' +
                ", \"city\":\"" + city + '\"' +
                ", \"street\":\"" + street+ '\"' +
                ", \"street number\":\"" + streetNumber+ '\"' +
                ", \"horizontal\":\"" + horizontal + '\"' +
                ", \"vertical\":\"" + vertical + '\"' +
                '}';
    }
}

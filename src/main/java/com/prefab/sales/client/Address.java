package com.prefab.sales.client;

import com.prefab.sales.utils.Validation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "id", unique = true)
    private int id;
    @Column(name = "address")
    private String address;
    @Column(name = "city")
    private String city;
    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "country")
    private String country;
    @Transient
    @OneToMany (targetEntity = ProspectAddress.class, mappedBy = "address",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProspectAddress> prospectAddresses = new HashSet<>();

    public Address() {
    }

    public Address(String address, String city, String postalCode, String country) {
        setAddress(address);
        setCity(city);
        setPostalCode(postalCode);
        setCountry(country);
    }

    private void setAddress(String address) {
        Validation.alphanumericValue(address);
        this.address = address.trim();
    }

    private void setCity(String city) {
        Validation.onlyLetters(city);
        this.city = city.trim();
    }

    private void setPostalCode(String postalCode) {
        Validation.alphanumericValue(postalCode);
        this.postalCode = postalCode.trim();
    }

    private void setCountry(String country) {
        Validation.onlyLetters(country);
        this.country = country.trim();
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address1 = (Address) o;
        return Objects.equals(address, address1.address) &&
                Objects.equals(city, address1.city) &&
                Objects.equals(postalCode, address1.postalCode) &&
                Objects.equals(country, address1.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, city, postalCode, country);
    }
}



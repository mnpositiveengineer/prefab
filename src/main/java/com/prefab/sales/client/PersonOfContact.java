package com.prefab.sales.client;

import com.prefab.sales.utils.Validation;

import java.util.Objects;

public class PersonOfContact {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String position;
    private boolean isDecisionMaker;

    public PersonOfContact(String firstName, String lastName, String email, String phoneNumber) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPhoneNumber(phoneNumber);
    }

    public void setFirstName(String firstName) {
        Validation.onlyLetters(firstName);
        this.firstName = firstName.trim();
    }

    public void setLastName(String lastName) {
        Validation.onlyLetters(lastName);
        this.lastName = lastName.trim();
    }

    public void setEmail(String email) {
        Validation.onlyEmail(email);
        this.email = email.trim();
    }

    public void setPhoneNumber(String phoneNumber) {
        Validation.onlyPhoneNumber(phoneNumber);
        this.phoneNumber = phoneNumber.trim();
    }

    public void setPosition(String position) {
        Validation.onlyLetters(position);
        this.position = position.trim();
    }

    public void setDecisionMaker(boolean decisionMaker) {
        isDecisionMaker = decisionMaker;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonOfContact that = (PersonOfContact) o;
        return email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}

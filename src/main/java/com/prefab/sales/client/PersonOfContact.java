package com.prefab.sales.client;

import com.prefab.sales.utils.Validation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "personofcontacts")
public class PersonOfContact {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "id", unique = true)
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "position")
    private String position;
    @Column(name = "decision_maker")
    private boolean isDecisionMaker;
    @ManyToOne
    @JoinColumn(name = "prospect_id")
    private Prospect prospect;

    public PersonOfContact() {
    }

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

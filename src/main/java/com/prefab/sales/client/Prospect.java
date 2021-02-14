package com.prefab.sales.client;

import com.prefab.sales.utils.CollectionModification;
import com.prefab.sales.utils.Validation;
import com.prefab.sales.utils.exceptions.ObjectAlreadyAddedException;
import com.prefab.sales.utils.exceptions.ObjectNotInCollectionException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "prospects")
public class Prospect {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "id", unique = true)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "principal_activity")
    private String principalActivity;
    @Transient
    private Set<Address> addresses = new HashSet<>();
    @OneToMany (targetEntity = PersonOfContact.class, mappedBy = "prospect",
            cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Set<PersonOfContact> personOfContacts = new HashSet<>();
    @Column(name = "tax")
    private String tax;

    public Prospect() {
    }

    public Prospect(String name) {
        setName(name);
    }

    public void addPersonOfContactToProspect(PersonOfContact...selectedPersonOfContacts) {
        for (PersonOfContact personOfContact : selectedPersonOfContacts) {
            try {
                CollectionModification.addToCollection(personOfContacts, personOfContact);
            } catch (ObjectAlreadyAddedException e) {
                System.out.println("Email " + personOfContact.getEmail() + " is already on the list.");
            }
        }
    }

    public void removePersonOfContactFromProspect(PersonOfContact...selectedPersonOfContacts) {
        for (PersonOfContact personOfContact : selectedPersonOfContacts) {
            try {
                CollectionModification.removeFromCollection(personOfContacts, personOfContact);
            } catch (ObjectNotInCollectionException e) {
                System.out.println("No person of email " + personOfContact.getEmail() + " on the list.");
            }
        }
    }

    public void addAddressToProspect(Address...selectedAddresses) {
        for (Address address : selectedAddresses) {
            try {
                CollectionModification.addToCollection(addresses, address);
            } catch (ObjectAlreadyAddedException e) {
                System.out.println("Address is already on the list.");
            }
        }
    }

    public void removeAddressFromProspect(Address...selectedAddresses) {
        for (Address address : selectedAddresses) {
            try {
                CollectionModification.removeFromCollection(addresses, address);
            } catch (ObjectNotInCollectionException e) {
                System.out.println("Address is not on the list.");
            }
        }
    }

    public void setName(String name) {
        Validation.alphanumericValue(name);
        this.name = name.trim();
    }

    public void setPrincipalActivity(String principalActivity) {
        Validation.onlyLetters(principalActivity);
        this.principalActivity = principalActivity.trim();
    }

    public void setTax(String tax) {
        Validation.alphanumericValue(tax);
        this.tax = tax;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public Set<PersonOfContact> getPersonOfContacts() {
        return personOfContacts;
    }

    public String getPrincipalActivity() {
        return principalActivity;
    }

    public String getName() {
        return name;
    }

    public String getTax() {
        return tax;
    }
}

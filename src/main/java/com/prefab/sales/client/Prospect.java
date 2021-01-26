package com.prefab.sales.client;

import com.prefab.sales.utils.CollectionModification;
import com.prefab.sales.utils.Validation;
import com.prefab.sales.utils.exceptions.ObjectAlreadyAddedException;
import com.prefab.sales.utils.exceptions.ObjectNotInCollectionException;
import java.util.HashSet;
import java.util.Set;

public class Prospect {

    private String name;
    private String principalActivity;
    private Set<Address> addresses = new HashSet<>();
    private Set<PersonOfContact> personOfContacts = new HashSet<>();
    private String tax;


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

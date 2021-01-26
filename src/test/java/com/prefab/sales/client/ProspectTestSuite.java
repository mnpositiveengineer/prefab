package com.prefab.sales.client;

import com.prefab.sales.client.Address;
import com.prefab.sales.client.PersonOfContact;
import com.prefab.sales.client.Prospect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ProspectTestSuite {

    Prospect prospect = new Prospect("Test Company");
    PersonOfContact personOfContact1 = new PersonOfContact("John", "Smith", "john@smith.com", "0048123456789");
    PersonOfContact personOfContact2 = new PersonOfContact("John", "Smith", "john1@smith.com", "0048123456789");
    PersonOfContact personOfContact3 = new PersonOfContact("John", "Smith", "john2@smith.com", "0048123456789");
    PersonOfContact personOfContact4 = new PersonOfContact("John", "Smith", "john3@smith.com", "0048123456789");
    PersonOfContact personOfContact5 = new PersonOfContact("Mike", "Alan", "john@smith.com", "00481234567891");

    Address address1 = new Address("Address", "City", "00-000", " Country");
    Address address2 = new Address("Different Address", "City", "00-000", " Country");
    Address address3 = new Address("Address", "Different City", "00-0001", " Country");
    Address address4 = new Address("Address", "City", "00-001", "Country");
    Address address5 = new Address("Address", "City", "00-000", "Different Country");
    Address address6 = new Address("Address", "City", "00-000", " Country");

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Test
    void ShouldAddPersonOfContact() {
        prospect.addPersonOfContactToProspect(personOfContact1, personOfContact2, personOfContact3, personOfContact4);
        Assertions.assertEquals(4, prospect.getPersonOfContacts().size());
    }

    @Test
    void ShouldNotAddPersonOfContact() {
        prospect.addPersonOfContactToProspect(personOfContact1);
        System.setOut(new PrintStream(outputStreamCaptor));
        prospect.addPersonOfContactToProspect(personOfContact5);
        Assertions.assertEquals("Email john@smith.com is already on the list.", outputStreamCaptor.toString()
                .trim());
        Assertions.assertEquals(1, prospect.getPersonOfContacts().size());
    }

    @Test
    void ShouldRemovePersonOfContact() {
        prospect.addPersonOfContactToProspect(personOfContact1, personOfContact2);
        prospect.removePersonOfContactFromProspect(personOfContact1, personOfContact2);
        Assertions.assertEquals(0, prospect.getPersonOfContacts().size());
    }

    @Test
    void ShouldNotRemovePersonOfContact() {
        System.setOut(new PrintStream(outputStreamCaptor));
        prospect.removePersonOfContactFromProspect(personOfContact1);
        Assertions.assertEquals("No person of email john@smith.com on the list.", outputStreamCaptor.toString()
                .trim());
        outputStreamCaptor.reset();
        prospect.addPersonOfContactToProspect(personOfContact1);
        prospect.removePersonOfContactFromProspect(personOfContact2);
        Assertions.assertEquals("No person of email john1@smith.com on the list.", outputStreamCaptor.toString()
                .trim());
        Assertions.assertEquals(1, prospect.getPersonOfContacts().size());
    }

    @Test
    void ShouldAddAddress() {
        prospect.addAddressToProspect(address1, address2, address3, address4, address5);
        Assertions.assertEquals(5, prospect.getAddresses().size());
    }

    @Test
    void ShouldNotAddAddress() {
        System.setOut(new PrintStream(outputStreamCaptor));
        prospect.addAddressToProspect(address1, address6);
        Assertions.assertEquals("Address is already on the list.", outputStreamCaptor.toString()
                .trim());
        Assertions.assertEquals(1, prospect.getAddresses().size());
    }

    @Test
    void ShouldRemoveAddress() {
        prospect.addAddressToProspect(address1, address2);
        prospect.removeAddressFromProspect(address1, address2);
        Assertions.assertEquals(0, prospect.getAddresses().size());
    }

    @Test
    void ShouldNotRemoveAddress() {
        System.setOut(new PrintStream(outputStreamCaptor));
        prospect.removeAddressFromProspect(address1);
        Assertions.assertEquals("Address is not on the list.", outputStreamCaptor.toString()
                .trim());
        outputStreamCaptor.reset();
        prospect.addAddressToProspect(address1);
        prospect.removeAddressFromProspect(address2);
        Assertions.assertEquals("Address is not on the list.", outputStreamCaptor.toString()
                .trim());
        Assertions.assertEquals(1, prospect.getAddresses().size());
    }
}
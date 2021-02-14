package com.prefab.sales.client.repositories;

import com.prefab.sales.client.Address;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AddressRepositoryTestSuite {

    @Autowired
    private AddressRepository addressRepository;

    @BeforeEach
    public void clearBefore(){
        addressRepository.deleteAll();
    }

    @AfterEach
    public void clearAfter(){
        addressRepository.deleteAll();
    }

    @Test
    public void ShouldReturnZeroRows() {
        Assertions.assertEquals(0, addressRepository.count());
    }

    @Test
    public void ShouldSaveOneAddressInDatabase() {
        Address address = new Address("Test", "Test", "00-000", "Testing");
        addressRepository.save(address);
        Assertions.assertEquals(1, addressRepository.count());
    }

    @Test
    public void ShouldRemoveAddressFromDatabase() {
        Address address = new Address("Test", "Test", "00-000", "Testing");
        addressRepository.save(address);
        Assertions.assertEquals(1, addressRepository.count());
        addressRepository.delete(address);
        Assertions.assertEquals(0, addressRepository.count());
    }

    @Test
    public void ShouldNotDuplicateAddressesInDatabase() {
        Address address = new Address("Test", "Test", "00-000", "Testing");
        addressRepository.save(address);
        Assertions.assertEquals(1, addressRepository.count());
        addressRepository.save(address);
        Assertions.assertEquals(1, addressRepository.count());
    }



}
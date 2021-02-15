package com.prefab.sales.client.repositories;

import com.prefab.sales.client.Address;
import com.prefab.sales.client.Prospect;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProspectAddressRepositoryTestSuite {

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    ProspectRepository prospectRepository;
    @Autowired
    ProspectAddressRepository prospectAddressRepository;

    @BeforeEach
    public void clearBefore(){
        addressRepository.deleteAll();
        prospectRepository.deleteAll();
        prospectAddressRepository.deleteAll();
    }

    @AfterEach
    public void clearAfter(){
        addressRepository.deleteAll();
        prospectRepository.deleteAll();
        prospectAddressRepository.deleteAll();
    }

    @Test
    public void ShouldReturnZeroRows() {
        Assertions.assertEquals(0, addressRepository.count());
        Assertions.assertEquals(0, prospectRepository.count());
    }

    @Test
    public void ShouldAssignOneAddressToProspect(){
        Address address = new Address("Test", "Test", "00-000", "Testing");
        Prospect prospect = new Prospect("Testing");
        prospect.addAddressToProspect(address);
        addressRepository.save(address);
        prospectRepository.save(prospect);
        prospectAddressRepository.saveAll(prospect.getProspectAddresses());
        Assertions.assertEquals(1, addressRepository.count());
        Assertions.assertEquals(1, prospectRepository.count());
        Assertions.assertEquals(1, prospectAddressRepository.count());
    }


}
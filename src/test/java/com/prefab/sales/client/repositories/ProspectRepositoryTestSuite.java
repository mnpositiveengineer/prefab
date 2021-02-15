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
class ProspectRepositoryTestSuite {

    @Autowired
    private ProspectRepository prospectRepository;

    @BeforeEach
    public void clearBefore(){
        prospectRepository.deleteAll();
    }

    @AfterEach
    public void clearAfter(){
        prospectRepository.deleteAll();
    }

    @Test
    public void ShouldReturnZeroRows() {
        Assertions.assertEquals(0, prospectRepository.count());
    }

    @Test
    public void ShouldSaveOneProjectInDatabase() {
        Prospect prospect = new Prospect("Testing");
        prospectRepository.save(prospect);
        Assertions.assertEquals(1, prospectRepository.count());
    }

    @Test
    public void ShouldRemoveProjectFromDatabase() {
        Prospect prospect = new Prospect("Testing");
        prospectRepository.save(prospect);
        Assertions.assertEquals(1, prospectRepository.count());
        prospectRepository.delete(prospect);
        Assertions.assertEquals(0, prospectRepository.count());
    }

    @Test
    public void ShouldNotDuplicateProspectsInDatabase() {
        Prospect prospect = new Prospect("Testing");
        prospectRepository.save(prospect);
        Assertions.assertEquals(1, prospectRepository.count());
        prospectRepository.save(prospect);
        Assertions.assertEquals(1, prospectRepository.count());
    }

    @Test
    public void ShouldSaveUpdateOnProspectToDatabase() {
        Prospect prospect = new Prospect("Company");
        prospectRepository.save(prospect);
        Assertions.assertEquals(1, prospectRepository.count());
        prospect.setTax("123456789");
        prospect.setPrincipalActivity("Testing");
        prospectRepository.save(prospect);
        Assertions.assertEquals("123456789", prospectRepository.findByName("Company").getTax());
        Assertions.assertEquals("Testing", prospectRepository.findByName("Company").getPrincipalActivity());
    }


}
package com.prefab.sales.client.repositories;

import com.prefab.sales.client.PersonOfContact;
import com.prefab.sales.client.Prospect;
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
class PersonOfContactRepositoryTestSuite {

    @Autowired
    private PersonOfContactRepository personOfContactRepository;

    @BeforeEach
    public void clearBefore(){
        personOfContactRepository.deleteAll();
    }

    @AfterEach
    public void clearAfter(){
        personOfContactRepository.deleteAll();
    }

    @Test
    public void ShouldReturnZeroRows() {
        Assertions.assertEquals(0, personOfContactRepository.count());
    }

    @Test
    public void ShouldSaveOnePOCInDatabase() {
        PersonOfContact personOfContact = new PersonOfContact("John", "Smith", "johm.smith@test.com", "123456789");
        personOfContactRepository.save(personOfContact);
        Assertions.assertEquals(1, personOfContactRepository.count());
    }

    @Test
    public void ShouldRemovePOCFromDatabase() {
        PersonOfContact personOfContact = new PersonOfContact("John", "Smith", "johm.smith@test.com", "123456789");
        personOfContactRepository.save(personOfContact);
        Assertions.assertEquals(1, personOfContactRepository.count());
        personOfContactRepository.delete(personOfContact);
        Assertions.assertEquals(0, personOfContactRepository.count());
    }

    @Test
    public void ShouldNotDuplicatePOCInDatabase() {
        PersonOfContact personOfContact = new PersonOfContact("John", "Smith", "johm.smith@test.com", "123456789");
        personOfContactRepository.save(personOfContact);
        Assertions.assertEquals(1, personOfContactRepository.count());
        personOfContactRepository.save(personOfContact);
        Assertions.assertEquals(1, personOfContactRepository.count());
    }

}
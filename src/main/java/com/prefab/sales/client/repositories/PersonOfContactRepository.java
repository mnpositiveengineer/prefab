package com.prefab.sales.client.repositories;

import com.prefab.sales.client.PersonOfContact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Repository
@Transactional
public interface PersonOfContactRepository extends CrudRepository<PersonOfContact, Integer> {
}

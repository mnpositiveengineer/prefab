package com.prefab.sales.client.repositories;

import com.prefab.sales.client.Prospect;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ProspectRepository extends CrudRepository<Prospect, Integer> {

    Prospect findByName(String name);
}

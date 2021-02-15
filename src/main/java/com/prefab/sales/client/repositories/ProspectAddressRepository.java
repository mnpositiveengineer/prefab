package com.prefab.sales.client.repositories;

import com.prefab.sales.client.ProspectAddress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ProspectAddressRepository extends CrudRepository<ProspectAddress, Integer> {
}

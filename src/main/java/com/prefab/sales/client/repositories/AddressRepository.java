package com.prefab.sales.client.repositories;

import com.prefab.sales.client.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.TransactionScoped;

@Repository
@TransactionScoped
public interface AddressRepository extends CrudRepository<Address, Integer> {
}
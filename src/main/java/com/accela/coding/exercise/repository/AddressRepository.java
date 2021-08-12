package com.accela.coding.exercise.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.accela.coding.exercise.model.Address;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {

}
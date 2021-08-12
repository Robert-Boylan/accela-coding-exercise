package com.accela.coding.exercise.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.accela.coding.exercise.model.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

	@Override
	List<Person> findAll();

}
package com.accela.coding.exercise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.accela.coding.exercise.exception.AddressNotFoundException;
import com.accela.coding.exercise.exception.PersonNotFoundException;
import com.accela.coding.exercise.model.Address;
import com.accela.coding.exercise.model.Person;
import com.accela.coding.exercise.model.dto.AddressDTO;
import com.accela.coding.exercise.model.dto.PersonDTO;
import com.accela.coding.exercise.service.PersonService;

@RestController
@RequestMapping("v1/exercise")
public class PersonController {

	@Autowired
	private PersonService personService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(path = "/addPerson")
	public void addPerson(@RequestBody PersonDTO personDto) {
		this.personService.savePerson(personDto);
	}

	@PatchMapping(path = "/editPerson")
	public ResponseEntity<Person> editPerson(@RequestParam long id, @RequestBody PersonDTO personDto) throws PersonNotFoundException {
		Person person = this.personService.editPerson(id, personDto);
		return ResponseEntity.ok(person);
	}

	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping(path = "/deletePerson")
	public void deletePerson(@RequestParam long id) throws PersonNotFoundException {
		this.personService.deletePerson(id);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(path = "/addAddress")
	public void addAddress(@RequestParam long id, @RequestBody AddressDTO addressDTO) throws PersonNotFoundException {
		this.personService.addAddress(id, addressDTO);
	}

	@PatchMapping(path = "/editAddress")
	public ResponseEntity<Address> editAddress(@RequestParam long id, @RequestBody AddressDTO addressDTO) throws AddressNotFoundException {
		Address address = this.personService.editAddress(id, addressDTO);
		return ResponseEntity.ok(address);
	}

	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping(path = "/deleteAddress")
	public void deleteAddress(@RequestParam long id) throws AddressNotFoundException {
		this.personService.deleteAddress(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(path = "/count")
	public long count() {
		return this.personService.count();
	}

	@GetMapping(path = "/persons")
	public ResponseEntity<List<Person>> getPersons() {
		List<Person> persons = this.personService.getPersons();
		return ResponseEntity.ok(persons);
	}
}
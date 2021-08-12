package com.accela.coding.exercise.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accela.coding.exercise.exception.AddressNotFoundException;
import com.accela.coding.exercise.exception.PersonNotFoundException;
import com.accela.coding.exercise.model.Address;
import com.accela.coding.exercise.model.Person;
import com.accela.coding.exercise.model.dto.AddressDTO;
import com.accela.coding.exercise.model.dto.PersonDTO;
import com.accela.coding.exercise.repository.AddressRepository;
import com.accela.coding.exercise.repository.PersonRepository;
import com.google.common.annotations.VisibleForTesting;

@Service
public class PersonService {

	private PersonRepository personRepository;
	private AddressRepository addressRepository;
	private ModelMapper modelMapper;

	@Autowired
	public PersonService(PersonRepository personRepository, AddressRepository addressRepository) {
		this.personRepository = personRepository;
		this.addressRepository = addressRepository;
		this.modelMapper = new ModelMapper();
	}

	public void savePerson(PersonDTO personDto) {
		Person person = createPersonFromDto(personDto);
		this.personRepository.save(person);
	}

	public Person editPerson(long id, PersonDTO personDto) throws PersonNotFoundException {
		Person person = findPersonById(id);
		person.setFirstName(personDto.getFirstName());
		person.setLastName(personDto.getLastName());
		return this.personRepository.save(person);
	}

	public void deletePerson(long id) throws PersonNotFoundException {
		Person person = findPersonById(id);
		this.personRepository.delete(person);
	}

	public void addAddress(long id, AddressDTO addressDto) throws PersonNotFoundException {
		Person person = findPersonById(id);
		Address address = createAddressFromDto(addressDto);
		address.setPerson(person);
		this.addressRepository.save(address);
	}

	public Address editAddress(long id, AddressDTO addressDto) throws AddressNotFoundException {
		Address address = findAddressById(id);
		address.setStreet(addressDto.getStreet());
		address.setCity(addressDto.getCity());
		address.setState(addressDto.getState());
		address.setPostalCode(addressDto.getPostalCode());
		return this.addressRepository.save(address);
	}

	public void deleteAddress(long id) throws AddressNotFoundException {
		Address address = findAddressById(id);
		this.addressRepository.delete(address);
	}

	public long count() {
		return this.personRepository.count();
	}

	public List<Person> getPersons() {
		return this.personRepository.findAll();
	}

	private Person findPersonById(long id) throws PersonNotFoundException {
		Optional<Person> person = this.personRepository.findById(id);
		if(person.isEmpty()) {
			String message = String.format("Unable to find person with id %d", id);
			throw new PersonNotFoundException(message);
		}
		return person.get();
	}
	
	private Address findAddressById(long id) throws AddressNotFoundException {
		Optional<Address> address = this.addressRepository.findById(id);
		if(address.isEmpty()) {
			String message = String.format("Unable to find address with id %d", id);
			throw new AddressNotFoundException(message);
		}
		return address.get();
	}

	@VisibleForTesting
	protected Person createPersonFromDto(PersonDTO personDto) {
		return this.modelMapper.map(personDto, Person.class);
	}

	@VisibleForTesting
	protected Address createAddressFromDto(AddressDTO addressDto) {
		return this.modelMapper.map(addressDto, Address.class);
	}
}
package com.accela.coding.exercise.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.accela.coding.exercise.exception.AddressNotFoundException;
import com.accela.coding.exercise.exception.PersonNotFoundException;
import com.accela.coding.exercise.model.Address;
import com.accela.coding.exercise.model.Person;
import com.accela.coding.exercise.model.dto.AddressDTO;
import com.accela.coding.exercise.model.dto.PersonDTO;
import com.accela.coding.exercise.repository.AddressRepository;
import com.accela.coding.exercise.repository.PersonRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PersonServiceTest {

	@Autowired
	private PersonService personService;

	@MockBean
	private PersonRepository personRepository;
	@MockBean
	private AddressRepository addressRepository;

	@Test
	public void testEditPerson() throws PersonNotFoundException {
		Person originalPerson = new Person();
		originalPerson.setFirstName("John");
		originalPerson.setLastName("Smith");
		Optional<Person> optionalPerson = Optional.of(originalPerson);
		PersonDTO personDto = new PersonDTO("Sean", "Reilly");
		Person expectedPerson = this.personService.createPersonFromDto(personDto);
		
		when(this.personRepository.findById(anyLong())).thenReturn(optionalPerson);
		when(this.personRepository.save(any(Person.class))).thenReturn(expectedPerson);

		Person actualPerson = this.personService.editPerson(1, personDto);
		assertEquals(expectedPerson.toString(), actualPerson.toString());
	}

	@Test
	public void testEditPersonThrowsNotFoundException() throws PersonNotFoundException {
		when(this.personRepository.findById(anyLong())).thenReturn(Optional.empty());

		PersonDTO personDto = new PersonDTO("Sean", "Reilly");

		assertThrows(PersonNotFoundException.class, () -> {
			this.personService.editPerson(1, personDto);
		}
				);
	}
	
	@Test
	public void testEditAddress() throws AddressNotFoundException {
		Address originalAddress = new Address();
		originalAddress.setStreet("STREET1");
		originalAddress.setCity("CITY1");
		originalAddress.setState("STATE1");
		originalAddress.setPostalCode("POSTAL CODE1");
		Optional<Address> optionalAddress = Optional.of(originalAddress);
		AddressDTO addresssDto = new AddressDTO("STREET2", "CITY2", "STATE2", "POSTAL CODE2");
		Address expectedAddress = this.personService.createAddressFromDto(addresssDto);
		
		when(this.addressRepository.findById(anyLong())).thenReturn(optionalAddress);
		when(this.addressRepository.save(any(Address.class))).thenReturn(expectedAddress);

		Address actualAddress = this.personService.editAddress(1, addresssDto);
		assertEquals("STREET2", actualAddress.getStreet());
		assertEquals("CITY2", actualAddress.getCity());
		assertEquals("STATE2", actualAddress.getState());
		assertEquals("POSTAL CODE2", actualAddress.getPostalCode());
	}

	@Test
	public void testCreatePersonFromDto() {
		PersonDTO personDto = new PersonDTO("John", "Smith");
		Person person = this.personService.createPersonFromDto(personDto);
		assertNull(person.getId());
		assertEquals("John", person.getFirstName());
		assertEquals("Smith", person.getLastName());
		assertEquals(0, person.getAddresses().size());
	}

	@Test
	public void testCreateAddressFromDto() {
		AddressDTO addressDto = new AddressDTO("STREET", "CITY", "STATE", "POSTAL CODE");
		Address address = this.personService.createAddressFromDto(addressDto);
		assertNull(address.getId());
		assertEquals("STREET", address.getStreet());
		assertEquals("CITY", address.getCity());
		assertEquals("STATE", address.getState());
		assertEquals("POSTAL CODE", address.getPostalCode());
		assertNull(address.getPerson());
	}
}
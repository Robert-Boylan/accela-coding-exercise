package com.accela.coding.exercise.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.accela.coding.exercise.exception.AddressNotFoundException;
import com.accela.coding.exercise.exception.PersonNotFoundException;
import com.accela.coding.exercise.model.Address;
import com.accela.coding.exercise.model.Person;
import com.accela.coding.exercise.model.dto.AddressDTO;
import com.accela.coding.exercise.model.dto.PersonDTO;
import com.accela.coding.exercise.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PersonController.class)
@ContextConfiguration(classes = { PersonService.class, PersonController.class })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonControllerIT {

	private static final String API_URL = "/v1/exercise";
	private static final String ADD_PERSON_URL = API_URL + "/addPerson";
	private static final String EDIT_PERSON_URL = API_URL + "/editPerson";
	private static final String DELETE_PERSON_URL = API_URL + "/deletePerson";
	private static final String ADD_ADDRESS_URL = API_URL + "/addAddress";
	private static final String EDIT_ADDRESS_URL = API_URL + "/editAddress";
	private static final String DELETE_ADDRESS_URL = API_URL + "/deleteAddress";
	private static final String GET_COUNT_URL = API_URL + "/count";
	private static final String GET_PERSONS_URL = API_URL + "/persons";

	@MockBean
	private PersonService personService;

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper objectMapper;
	private PersonDTO mockPersonDto;
	private AddressDTO mockAddressDto;

	@BeforeAll
	public void setup() {
		this.objectMapper = new ObjectMapper();
		this.mockPersonDto = new PersonDTO("John", "Smith");
		this.mockAddressDto = new AddressDTO("Street", "City", "State", "Postal Code");
	}

	@Test
	public void testAddPersonReturnsIsCreated() throws Exception {
		String personDtoJson = this.objectMapper.writeValueAsString(this.mockPersonDto);

		this.mockMvc.perform(post(ADD_PERSON_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(personDtoJson))
		.andExpect(status().isCreated());
	}

	@Test
	public void testEditPersonReturnsOK() throws Exception {
		String personDtoJson = this.objectMapper.writeValueAsString(this.mockPersonDto);

		Person expectedPerson = new Person();
		expectedPerson.setFirstName(this.mockPersonDto.getFirstName());
		expectedPerson.setLastName(this.mockPersonDto.getLastName());
		when(this.personService.editPerson(1, this.mockPersonDto)).thenReturn(expectedPerson);

		String responseValue = this.mockMvc.perform(patch(EDIT_PERSON_URL)
				.param("id", "1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(personDtoJson))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		Person actualPerson = this.objectMapper.readValue(responseValue, Person.class);
		assertEquals(expectedPerson.toString(), actualPerson.toString());
	}

	@Test
	public void testEditPersonReturnsNotFound() throws Exception {
		String personDtoJson = this.objectMapper.writeValueAsString(this.mockPersonDto);

		when(this.personService.editPerson(anyLong(), any(PersonDTO.class))).thenThrow(PersonNotFoundException.class);

		this.mockMvc.perform(patch(EDIT_PERSON_URL)
				.param("id", "1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(personDtoJson))
		.andExpect(status().isNotFound());
	}

	@Test
	public void testDeletePersonReturnsOK() throws Exception {
		doNothing().when(this.personService).deletePerson(anyLong());

		this.mockMvc.perform(delete(DELETE_PERSON_URL)
				.param("id", "1"))
		.andExpect(status().isOk());
	}

	@Test
	public void testDeletePersonReturnsNotFound() throws Exception {
		doThrow(PersonNotFoundException.class).when(this.personService).deletePerson(anyLong());

		this.mockMvc.perform(delete(DELETE_PERSON_URL)
				.param("id", "1"))
		.andExpect(status().isNotFound());
	}

	@Test
	public void testAddAddressReturnsIsCreated() throws Exception {
		String addressDtoJson = this.objectMapper.writeValueAsString(this.mockAddressDto);

		this.mockMvc.perform(post(ADD_ADDRESS_URL)
				.param("id", "1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(addressDtoJson))
		.andExpect(status().isCreated());
	}

	@Test
	public void testAddAddressReturnsNotFound() throws Exception {
		String addressDtoJson = this.objectMapper.writeValueAsString(this.mockAddressDto);

		doThrow(PersonNotFoundException.class).when(this.personService).addAddress(anyLong(), any(AddressDTO.class));

		this.mockMvc.perform(post(ADD_ADDRESS_URL)
				.param("id", "1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(addressDtoJson))
		.andExpect(status().isNotFound());
	}

	@Test
	public void testEditAddressReturnsIsOk() throws Exception {
		String addressDtoJson = this.objectMapper.writeValueAsString(this.mockAddressDto);

		Address expectedAddress = new Address();
		expectedAddress.setStreet(this.mockAddressDto.getStreet());
		expectedAddress.setCity(this.mockAddressDto.getCity());
		expectedAddress.setState(this.mockAddressDto.getState());
		expectedAddress.setPostalCode(this.mockAddressDto.getPostalCode());
		when(this.personService.editAddress(1, this.mockAddressDto)).thenReturn(expectedAddress);

		String responseValue = this.mockMvc.perform(patch(EDIT_ADDRESS_URL)
				.param("id", "1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(addressDtoJson))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		Address actualAddress = this.objectMapper.readValue(responseValue, Address.class);
		assertEquals(expectedAddress.toString(), actualAddress.toString());
	}

	@Test
	public void testEditAddressReturnsNotFound() throws Exception {
		String addressDtoJson = this.objectMapper.writeValueAsString(this.mockAddressDto);

		when(this.personService.editAddress(1, this.mockAddressDto)).thenThrow(AddressNotFoundException.class);

		this.mockMvc.perform(patch(EDIT_ADDRESS_URL)
				.param("id", "1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(addressDtoJson))
		.andExpect(status().isNotFound());
	}

	@Test
	public void testDeleteAddressReturnsIsOk() throws Exception {
		this.mockMvc.perform(delete(DELETE_ADDRESS_URL)
				.param("id", "1"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteAddressReturnsNotFound() throws Exception {
		doThrow(AddressNotFoundException.class).when(this.personService).deleteAddress(anyLong());
		
		this.mockMvc.perform(delete(DELETE_ADDRESS_URL)
				.param("id", "1"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void testGetCount() throws Exception {
		when(this.personService.count()).thenReturn(1L);
		
		long actualCount = Long.parseLong(this.mockMvc.perform(get(GET_COUNT_URL))
		.andExpect(status().isOk())
		.andReturn()
		.getResponse()
		.getContentAsString());
		
		assertEquals(1L, actualCount);
	}
	
	@Test
	public void testGetPersons() throws Exception {
		when(this.personService.getPersons()).thenReturn(newPersonList());
		
		String responseValue = this.mockMvc.perform(get(GET_PERSONS_URL))
		.andExpect(status().isOk())
		.andReturn()
		.getResponse()
		.getContentAsString();
		
		List<Person> actualPersons = Arrays.asList(this.objectMapper.readValue(responseValue, Person[].class));
		assertEquals(1, actualPersons.size());
	}
	
	private List<Person> newPersonList() {
		List<Person> persons = new ArrayList<>();
		persons.add(newPerson());
		return persons;
	}
	
	private Person newPerson() {
		Person person = new Person();
		person.setFirstName("FIRST_NAME");
		person.setLastName("LAST_NAME");
		return person;
	}
}
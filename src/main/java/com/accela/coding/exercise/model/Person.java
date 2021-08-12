package com.accela.coding.exercise.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter
@NoArgsConstructor
@ToString
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	@Setter
	private String firstName;
	@Column
	@Setter
	private String lastName;
	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
	@Setter
	private Set<Address> addresses = new HashSet<>();

	public void addAddress(Address address) {
		this.addresses.add(address);
	}
}
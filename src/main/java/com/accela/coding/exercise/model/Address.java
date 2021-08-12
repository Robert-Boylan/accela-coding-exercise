package com.accela.coding.exercise.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter
@NoArgsConstructor
@ToString
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	@Setter
	private String street;
	@Column
	@Setter
	private String city;
	@Column
	@Setter
	private String state;
	@Column
	@Setter
	private String postalCode;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PERSON_ID", referencedColumnName = "ID", nullable = false)
	@Setter
	@JsonIgnoreProperties({ "addresses" }) 
	private Person person;
}
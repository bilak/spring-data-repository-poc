package com.github.bilak.poc.springdatarepository.persistence.jpa.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by lvasek on 21/11/2016.
 */
@Entity
public class TestEntity {

	@Id
	private String id;

	private String email;

	private String firstName;

	private String lastName;

	public TestEntity() {
	}

	public TestEntity(String id, String email, String firstName, String lastName) {
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}

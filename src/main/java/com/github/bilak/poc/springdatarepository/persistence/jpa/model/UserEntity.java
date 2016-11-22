package com.github.bilak.poc.springdatarepository.persistence.jpa.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Created by lvasek on 21/11/2016.
 */
@Entity
public class UserEntity {

	@Id
	private String id;
	private String username;
	private String userEmail;

	public UserEntity() {
	}

	public UserEntity(String id, String username, String userEmail) {
		this.id = id;
		this.username = username;
		this.userEmail = userEmail;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, userEmail);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final UserEntity other = (UserEntity) obj;
		return Objects.equals(this.id, other.id)
				&& Objects.equals(this.username, other.username)
				&& Objects.equals(this.userEmail, other.userEmail);
	}

	@Override
	public String toString() {
		return "UserEntity{" +
				"id='" + id + '\'' +
				", username='" + username + '\'' +
				", userEmail='" + userEmail + '\'' +
				'}';
	}
}

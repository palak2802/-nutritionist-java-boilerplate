package com.stackroute.foodservice.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/*
 * Please note that this class is annotated with @Document annotation
 * @Document identifies a domain object to be persisted to MongoDB.
 *  
 */
@Document
public class User {

	/*
	 * This class should have six fields (userId,firstName,
	 * lastName,contact,email,createdAt). Out of these six fields, the field
	 * userId should be annotated with @Id (This annotation explicitly specifies the document
	 * identifier). This class should also contain the getters and setters for the
	 * fields, along with the no-arg , parameterized constructor and toString
	 * method.The value of createdAt should not be accepted from the user but
	 * should be always initialized with the system date.
	 */
	@Id
	private String userId;
	private String firstName;
	private String lastName;
	private String contact;
	private String email;
	@JsonSerialize(using = ToStringSerializer.class)
	private LocalDateTime createdAt;
	
    public User() {
    	this.createdAt = LocalDateTime.now();
    }

	public User(String userId, String firstName, String lastName, String contact, String email, LocalDateTime createdAt) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contact = contact;
		this.email = email;
		this.createdAt = createdAt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt() {
		this.createdAt = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "UserProfile [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", contact="
				+ contact + ", email=" + email + ", createdAt=" + createdAt + "]";
	}
    
}

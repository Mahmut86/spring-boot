package com.ilknur.task.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@NotNull
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name="isAuthenticate")
	private Boolean isAuthenticate=false;

	public User() {

	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getAuthenticate() {
		return isAuthenticate;
	}

	public void setAuthenticate(Boolean authenticate) {
		isAuthenticate = authenticate;
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
}

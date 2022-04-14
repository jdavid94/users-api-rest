package com.jesussuarez.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;




@Entity 
@Table(name="users")
public class User implements Serializable { 
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;	
	
	@Email 
	@Column(nullable=false, unique=true)
	private String email;
	
	private String password;
	
	@JsonIgnoreProperties(value={"users", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Phone> phones;	
		
	@Temporal(TemporalType.DATE)
	@Column(name="create_at")
	private Date createAt;
	
	@Temporal(TemporalType.DATE)
	@Column(name="modified_at")
	private Date modifiedeAt;
	
	@Column(name="is_active")
	private Boolean isActive;	
	
	@JsonIgnoreProperties(value={"users", "hibernateLazyInitializer", "handler"}, allowSetters = true) 
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private List<Role> roles;
		
		
	public User() {
		createAt = new Date();
		isActive = true;
		this.phones = new ArrayList<>();
		this.roles = new ArrayList<Role>();
	}
	
	
	public User(Long id, String name, @Email String email, String password, List<Phone> phones, Date createAt,
			Date modifiedeAt, Boolean isActive, List<Role> roles) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phones = phones;
		this.createAt = createAt;
		this.modifiedeAt = modifiedeAt;
		this.isActive = isActive;
		this.roles = roles;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public List<Phone> getPhones() {
		return phones;
	}


	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}


	public Date getCreateAt() {
		return createAt;
	}



	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}



	public Date getModifiedeAt() {
		return modifiedeAt;
	}



	public void setModifiedeAt(Date modifiedeAt) {
		this.modifiedeAt = modifiedeAt;
	}



	public Boolean getIsActive() {
		return isActive;
	}



	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	
	public List<Role> getRoles() {
		return roles;
	}


	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

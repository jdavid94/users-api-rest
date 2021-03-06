package com.jesussuarez.springboot.backend.apirest.models.entity;

import java.io.Serializable;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;


@Entity
@Table(name="roles") 
public class Role implements Serializable { 
	
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
		
	
	private String type;
	
			
	
	public Role(Long id, String type) {
		super();
		this.id = id;
		this.type = type;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}




	public String getType() {
		return type;
	}




	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}

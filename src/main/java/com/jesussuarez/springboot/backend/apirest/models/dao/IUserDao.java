package com.jesussuarez.springboot.backend.apirest.models.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jesussuarez.springboot.backend.apirest.models.entity.User;

public interface IUserDao extends CrudRepository<User, Long>, JpaRepository<User, Long> {
	
	public User findByEmail(String email);
	
	@Query("select u from User u where u.email=?1")
	public User findByEmail2(String email);
	
}
	
	

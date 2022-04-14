package com.jesussuarez.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.jesussuarez.springboot.backend.apirest.models.entity.User;

public interface IUserService {
	
	public User findByEmail(String email); 
	public List<User> findAll();
	public Page<User> findAll(Pageable pageable);
	public User findBy(Long id);
	public User save(User user);
	public void delete(Long id);	
	
}

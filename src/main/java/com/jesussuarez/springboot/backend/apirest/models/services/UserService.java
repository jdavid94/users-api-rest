package com.jesussuarez.springboot.backend.apirest.models.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import com.jesussuarez.springboot.backend.apirest.models.dao.IUserDao;
import com.jesussuarez.springboot.backend.apirest.models.entity.User;


@Service 
public class UserService implements IUserService, UserDetailsService {
	
	private Logger logger = LoggerFactory.getLogger(UserService.class);	
		
	@Autowired
	private IUserDao userDao;
	
	@Override
	@Transactional(readOnly=true) 
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userDao.findByEmail(email);
		
		if (user == null) {
			logger.error("User Does Not Exist!");
			throw new UsernameNotFoundException("User Does Not Exist!");
		}
		
		List<GrantedAuthority> authorities = user.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getType()))
				.peek(authority -> logger.info("Role : " + authority.getAuthority()))
				.collect(Collectors.toList());		
						
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getIsActive(), true, true, true, authorities);	
	}
	

	@Override
	@Transactional(readOnly=true) 
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	@Transactional(readOnly = true )
	public List<User> findAll() {	
		return (List<User>) userDao.findAll();
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		return userDao.findAll(pageable);
	}

	@Override
	public User findBy(Long id) {
		return userDao.findById(id).orElse(null);
	}

	@Override
	public User save(User user) {		
		return userDao.save(user);
	}

	@Override
	public void delete(Long id) {
		userDao.deleteById(id);
		
	}

}
;
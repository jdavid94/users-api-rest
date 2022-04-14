package com.jesussuarez.springboot.backend.apirest;



import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jesussuarez.springboot.backend.apirest.models.dao.IUserDao;
import com.jesussuarez.springboot.backend.apirest.models.entity.Phone;
import com.jesussuarez.springboot.backend.apirest.models.entity.Role;
import com.jesussuarez.springboot.backend.apirest.models.entity.User;
import com.jesussuarez.springboot.backend.apirest.models.services.IUserService;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserBackendServiceApplicationTests {
	
	@Autowired
	IUserService usaurioService;
	
	@Mock
	IUserDao userDao;
			
	@Test
	public void createUser()	{
		List<Phone> phones = new ArrayList<Phone>();
		Phone phone = new Phone(Long.valueOf(1),"1234567","1","57");
		phones.add(phone);
		List<Role> roles = new ArrayList<Role>();
		Role role = new Role(Long.valueOf(1), "USER_ROLE");
		roles.add(role);
		User mockUser = new User(Long.valueOf(1),"Test", "test@gmail.com", "1234", phones, null, null, true, roles);
		
		usaurioService.save(mockUser);
		verify(userDao, times(1)).save(mockUser);
	}
	
	@Test	public void findAll()	{
		List<User> list = new ArrayList<User>();
		User mockUser = new User(Long.valueOf(1),"Test", "test@gmail.com", "1234", null, null, null, true, null);
		User mockUser2 = new User(Long.valueOf(2),"Test2", "tes2t@gmail.com", "1234", null, null, null, true, null);
		User mockUser3 = new User(Long.valueOf(3),"Test3", "test3@gmail.com", "1234", null, null, null, true, null);
		
	    list.add(mockUser);
		list.add(mockUser2);
		list.add(mockUser3);	
		
		
		when(userDao.findAll()).thenReturn(list);
		
		// Test
		List<User> userList = usaurioService.findAll();
		
		assertEquals(3, userList.size());		
			
	}


}

package com.jesussuarez.springboot.backend.apirest.controllers;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.jesussuarez.springboot.backend.apirest.models.entity.User;
import com.jesussuarez.springboot.backend.apirest.models.services.IUserService;

@RestController
@RequestMapping("/api")
public class UserRestController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
    protected AuthenticationManager authenticationManager;
	
		
	@GetMapping("/users")
	public List<User> index() {
		return userService.findAll();		
	}
	
	
	@GetMapping("/users/{email}")	
	public ResponseEntity<?> show(@PathVariable String email) {
		User user = null;
		Map<String, Object> response = new HashMap<>();
		try {
			user = userService.findByEmail(email);	
			
		} catch(DataAccessException e) {
			response.put("message", "Error with DataBase Fin by ID Query");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}			
		if (user == null) {
			response.put("message", "Email: ".concat(email.toString().concat(" Does not Exist in BD!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}		
		return new ResponseEntity<User>(user, HttpStatus.OK);	
	}
	

	@PostMapping("/users")
	public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result, HttpServletRequest request) {	
		User newUser = new User();			
		Map<String, Object> response = new HashMap<>();		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> {
						return "Campo '" + err.getField() +"' "+ err.getDefaultMessage();
					})
					.collect(Collectors.toList());
			response.put("Errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}		
		try {		
			newUser = userService.save(user);
		} catch (DataAccessException e) {
			response.put("Message", "Error with DataBase Insert Query");			
			if (e.getLocalizedMessage().contains("PUBLIC.UK_6DOTKOTT2KJSP8VW4D0M25FB7_INDEX_4 ON PUBLIC.USERS(EMAIL)")) {
				response.put("Detail", "Email already Exist!!");
			}
			//response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		try {
            request.login(newUser.getEmail(), newUser.getPassword());
            authenticateUserAndSetSession(newUser, request);            
        } catch (ServletException e) {            
        	
        }		
		response.put("message", "User created successfully");
		response.put("User", newUser);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);		
	}
	
	private void authenticateUserAndSetSession(User user, HttpServletRequest request) {
        String username = user.getEmail();
        String password = user.getPassword();       
                
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        authToken.setDetails(new WebAuthenticationDetails(request));
        
        Authentication authentication = authenticationManager.authenticate(authToken);        
        SecurityContextHolder.getContext().setAuthentication(authentication);        
    }
	

	@PutMapping("/user/{id}")	
	public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id) {
		User userTemp = userService.findBy(id);
		User userrUpdate = null;
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> {
						return "Campo '" + err.getField() +"' "+ err.getDefaultMessage();
					})
					.collect(Collectors.toList());
			response.put("Errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		
		if (userTemp == null) {
			response.put("message", "Error Update, User with ID: ".concat(id.toString().concat(" Does not Exist in BD!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}	
		try {
			userTemp.setName(user.getName());
			userTemp.setEmail(user.getEmail());
			userTemp.setName(user.getName());
			userTemp.setPassword(user.getPassword());	
			userTemp.setPhones(user.getPhones());
			userTemp.setModifiedeAt(new Date());
										
			userrUpdate = userService.save(userTemp);	
			
		} catch (DataAccessException e) {
			response.put("message", "Error with DataBase Update Query");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("message", "User updated successfully");
		response.put("User", userrUpdate);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);		
	}
		
		
	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			userService.delete(id);
		} catch (DataAccessException e) {
			response.put("message", "Error with DataBase Delete Query");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("message", "User deleted successfully");	
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);				
	}

}

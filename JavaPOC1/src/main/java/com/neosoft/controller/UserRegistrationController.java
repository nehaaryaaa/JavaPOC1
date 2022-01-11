package com.neosoft.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.neosoft.model.User;
import com.neosoft.repository.UserRegistrationRepository;

@RestController
public class UserRegistrationController {

	@Autowired
	private UserRegistrationRepository userRepo;
	
	@PostMapping("/add/user")
	public User addUser(@RequestBody User user) {
		return userRepo.save(user);
	}
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return userRepo.findAll();
	}
	
	@PutMapping("/update/user/{id}")
	public Optional<User> updateUser(@Valid @RequestBody User user,@PathVariable long id) {
		return userRepo.findById(id).map(u -> {
			u.setName(user.getName());
			u.setSurname(user.getSurname());
			u.setPincode(user.getPincode());
			u.setDob(user.getDob());
			u.setDoj(user.getDoj());
			return userRepo.save(u);
		});
	}
	
	@DeleteMapping("/delete/user/{id}")					//for hard deleting remove the @SQLDelete and @Where annotation from model class
	public void deleteUser(@PathVariable long id) {		//for soft deleting add the @SQLDelete and @Where annotation in model class
		 userRepo.deleteById(id);
	}
	
	@GetMapping("/searchBy/{name}/or/{surname}/or/{pincode}")
	public List<User> getUserByNameOrSurnameOrPincode(@PathVariable String name, @PathVariable String surname, @PathVariable long pincode){
		return userRepo.findByNameOrSurnameOrPincode(name,surname,pincode);
	}
	
	@GetMapping("/sortBy/dob")
	public List<User> getUserOrderByDob(){
		return userRepo.findByOrderByDob();
	}
	
	@GetMapping("/sortBy/doj")
	public List<User> getUserOrderByDoj(){
		return userRepo.findByOrderByDojDesc();
	}
}

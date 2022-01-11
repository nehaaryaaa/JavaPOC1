package com.neosoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neosoft.model.User;

public interface UserRegistrationRepository extends JpaRepository<User, Long>{

	List<User> findByNameOrSurnameOrPincode(String name, String surname, long pincode);

	List<User> findByOrderByDob();
	
	List<User> findByOrderByDojDesc();

}

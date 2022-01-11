package com.neosoft;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neosoft.controller.UserRegistrationController;
import com.neosoft.model.User;
import com.neosoft.repository.UserRegistrationRepository;

@WebMvcTest(UserRegistrationController.class)
class JavaPoc1ApplicationTests {
	
	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper; // It provides functionality for reading and writing JSON, either to and from basic POJOs
	
	@MockBean
	UserRegistrationRepository  userRepo;
	
	User user1 = new User(1L,"Neha","Arya",411015,new SimpleDateFormat("2000-01-11"),new SimpleDateFormat("2021-08-18"), false);
	User user2 = new User(2L,"Snehankit","Konde",411001,new SimpleDateFormat("1996-12-25"),new SimpleDateFormat("2021-12-29"), false);
	User user3 = new User(3L,"Vaibhav","Bankar",411011,new SimpleDateFormat("2000-07-19"),new SimpleDateFormat("2020-05-19"), false);
	
	
	@Test
	void contextLoads() {
		
	}
	
	@Test
	public void getAllUsersSuccess() throws Exception {
		List<User> users = new ArrayList<>(Arrays.asList(user1,user2,user3));
		
		Mockito.when(userRepo.findAll()).thenReturn(users);
		//When findAll called then ready with records (No DB calls)
		mockMvc.perform(MockMvcRequestBuilders
				.get("/users")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[2].name", is("Vaibhav")));
	}
	
		
	@Test
	public void createUser_success() throws Exception {
		User user = User.builder()
					.name("Pushpa")
					.surname("Yadav")
					.pincode(411099)
					//.dob(new SimpleDateFormat("1995-10-17"))
					//.doj(new SimpleDateFormat("2021-07-22"))
					.deleted(false)
					.build();
		
		Mockito.when(userRepo.save(user)).thenReturn(user);
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/add/user")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(user));
		
		mockMvc.perform(mockRequest)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.name", is("Pushpa")));		
	}
	
	 /*@Test
	    public void updatePatientRecord_success() throws Exception {
	        PatientRecord updatedRecord = PatientRecord.builder()
	                .patientId(1l)
	                .name("Rayven Zambo")
	                .age(23)
	                .address("Cebu Philippines")
	                .build();

	        Mockito.when(patientRecordRepository.findById(RECORD_1.getPatientId()))
	        .thenReturn(Optional.of(RECORD_1));
	        
	        Mockito.when(patientRecordRepository.save(updatedRecord)).thenReturn(updatedRecord);

	        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/patient")
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON)
	                .content(this.mapper.writeValueAsString(updatedRecord));

	        mockMvc.perform(mockRequest)
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$", notNullValue()))
	                .andExpect(jsonPath("$.name", is("Rayven Zambo")));
	    }*/
	@Test
	public void updateUser_success() throws Exception {
		User updatedUser = User.builder()
				.id(1L)
				.name("Avantika")
				.surname("Patil")
				.pincode(411015)
				.dob(new SimpleDateFormat("2000-01-11"))
				.doj(new SimpleDateFormat("2021-08-18"))
				.deleted(false)
				.build();
		Mockito.when(userRepo.findById(user1.getId())).thenReturn(Optional.of(user1));
		Mockito.when(userRepo.save(updatedUser)).thenReturn(updatedUser);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/update/user/")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(updatedUser));
		mockMvc.perform(mockRequest)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.name", is("Avantika")));
	}

}

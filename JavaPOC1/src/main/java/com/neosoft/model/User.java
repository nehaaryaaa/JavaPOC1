package com.neosoft.model;

import java.text.SimpleDateFormat;
import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="user_registration")
@SQLDelete(sql = "UPDATE user_registration SET deleted=true WHERE id=?") //It will override the delete request
@Where(clause = "deleted=false")    // for viewing the soft deleted members too remove @Where annotation as it will show only non-deleted ones
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Size(max = 65)
	private String name;
	
	@Size(max = 65)
	private String surname;
	
	private long pincode;
	
	@Temporal(TemporalType.DATE)
	private SimpleDateFormat dob;
	
	@Temporal(TemporalType.DATE)
	private SimpleDateFormat doj;
	
	private boolean deleted;
	
}

package org.ssglobal.training.codes.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name="user_applicants")
public class UserApplicants implements Serializable {
	private static final long serialVersionUID = -5626314843978099497L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="applicant_id")
	private Integer applicantId;

	private String address;

	@Column(name="birth_date")
	private LocalDate birthDate;

	@Column(name="civil_status")
	private String civilStatus;

	@Column(name="contact_no")
	private String contactNo;

	@Column(name="date_registered")
	private LocalDateTime dateRegistered;

	private String email;

	@Column(name="first_name")
	private String firstName;

	private String gender;
	
	@Column(name = "valid_id_picture")
	private String validIdPicture;
	
	@Column(name = "is_validated")
	private Boolean isValidated;
	
	@Column(name = "is_activated")
	private Boolean isActivated;

	@Column(name="last_name")
	private String lastName;

	@Column(name="middle_name")
	private String middleName;

	private String nationality;

	private String password;

	private String[] socials;

	@Column(name="user_type")
	private String userType;

	private String username;
}

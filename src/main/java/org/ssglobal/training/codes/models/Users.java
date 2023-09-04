package org.ssglobal.training.codes.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name="users")
public class Users implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private Integer userId;

	@Column(name="active_deactive")
	private Boolean activeDeactive;

	@Column(name = "is_validated")
	private Boolean isValidated;
	
	@Column(name="active_status")
	private Boolean activeStatus;

	private String address;

	@Column(name="birth_date")
	private LocalDate birthDate;

	@Column(name="civil_status")
	private String civilStatus;

	@Column(name="contact_no")
	private String contactNo;

	@Column(name="date_created")
	private LocalDateTime dateCreated;

	private String email;

	@Column(name="first_name")
	private String firstName;

	private String gender;

	@Column(name="image")
	private String image;
	
	@Column(name = "valid_id_picture")
	private String validIdPicture;

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

	// bi-directional many-to-one association to Administrator
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@JsonBackReference
	private List<Administrator> administrators;

	// bi-directional many-to-one association to Farmer
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@JsonBackReference
	private List<Farmer> farmers;

	// bi-directional many-to-one association to Supplier
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@JsonBackReference
	private List<Supplier> suppliers;

	// bi-directional one-to-one association to UserToken
	@OneToOne(mappedBy = "user")
	@JsonBackReference
	private UserToken userToken;
	
	// bi-directional many-to-one association to Farmer
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@JsonBackReference
	private List<UserNotifications> userNotifications;
}

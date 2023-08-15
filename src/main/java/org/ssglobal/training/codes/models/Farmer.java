package org.ssglobal.training.codes.models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name="farmer")
public class Farmer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="farmer_id")
	private Integer farmerId;

	@Column(name="crop_specialization_id")
	private Integer[] cropSpecializationId;

	//bi-directional many-to-one association to CourseEnrolled
	@OneToMany(mappedBy="farmer", fetch=FetchType.EAGER)
	@JsonBackReference
	private List<CourseEnrolled> courseEnrolleds;

	//bi-directional many-to-one association to CropDetail
	@OneToMany(mappedBy="farmer", fetch=FetchType.EAGER)
	@JsonBackReference
	private List<CropDetail> cropDetails;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonManagedReference
	private Users user;

	//bi-directional many-to-one association to FarmerComplaint
	@OneToMany(mappedBy="farmer", fetch=FetchType.EAGER)
	@JsonBackReference
	private List<FarmerComplaint> farmerComplaints;

	//bi-directional many-to-one association to PostAdvertisementRespons
	@OneToMany(mappedBy="farmer", fetch=FetchType.EAGER)
	@JsonBackReference
	private List<PostAdvertisementResponse> postAdvertisementResponses;
}
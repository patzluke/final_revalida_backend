package org.ssglobal.training.codes.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name="course_enrolled")
public class CourseEnrolled implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="enrollment_id")
	private Integer enrollmentId;

	@Temporal(TemporalType.DATE)
	@Column(name="end_of_enrollment")
	private Date endOfEnrollment;

	@Temporal(TemporalType.DATE)
	@Column(name="enrollment_date")
	private Date enrollmentDate;

	//bi-directional many-to-one association to Course
	@ManyToOne
	@JoinColumn(name="course_id")
	@JsonManagedReference
	private Course course;

	//bi-directional many-to-one association to Farmer
	@ManyToOne
	@JoinColumn(name="farmer_id")
	@JsonManagedReference
	private Farmer farmer;
}
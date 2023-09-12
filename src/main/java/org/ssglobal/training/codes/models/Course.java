package org.ssglobal.training.codes.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name="course")
public class Course implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="course_id")
	private Integer courseId;

	@Column(name="course_name")
	private String courseName;

	private String description;

	@Column(name="duration_in_days")
	private Integer durationInDays;
	
	@Column(name="link")
	private String link;
	
	@Column(name="active_deactive")
	private boolean activeDeactive;

	//bi-directional many-to-one association to CourseEnrolled
	@OneToMany(mappedBy="course", fetch=FetchType.EAGER)
	@JsonBackReference
	private List<CourseEnrolled> courseEnrolleds;
}
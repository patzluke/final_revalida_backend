package org.ssglobal.training.codes.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name="course")
public class Course implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="course_id")
	private Integer courseId;

	@Column(name="course_name")
	private String courseName;

	private String description;

	@Temporal(TemporalType.DATE)
	@Column(name="duration_in_days")
	private Date durationInDays;

	//bi-directional many-to-one association to CourseEnrolled
	@OneToMany(mappedBy="course", fetch=FetchType.EAGER)
	private List<CourseEnrolled> courseEnrolleds;
}
package org.ssglobal.training.codes.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name="crop_specialization")
public class CropSpecialization implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="crop_specialization_id")
	private Integer cropSpecializationId;

	@Column(name="specialization_name")
	private String specializationName;

	//bi-directional many-to-one association to PostAdvertisement
	@OneToMany(mappedBy="cropSpecialization", fetch=FetchType.EAGER)
	private List<PostAdvertisement> postAdvertisements;
}
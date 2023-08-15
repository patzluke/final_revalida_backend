package org.ssglobal.training.codes.models;

import java.io.Serializable;

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

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name="post_advertisement")
public class PostAdvertisement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="post_id")
	private Integer postId;

	@Column(name="crop_image")
	private String cropImage;

	@Column(name="crop_name")
	private String cropName;

	private String description;

	private Integer quantity;

	//bi-directional many-to-one association to CropSpecialization
	@ManyToOne
	@JoinColumn(name="crop_specialization_id")
	private CropSpecialization cropSpecialization;

	//bi-directional many-to-one association to PostAdvertisementRespons
	@OneToMany(mappedBy="postAdvertisement", fetch=FetchType.EAGER)
	private List<PostAdvertisementResponse> postAdvertisementResponses;
}
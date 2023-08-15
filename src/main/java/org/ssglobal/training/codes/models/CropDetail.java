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

import com.fasterxml.jackson.annotation.JsonBackReference;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name="crop_details")
public class CropDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="crop_id")
	private Integer cropId;

	@Column(name="crop_image")
	private String cropImage;

	@Column(name="crop_name")
	private String cropName;

	private double price;

	private Integer quantity;

	//bi-directional many-to-one association to Farmer
	@ManyToOne
	@JoinColumn(name="farmer_id")
	private Farmer farmer;

	//bi-directional many-to-one association to CropOrder
	@OneToMany(mappedBy="cropDetail", fetch=FetchType.EAGER)
	@JsonBackReference
	private List<CropOrder> cropOrders;
}
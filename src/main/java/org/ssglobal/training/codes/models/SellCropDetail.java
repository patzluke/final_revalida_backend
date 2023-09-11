package org.ssglobal.training.codes.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
import com.fasterxml.jackson.annotation.JsonManagedReference;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name="sell_crop_details")
public class SellCropDetail implements Serializable {
	private static final long serialVersionUID = 6085444830924497523L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="sell_id")
	private Integer sellId;

	@Column(name="crop_name")
	private String cropName;

	private double price;

	private String quantity;
	
	@Column(name = "mobilenum_banknumber")
	private String mobilenumBanknumber;
	
	@Column(name = "account_name")
	private String accountName;
	
	@Column(name = "payment_mode")
	private String paymentMode;

	//bi-directional many-to-one association to Farmer
	@ManyToOne
	@JoinColumn(name="farmer_id")
	private Farmer farmer;

	//bi-directional many-to-one association to CropOrder
	@OneToMany(mappedBy="sellCropDetail", fetch=FetchType.EAGER)
	@JsonBackReference
	private List<CropOrder> cropOrders;
	
	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="post_response_id")
	@JsonManagedReference
	private PostAdvertisementResponse postAdvertisementResponse;
}
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

import java.time.LocalDateTime;
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
@Table(name="post_advertisement_responses")
public class PostAdvertisementResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="post_response_id")
	private Integer postResponseId;
	
	private Double price;
	
	private String quantity;

	@Column(name="date_created")
	private LocalDateTime dateCreated;

	@Column(name="date_modified")
	private LocalDateTime dateModified;

	@Column(name="is_accepted")
	private Boolean isAccepted;
	
	@Column(name="is_final_offer_submitted")
	private Boolean isFinalOfferSubmitted;

	private String message;

	@Column(name="preferred_payment_mode")
	private String preferredPaymentMode;

	//bi-directional many-to-one association to Farmer
	@ManyToOne
	@JoinColumn(name="farmer_id")
	@JsonManagedReference
	private Farmer farmer;

	//bi-directional many-to-one association to PostAdvertisement
	@ManyToOne
	@JoinColumn(name="post_id")
	@JsonManagedReference
	private PostAdvertisement postAdvertisement;

	// bi-directional many-to-one association to Administrator
	@OneToMany(mappedBy = "postAdvertisementResponse", fetch = FetchType.EAGER)
	@JsonBackReference
	private List<SellCropDetail> sellCropDetails;
}
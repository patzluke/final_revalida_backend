package org.ssglobal.training.codes.models;

import java.io.Serializable;
import java.time.LocalDateTime;

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


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name="crop_orders")
public class CropOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="order_id_ref")
	private String orderIdRef;

	private String address;
	
	@Column(name="order_status")
	private String orderStatus;
	
	@Column(name = "is_proof_of_payment_submitted")
	@Builder.Default
	private Boolean isProofOfPaymentSubmitted = false;
	
	@Column(name = "is_crop_received_by_supplier")
	@Builder.Default
	private Boolean isCropReceivedBySupplier = false;
	
	@Column(name = "is_payment_received_by_farmer")
	@Builder.Default
	private Boolean isPaymentReceivedByFarmer = false;

	@Column(name = "payment_received_date")
	private LocalDateTime paymentReceivedDate;
	
	@Column(name = "order_received_date")
	private LocalDateTime orderReceivedDate;

	@Column(name = "cancel_reason")
	private String cancelReason;

	//bi-directional many-to-one association to CropDetail
	@ManyToOne
	@JoinColumn(name="sell_id")
	private SellCropDetail sellCropDetail;

	//bi-directional many-to-one association to Supplier
	@ManyToOne
	@JoinColumn(name="supplier_id")
	private Supplier supplier;

	//bi-directional many-to-one association to CropPayment
	@OneToMany(mappedBy="cropOrder", fetch=FetchType.EAGER)
	@JsonBackReference
	private List<CropPayment> cropPayments;
}
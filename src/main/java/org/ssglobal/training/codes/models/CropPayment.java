package org.ssglobal.training.codes.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name="crop_payment")
public class CropPayment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="payment_id")
	private Integer paymentId;

	@Column(name="paid_by")
	private String paidBy;

	@Column(name="pay_date")
	private LocalDateTime payDate;
	
	@Column(name = "proof_of_payment_image")
	private String proofOfPaymentImage;

	//bi-directional many-to-one association to CropOrder
	@ManyToOne
	@JoinColumn(name="order_id_ref")
	@JsonManagedReference
	private CropOrder cropOrder;
}
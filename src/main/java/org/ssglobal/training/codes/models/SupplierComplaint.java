package org.ssglobal.training.codes.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name="supplier_complaint")
public class SupplierComplaint {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="supplier_complaint_id")
	private Integer supplierComplaintId;

	@Column(name="admin_reply_message")
	private String adminReplyMessage;

	@Column(name="complaint_title")
	private String complaintTitle;
	
	@Column(name="complaint_type")
	private String complaintType;
	
	@Column(name="complaint_message")
	private String complaintMessage;

	@Column(name="is_read")
	private Boolean isRead;

	@Column(name="is_resolved")
	private Boolean isResolved;

	@Column(name="read_date")
	private LocalDateTime readDate;
	
	@Column(name="date_submitted")
	private LocalDateTime dateSubmitted;
	
	@Column(name="active_deactive")
	private Boolean activeDeactive;
	
	private String image;

	//bi-directional many-to-one association to Supplier
	@ManyToOne
	@JoinColumn(name="supplier_id")
	@JsonManagedReference
	private Supplier supplier;
}

package org.ssglobal.training.codes.models;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name="user_notifications")
public class UserNotifications implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="notification_id")
	private BigInteger notificationId;

	@Column(name="notification_message")
	private String notificationMessage;
	
	@Column(name="is_read")
	private Boolean isRead;
	
	@Column(name="date_created")
	private LocalDateTime dateCreated;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonManagedReference
	private Users user;
}
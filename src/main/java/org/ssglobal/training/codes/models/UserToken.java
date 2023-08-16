package org.ssglobal.training.codes.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name="user_tokens")
public class UserToken implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="user_id")
	private Integer userId;
	
	private String token;

	//bi-directional many-to-one association to User
	@OneToOne
	@JoinColumn(name="user_id")
	@JsonManagedReference
	private Users user;
}

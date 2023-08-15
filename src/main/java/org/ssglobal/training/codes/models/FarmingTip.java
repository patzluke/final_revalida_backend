package org.ssglobal.training.codes.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name="farming_tip")
public class FarmingTip implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="farming_tip_id")
	private Integer farmingTipId;

	@Column(name="date_created")
	private LocalDateTime dateCreated;

	@Column(name="tip_message")
	private String tipMessage;
}
package com.Aj.Entity;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Pament {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer PamentId;
	private int UserId;
	private String PamentType;
	private int PamentAmount;
	
	@OneToOne
	private Booking booking;
	
	@ManyToOne
	private User user;

}

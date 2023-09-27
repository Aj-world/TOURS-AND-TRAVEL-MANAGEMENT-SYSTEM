package com.Aj.Entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
@Entity
@Data
public class Booking {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int BookId;
	private int guest;
	private Date ArivalDate;
	private Date LeavingDate;
 
	@OneToOne
	private Package package1;
	
	@OneToOne
	private Pament pament;
	
	@ManyToOne
	private User user;

	
}

package com.Aj.Entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
@Entity
@Data
public class Package {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int packageid;
	private String packageName;
	private String packageType;
	private Date journyData;
	private int price;
	
	@ManyToOne
	private User user;
	
	@OneToOne
	private Booking booking;

}

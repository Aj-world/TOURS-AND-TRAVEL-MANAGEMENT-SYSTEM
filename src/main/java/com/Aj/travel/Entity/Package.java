package com.Aj.travel.Entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "travel_packages")
@Data
public class Package {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int packageid;

	@Column(nullable = false, length = 120)
	private String packageName;

	@Column(length = 60)
	private String packageType;

	private Date journyData;

	@Column(nullable = false)
	private int price;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "package1")
	private Booking booking;

}


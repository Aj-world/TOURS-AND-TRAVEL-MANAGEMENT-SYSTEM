package com.aj.travel.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aj.travel.entity.Package;
import com.aj.travel.exception.ResourceNotFoundException;
import com.aj.travel.repository.PackageRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PackageService {

	private final PackageRepository packageRepository;

	public PackageService(PackageRepository packageRepository) {
		this.packageRepository = packageRepository;
	}

	@Transactional(readOnly = true)
	public Page<Package> getAllPackages(Pageable pageable) {
		log.debug("Fetching packages | page={} | size={}", pageable.getPageNumber(), pageable.getPageSize());
		return packageRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Package getPackageById(int id) {
		log.debug("Fetching package by id | packageId={}", id);
		return packageRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Package not found: " + id));
	}
}

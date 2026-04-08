package com.aj.travel.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aj.travel.entity.Package;
import com.aj.travel.exception.ResourceNotFoundException;
import com.aj.travel.repository.PackageRepository;

@Service
public class PackageService {

	private final PackageRepository packageRepository;

	public PackageService(PackageRepository packageRepository) {
		this.packageRepository = packageRepository;
	}

	@Transactional(readOnly = true)
	public Page<Package> getAllPackages(Pageable pageable) {
		return packageRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Package getPackageById(int id) {
		return packageRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Package not found: " + id));
	}
}

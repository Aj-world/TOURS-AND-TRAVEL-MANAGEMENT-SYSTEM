package com.Aj.travel.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Aj.travel.Entity.Package;
import com.Aj.travel.Exception.ResourceNotFoundException;
import com.Aj.travel.Repository.PackageRepository;

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


package com.Aj.travel.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Aj.travel.Entity.Package;

public interface PackageRepository extends JpaRepository<Package, Integer> {
	Page<Package> findAll(Pageable pageable);
}


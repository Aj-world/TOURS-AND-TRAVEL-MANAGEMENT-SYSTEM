package com.aj.travel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aj.travel.entity.Package;

public interface PackageRepository extends JpaRepository<Package, Integer> {
	Page<Package> findAll(Pageable pageable);
}

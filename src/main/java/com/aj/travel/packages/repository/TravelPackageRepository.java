package com.aj.travel.packages.repository;

import com.aj.travel.packages.domain.TravelPackage;
import com.aj.travel.packages.domain.PackageStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelPackageRepository extends JpaRepository<TravelPackage, Long> {

    List<TravelPackage> findByStatus(PackageStatus status);

}
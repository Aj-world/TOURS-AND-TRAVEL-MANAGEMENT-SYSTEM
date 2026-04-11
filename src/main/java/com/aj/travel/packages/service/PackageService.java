package com.aj.travel.packages.service;

import com.aj.travel.packages.domain.TravelPackage;
import com.aj.travel.packages.domain.PackageStatus;
import com.aj.travel.packages.repository.TravelPackageRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PackageService {

    private final TravelPackageRepository packageRepository;

    public TravelPackage createPackage(TravelPackage travelPackage) {
        return packageRepository.save(travelPackage);
    }

    @Transactional(readOnly = true)
    public List<TravelPackage> getActivePackages() {
        return packageRepository.findByStatus(PackageStatus.ACTIVE);
    }

    @Transactional(readOnly = true)
    public TravelPackage getPackage(Long id) {
        return packageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found"));
    }
}
package com.aj.travel.packages.service;

import com.aj.travel.common.exception.ResourceNotFoundException;
import com.aj.travel.packages.domain.PackageStatus;
import com.aj.travel.packages.domain.TravelPackage;
import com.aj.travel.packages.dto.CreateTravelPackageRequest;
import com.aj.travel.packages.dto.TravelPackageResponse;
import com.aj.travel.packages.mapper.PackageMapper;
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
    private final PackageMapper packageMapper;

    public TravelPackageResponse createPackage(CreateTravelPackageRequest request) {
        TravelPackage travelPackage = packageMapper.toEntity(request);

        return packageMapper.toResponse(packageRepository.save(travelPackage));
    }

    public TravelPackageResponse updatePackage(Long id, CreateTravelPackageRequest request) {
        TravelPackage travelPackage = packageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Package not found"));

        packageMapper.updateEntity(travelPackage, request);

        return packageMapper.toResponse(packageRepository.save(travelPackage));
    }

    public void deletePackage(Long id) {
        TravelPackage travelPackage = packageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Package not found"));

        packageRepository.delete(travelPackage);
    }

    @Transactional(readOnly = true)
    public List<TravelPackageResponse> getActivePackages() {
        return packageRepository.findByStatus(PackageStatus.ACTIVE)
                .stream()
                .map(packageMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TravelPackageResponse getPackage(Long id) {
        TravelPackage travelPackage = packageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Package not found"));

        return packageMapper.toResponse(travelPackage);
    }
}

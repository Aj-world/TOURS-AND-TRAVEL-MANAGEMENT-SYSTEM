package com.aj.travel.packages.service;

import com.aj.travel.packages.domain.PackageStatus;
import com.aj.travel.packages.domain.TravelPackage;
import com.aj.travel.packages.dto.CreateTravelPackageRequest;
import com.aj.travel.packages.dto.TravelPackageResponse;
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

    public TravelPackageResponse createPackage(CreateTravelPackageRequest request) {
        TravelPackage travelPackage = new TravelPackage();
        travelPackage.setTitle(request.getTitle());
        travelPackage.setDescription(request.getDescription());
        travelPackage.setLocation(request.getLocation());
        travelPackage.setPrice(request.getPrice());
        travelPackage.setCapacity(request.getCapacity());
        travelPackage.setStartDate(request.getStartDate());
        travelPackage.setEndDate(request.getEndDate());
        travelPackage.setStatus(request.getStatus());

        return mapToResponse(packageRepository.save(travelPackage));
    }

    @Transactional(readOnly = true)
    public List<TravelPackageResponse> getActivePackages() {
        return packageRepository.findByStatus(PackageStatus.ACTIVE)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TravelPackageResponse getPackage(Long id) {
        TravelPackage travelPackage = packageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        return mapToResponse(travelPackage);
    }

    public TravelPackageResponse mapToResponse(TravelPackage travelPackage) {
        return new TravelPackageResponse(
                travelPackage.getId(),
                travelPackage.getTitle(),
                travelPackage.getDescription(),
                travelPackage.getLocation(),
                travelPackage.getPrice(),
                travelPackage.getCapacity(),
                travelPackage.getStartDate(),
                travelPackage.getEndDate(),
                travelPackage.getStatus() != null ? travelPackage.getStatus().name() : null,
                travelPackage.getCreatedAt()
        );
    }
}

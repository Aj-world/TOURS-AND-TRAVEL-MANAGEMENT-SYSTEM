package com.aj.travel.packages.controller;

import com.aj.travel.common.api.ApiResponse;
import com.aj.travel.packages.dto.CreateTravelPackageRequest;
import com.aj.travel.packages.dto.TravelPackageResponse;
import com.aj.travel.packages.service.PackageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/packages")
@RequiredArgsConstructor
public class PackageController {

    private final PackageService packageService;

    @GetMapping
    public ApiResponse<List<TravelPackageResponse>> getPackages() {

        return new ApiResponse<>(
                true,
                "Packages retrieved successfully",
                packageService.getActivePackages()
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<TravelPackageResponse> getPackage(@PathVariable Long id) {

        return new ApiResponse<>(
                true,
                "Package retrieved successfully",
                packageService.getPackage(id)
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<TravelPackageResponse> createPackage(@Valid @RequestBody CreateTravelPackageRequest request) {

        return new ApiResponse<>(
                true,
                "Package created successfully",
                packageService.createPackage(request)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<TravelPackageResponse> updatePackage(
            @PathVariable Long id,
            @Valid @RequestBody CreateTravelPackageRequest request
    ) {

        return new ApiResponse<>(
                true,
                "Package updated successfully",
                packageService.updatePackage(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deletePackage(@PathVariable Long id) {

        packageService.deletePackage(id);

        return new ApiResponse<>(
                true,
                "Package deleted successfully",
                null
        );
    }

}

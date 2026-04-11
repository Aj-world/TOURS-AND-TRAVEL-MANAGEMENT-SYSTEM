package com.aj.travel.packages.controller;

import com.aj.travel.common.api.ApiResponse;
import com.aj.travel.packages.dto.TravelPackageResponse;
import com.aj.travel.packages.service.PackageService;
import lombok.RequiredArgsConstructor;
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

}

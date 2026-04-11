package com.aj.travel.packages.controller;

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
    public List<TravelPackageResponse> getPackages() {

        return packageService.getActivePackages();
    }

    @GetMapping("/{id}")
    public TravelPackageResponse getPackage(@PathVariable Long id) {

        return packageService.getPackage(id);
    }

}

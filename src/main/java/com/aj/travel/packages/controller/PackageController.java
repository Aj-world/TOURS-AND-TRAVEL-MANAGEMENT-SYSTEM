package com.aj.travel.packages.controller;

import com.aj.travel.packages.domain.TravelPackage;
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
    public List<TravelPackage> getPackages() {

        return packageService.getActivePackages();
    }

    @GetMapping("/{id}")
    public TravelPackage getPackage(@PathVariable Long id) {

        return packageService.getPackage(id);
    }

}
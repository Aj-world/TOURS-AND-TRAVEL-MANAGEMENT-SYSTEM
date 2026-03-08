package com.Aj.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Aj.Entity.Package;

public interface PackageRepository extends JpaRepository<Package, Integer> {
}

package com.TopStyleHat.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.TopStyleHat.Model.Region;

public interface RegionRepository extends JpaRepository<Region, Integer> {
    
}

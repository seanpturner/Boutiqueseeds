package com.boutiqueseeds.boutiqueSeeds.repos;

import com.boutiqueseeds.boutiqueSeeds.entities.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepo extends JpaRepository<Plant, Long> {
}

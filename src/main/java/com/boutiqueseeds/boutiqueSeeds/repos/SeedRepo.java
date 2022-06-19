package com.boutiqueseeds.boutiqueSeeds.repos;

import com.boutiqueseeds.boutiqueSeeds.entities.Seed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeedRepo extends JpaRepository<Seed, Long> {
}

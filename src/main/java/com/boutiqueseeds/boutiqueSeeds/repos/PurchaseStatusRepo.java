package com.boutiqueseeds.boutiqueSeeds.repos;

import com.boutiqueseeds.boutiqueSeeds.entities.PurchaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseStatusRepo extends JpaRepository<PurchaseStatus, Long> {
}

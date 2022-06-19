package com.boutiqueseeds.boutiqueSeeds.repos;

import com.boutiqueseeds.boutiqueSeeds.entities.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepo extends JpaRepository<Login, Long> {
}

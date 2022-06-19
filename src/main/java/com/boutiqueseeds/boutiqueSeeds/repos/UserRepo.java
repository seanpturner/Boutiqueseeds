package com.boutiqueseeds.boutiqueSeeds.repos;

import com.boutiqueseeds.boutiqueSeeds.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}

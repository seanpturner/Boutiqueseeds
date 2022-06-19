package com.boutiqueseeds.boutiqueSeeds.repos;

import com.boutiqueseeds.boutiqueSeeds.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {
}

package com.boutiqueseeds.boutiqueSeeds.repos;

import com.boutiqueseeds.boutiqueSeeds.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> {
}

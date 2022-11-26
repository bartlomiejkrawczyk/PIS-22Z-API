package com.example.api.wiki.repository;

import com.example.api.wiki.entity.ConceptEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConceptRepository extends CrudRepository<ConceptEntity, Integer> {

	@Query("SELECT c FROM ConceptEntity c LEFT JOIN FETCH c.paragraphs WHERE c.id = :conceptId")
	Optional<ConceptEntity> findConceptById(int conceptId);
}

package com.example.api.exam.repository;

import com.example.api.exam.entity.ExerciseEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends CrudRepository<ExerciseEntity, Integer> {

	List<ExerciseEntity> findAllBySectionId(int sectionId);
}

package com.example.api.wiki.repository;

import com.example.api.wiki.entity.SectionEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends CrudRepository<SectionEntity, Integer> {

	List<SectionEntity> findAllBySuperSectionIdIsNull();

	List<SectionEntity> findAllBySuperSectionIdEquals(int superSectionId);
}

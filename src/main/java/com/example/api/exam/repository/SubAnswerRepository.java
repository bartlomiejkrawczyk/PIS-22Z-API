package com.example.api.exam.repository;

import com.example.api.exam.entity.SubAnswerEntity;
import com.example.api.exam.entity.id.SubAnswerId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubAnswerRepository extends CrudRepository<SubAnswerEntity, SubAnswerId> {
}

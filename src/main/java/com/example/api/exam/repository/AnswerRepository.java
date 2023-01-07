package com.example.api.exam.repository;

import com.example.api.exam.entity.AnswerEntity;
import com.example.api.exam.entity.id.AnswerId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends CrudRepository<AnswerEntity, AnswerId> {
}

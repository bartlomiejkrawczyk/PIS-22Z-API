package com.example.api.users.repository;

import com.example.api.users.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

	@Query("SELECT c.password FROM UserEntity c WHERE c.id = :userId")
	Optional<String> findPasswordById(int userId);
}

package com.example.api.user.repository;

import com.example.api.user.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {
	Optional<UserEntity> findUserByLogin(String login);

}

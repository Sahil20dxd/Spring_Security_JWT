package org.sahil.security_jwt.repository;

import org.sahil.security_jwt.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UserRepo extends JpaRepository<UserEntity,Integer>{
    Optional<UserEntity> findByUserName(String userName);
}

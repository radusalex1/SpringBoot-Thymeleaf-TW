package com.example.springbootthymeleaftw.repository;

import com.example.springbootthymeleaftw.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("Select u from UserEntity u where u.email=?1")
    Optional<UserEntity> findByEmail(String Email);
}

package com.example.springbootthymeleaftw.repository;

import com.example.springbootthymeleaftw.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("Select u from UserEntity u where u.email=?1")
    Optional<UserEntity> findByEmail(String Email);

//    @Query("SELECT au.id, au.email, au.password, au.username from UserEntity au\n" +
//            "inner join app_users_roles aur on aur.app_user_id = au.id\n" +
//            "inner join role r on r.id = aur.role_id\n" +
//            "where r.name = 'B2C'")
//    List<UserEntity> findAllB2C();

    List<UserEntity> findAll();
}

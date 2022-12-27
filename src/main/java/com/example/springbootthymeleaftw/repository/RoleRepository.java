package com.example.springbootthymeleaftw.repository;

import com.example.springbootthymeleaftw.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    @Query("Select r from RoleEntity r where r.id=?1")
    Role getRoleById(Long Id);
}

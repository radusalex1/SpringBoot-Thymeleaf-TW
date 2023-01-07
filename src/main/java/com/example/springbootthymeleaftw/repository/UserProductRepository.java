package com.example.springbootthymeleaftw.repository;

import com.example.springbootthymeleaftw.model.entity.UserProductEntity;
import com.example.springbootthymeleaftw.model.entity.UserProductEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProductRepository extends JpaRepository<UserProductEntity, UserProductEntityPK> {

    List<UserProductEntity> getUserProductEntitiesBy();



}

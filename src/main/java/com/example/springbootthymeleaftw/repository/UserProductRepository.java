package com.example.springbootthymeleaftw.repository;

import com.example.springbootthymeleaftw.model.entity.UserProductEntity;
import com.example.springbootthymeleaftw.model.entity.UserProductEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProductRepository extends JpaRepository<UserProductEntity, UserProductEntityPK> {

    List<UserProductEntity> getUserProductEntitiesBy();

    @Query("select upe from UserProductEntity upe where upe.user.id=?1 and upe.product.id=?2")
    UserProductEntity getByB2bAndProduct(long b2bId,long productId);

    @Query("select upe from UserProductEntity upe where upe.user.id=?1 and upe.product.id=?2")
    UserProductEntity getByB2cAndProduct(long b2cId,long productId);

}

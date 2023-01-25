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

    @Query("select upe from UserProductEntity  upe where upe.product.category=?1 and upe.user.companyName=?2 and upe.quantity>=?3")
    List<UserProductEntity> getByFilter(String category, String companyName, Integer quantity);

    @Query("select upe from UserProductEntity  upe where upe.product.category=?1 and upe.user.companyName=?2")
    List<UserProductEntity> getByCategoryAndCompany(String category, String companyName);

    @Query("select upe from UserProductEntity  upe where upe.product.category=?1 and upe.quantity>=?2")

    List<UserProductEntity> getByCategoryAndQuantity(String category, Integer quantity);
    @Query("select upe from UserProductEntity  upe where upe.user.companyName=?1 and upe.quantity>=?2")
    List<UserProductEntity> getByCompanyAndQuantity(String companyName, Integer quantity);

    @Query("select upe from UserProductEntity  upe where upe.product.category=?1")
    List<UserProductEntity> getByCategory(String category);

    @Query("select upe from UserProductEntity  upe where upe.quantity>=?1")
    List<UserProductEntity> getByQuantity(Integer quantity);

    @Query("select upe from UserProductEntity  upe where  upe.user.companyName=?1")
    List<UserProductEntity> getByCompany(String companyName);

    @Query("select upe from UserProductEntity upe where upe.user.id=?1")
    List<UserProductEntity> findAllByB2b(Long id);

    @Query("select upe from UserProductEntity upe where  upe.user.id=?1 and upe.product.id=?2")
    UserProductEntity getByUserAndProduct(Long userId,Long productId);
}

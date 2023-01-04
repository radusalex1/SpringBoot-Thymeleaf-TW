package com.example.springbootthymeleaftw.repository;

import com.example.springbootthymeleaftw.model.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request,Long> {

    List<Request> findAllByAcceptedIsFalse();

    @Query("update Request set accepted = true where email=?1")
    void updateByEmail(String email);
}

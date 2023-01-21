package com.example.springbootthymeleaftw.repository;

import com.example.springbootthymeleaftw.model.entity.CargoRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CargoRequestRepository extends JpaRepository<CargoRequest,Long> {

    @Query("select cr from CargoRequest cr where cr.toUserId=?1")
    List<CargoRequest> getAllUnacceptedForB2b(Long Id);
}

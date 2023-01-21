package com.example.springbootthymeleaftw.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "CargoRequests",schema = "public",catalog = "college")
public class CargoRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private  Long id;

    @Basic
    @Column(name = "fromUserId")
    private Long fromUserId;

    @Basic
    @Column(name = "toUserId")
    private Long toUserId;

    @Basic
    @Column(name="productId")
    private Long productId;

    @Basic
    @Column(name = "productQuantity")
    private Integer quantity;

    @Basic
    @Column
    private boolean accepted=false;
}

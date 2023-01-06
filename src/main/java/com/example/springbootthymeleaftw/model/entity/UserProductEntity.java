package com.example.springbootthymeleaftw.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "users_products",schema = "public", catalog = "college")
public class UserProductEntity implements Serializable {

    @EmbeddedId
    private UserProductEntityPK id = new UserProductEntityPK();

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "USER_ID",referencedColumnName = "id")
    private UserEntity user;


    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "PRODUCT_ID",referencedColumnName = "id")
    private Product product;

    @Column(name = "productQuantity")
    private Integer quantity;
}

package com.example.springbootthymeleaftw.model.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserProductEntityPK implements Serializable {
    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "product_id")
    private Long product_id;

}

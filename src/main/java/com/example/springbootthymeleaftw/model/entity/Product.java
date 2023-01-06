package com.example.springbootthymeleaftw.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@Table(name = "products",schema = "public", catalog = "college")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "price")
    private double price;

    @Basic
    @Column(name = "category")
    private String category;

    @Transient
    private Integer quantity;
    public Product(String name, double price,String category) {
        this.name = name;
        this.price = price;
        this.category=category;
    }

    public Product() {
    }

    public Product(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
    }

    @OneToMany(mappedBy = "user")
    private List<UserProductEntity> userProductEntityList = new ArrayList<UserProductEntity>();

//    @ManyToMany(mappedBy = "products")
//    @JsonIgnore
//    private Collection<UserEntity> users;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            /* The table app_users_roles does not need representation in code */
//            name = "app_products_users",
//            joinColumns = @JoinColumn(
//                    name = "app_user_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "product_id", referencedColumnName = "id"))
//    private Collection<UserEntity> users;
}

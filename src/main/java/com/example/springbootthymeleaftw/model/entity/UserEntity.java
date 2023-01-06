package com.example.springbootthymeleaftw.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name="app_user", schema = "public", catalog = "college")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "username")
    private String username;

    @Basic
    @Column(name = "email", unique = true) /* Duplicates emails not allowed */
    private String email;

    @Basic
    @Column(name = "password")
    private String password;

    @Transient
    private String passwordConfirm;

    @Transient
    private Long roleCode;

    @Transient
    private String getRole(){
        for (RoleEntity r:roles) {
            return r.getName();
        }
        return "no role";
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            /* The table app_users_roles does not need representation in code */
            name = "app_users_roles",
            joinColumns = @JoinColumn(
                    name = "app_user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<RoleEntity> roles;


//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            /* The table app_users_roles does not need representation in code */
//            name = "app_users_products",
//            joinColumns = @JoinColumn(
//                    name = "user_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "product_id", referencedColumnName = "id"))
//    @LazyCollection(LazyCollectionOption.FALSE)
//    private Set<Product> products;

//    @ManyToMany(mappedBy = "users")
//    @JsonIgnore
//    private Collection<Product> products;


    @OneToMany(mappedBy = "product")
    private List<UserProductEntity> userProductEntityList = new ArrayList<UserProductEntity>();

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
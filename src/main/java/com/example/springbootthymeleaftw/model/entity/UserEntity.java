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
    public String username;

    @Basic
    @Column(name = "email", unique = true) /* Duplicates emails not allowed */
    private String email;

    @Basic
    @Column(name = "password")
    private String password;

    @Basic
    @Column(name = "companyName")
    private String companyName;

    @Basic
    @Column(name = "companyCode")
    private String companyCode;

    @Basic
    @Column(name = "companyAddress")
    private String companyAddress;


    @Transient
    private String passwordConfirm;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            /* The table app_users_roles does not need representation in code */
            name = "app_users_roles",
            joinColumns = @JoinColumn(
                    name = "app_user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<RoleEntity> roles;

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
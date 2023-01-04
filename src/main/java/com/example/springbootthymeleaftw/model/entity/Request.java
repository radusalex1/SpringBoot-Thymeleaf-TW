package com.example.springbootthymeleaftw.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Requests",schema = "public",catalog = "college")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private  Long id;

    @Basic
    @Column(name = "newUserEmail",unique = true)
    private String email;

    @Basic
    @Column()
    private boolean accepted=false;
}

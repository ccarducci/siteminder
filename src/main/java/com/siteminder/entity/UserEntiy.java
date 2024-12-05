package com.siteminder.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users_check")
@Data
public class UserEntiy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;

}

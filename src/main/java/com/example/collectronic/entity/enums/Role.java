//package com.example.collectronic.entity.enums;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Data
//@AllArgsConstructor
//@Entity
//@Table(name = "roles")
//public class Role {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @Enumerated(EnumType.STRING)
//    @Column(length = 20)
//    private ERole name;
//
//    public Role() {
//
//    }
//
//    public Role(ERole roleUser) {
//        this.name = roleUser;
//    }
//}
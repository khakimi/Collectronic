package com.example.collectronic.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ImageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    private String public_id;
    private String userId;
    private String userCollectionId;
    private String itemId;
}

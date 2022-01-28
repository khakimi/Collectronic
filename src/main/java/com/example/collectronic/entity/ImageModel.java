package com.example.collectronic.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
    @Column( unique = true, updatable = false)
    private Long userId;
    @Column( unique = true, updatable = false)
    private Long userCollectionId;
    @Column( unique = true, updatable = false)
    private Long itemId;
}

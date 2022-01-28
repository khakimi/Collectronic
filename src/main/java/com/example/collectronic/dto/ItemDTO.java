package com.example.collectronic.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ItemDTO {
    private Long id;
    private String title;
    private String caption;
    private Integer likes;
    private String imageURL;
    private String username;
    private Set<String> usersLiked;

}

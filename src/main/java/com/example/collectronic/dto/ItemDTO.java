package com.example.collectronic.dto;

import lombok.Data;

@Data
public class ItemDTO {
    private Long id;
    private String title;
    private String caption;
    private Integer likes;
    private String imageURL;
}

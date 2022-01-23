package com.example.collectronic.dto;

import com.example.collectronic.entity.ImageModel;
import com.example.collectronic.entity.User;
import com.example.collectronic.entity.enums.ESubject;
import lombok.Data;

@Data
public class UserCollectionDTO {
    private Long id;
    private String title;
    private String description;
    private String username;
    private ESubject eSubject;
}

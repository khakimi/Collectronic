package com.example.collectronic.facade;

import com.example.collectronic.dto.UserCollectionDTO;
import com.example.collectronic.entity.UserCollection;
import org.springframework.stereotype.Component;

@Component
public class UserCollectionFacade {
    public UserCollectionDTO userCollectionToUserCollectionDTO(UserCollection userCollection){
        UserCollectionDTO userCollectionDTO = new UserCollectionDTO();
        userCollectionDTO.setId(userCollection.getId());
        userCollectionDTO.setUsername(userCollection.getUser().getUsername());
        userCollectionDTO.setDescription(userCollection.getDescription());
        userCollectionDTO.setESubject(userCollection.getSubject());
        userCollectionDTO.setTitle(userCollection.getTitle());
        return userCollectionDTO;
    }
}
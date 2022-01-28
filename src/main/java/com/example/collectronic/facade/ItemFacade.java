package com.example.collectronic.facade;

import com.example.collectronic.dto.ItemDTO;
import com.example.collectronic.entity.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemFacade {
    public ItemDTO itemToItemDTO(Item item){
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setTitle(item.getTitle());
        itemDTO.setCaption(item.getCaption());
        itemDTO.setLikes(item.getLikes());
        itemDTO.setUsersLiked(item.getLikedUsers());
        itemDTO.setUsername(item.getUserCollection().getUser().getUsername());
        return itemDTO;
    }
}

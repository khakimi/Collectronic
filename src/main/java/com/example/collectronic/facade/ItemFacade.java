package com.example.collectronic.facade;

import com.example.collectronic.dto.ItemDTO;
import com.example.collectronic.entity.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemFacade {
    public ItemDTO itemToItemDTO(Item item){
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(itemDTO.getId());
        itemDTO.setTitle(item.getTitle());
        itemDTO.setCaption(item.getCaption());
        itemDTO.setLikes(itemDTO.getLikes());
        return itemDTO;
    }
}

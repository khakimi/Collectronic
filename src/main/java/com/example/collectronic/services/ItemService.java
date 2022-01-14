package com.example.collectronic.services;

import com.example.collectronic.dto.ItemDTO;
import com.example.collectronic.entity.Item;
import com.example.collectronic.repository.ItemRepository;
import com.example.collectronic.repository.UserCollectionRepository;
import com.example.collectronic.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class ItemService {
    public static final Logger LOG = LoggerFactory.getLogger(ItemService.class);

    private final ItemRepository itemRepository;
    private final UserCollectionRepository userCollectionRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository,
                       UserCollectionRepository userCollectionRepository){
        this.itemRepository = itemRepository;
        this.userCollectionRepository = userCollectionRepository;
    }

//    public Item createItem(ItemDTO itemDTO, Principal principal){
//        //User user = getUserByPrincipal(principal);
//        Item item = new Item();
//    }
}

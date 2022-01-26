package com.example.collectronic.services;

import com.example.collectronic.dto.ItemDTO;
import com.example.collectronic.entity.ImageModel;
import com.example.collectronic.entity.Item;
import com.example.collectronic.entity.User;
import com.example.collectronic.entity.UserCollection;
import com.example.collectronic.exceptions.ItemNotFoundException;
import com.example.collectronic.exceptions.UserCollectionNotFoundException;
import com.example.collectronic.repository.ImageRepository;
import com.example.collectronic.repository.ItemRepository;
import com.example.collectronic.repository.UserCollectionRepository;
import com.example.collectronic.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    public static final Logger LOG = LoggerFactory.getLogger(ItemService.class);

    private final ItemRepository itemRepository;
    private final UserCollectionRepository userCollectionRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository,
                       UserCollectionRepository userCollectionRepository,
                       UserRepository userRepository,
                       ImageService imageService,
                       ImageRepository imageRepository){
        this.itemRepository = itemRepository;
        this.userCollectionRepository = userCollectionRepository;
        this.userRepository = userRepository;
        this.imageService = imageService;
        this.imageRepository = imageRepository;
    }

    public Item createItem(ItemDTO itemDTO, Long userCollectionId, Principal principal){
        User user = getUserByPrincipal(principal);
        UserCollection userCollection = userCollectionRepository.findById(userCollectionId).orElseThrow(() ->
                new UserCollectionNotFoundException("Collection cannot be found for user: " + user.getUsername()));

        Item item = new Item();
        item.setUserCollection(userCollection);
        item.setCaption(itemDTO.getCaption());
        item.setTitle(itemDTO.getTitle());
        item.setLikes(0);
        LOG.info("Saving tem for User: {}", user.getUsername());
        return itemRepository.save(item);
    }

    public List<Item> getAllItemsToUserCollection(Long userCollectionId){
        return itemRepository.findAllByUserCollectionOrderByCreatedDateDesc(userCollectionRepository.findById(userCollectionId)
                .orElseThrow(() -> new UserCollectionNotFoundException(("Collection can not be found "))));

    }

    public Item getItemById(Long itemId){
        return itemRepository.getById(itemId);
    }

    public Item likeItem(Long itemId, String username){
        Item item = itemRepository.findById(itemId).orElseThrow(()->new ItemNotFoundException("Item cannot be found"));
        Optional<String> userLiked = item.getLikedUsers().stream().filter(u -> u.equals(username)).findAny();
        if (userLiked.isPresent()){
            item.setLikes(item.getLikes()-1);
            item.getLikedUsers().remove(username);
        }else{
            item.setLikes(item.getLikes()+1);
            item.getLikedUsers().add(username);
        }
        return itemRepository.save(item);
    }

    public void deleteItem(Long itemId, Principal principal) throws IOException {
        Item item = getItemById(itemId);
        Optional<ImageModel> imageModel = imageRepository.findByItemId(itemId);
        if(imageModel.isPresent()){
            imageService.delete(imageModel.get().getId());
        }
        itemRepository.delete(item);
    }


    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));

    }
}

package com.example.collectronic.services;

import com.example.collectronic.dto.ItemDTO;
import com.example.collectronic.dto.UserCollectionDTO;
import com.example.collectronic.entity.ImageModel;
import com.example.collectronic.entity.Item;
import com.example.collectronic.entity.User;
import com.example.collectronic.entity.UserCollection;
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
public class UserCollectionService {
    public static final Logger LOG = LoggerFactory.getLogger(UserCollectionService.class);

    private final UserCollectionRepository userCollectionRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;
    private final ItemService itemService;

    @Autowired
    public UserCollectionService(UserCollectionRepository userCollectionRepository,
                                 UserRepository userRepository,
                                 ItemRepository itemRepository,
                                 ImageService imageService,
                                 ImageRepository imageRepository,
                                 ItemService itemService) {
        this.userCollectionRepository = userCollectionRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.imageService = imageService;
        this.imageRepository = imageRepository;
        this.itemService = itemService;
    }

    public UserCollection createUserCollection(UserCollectionDTO userCollectionDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        UserCollection userCollection = new UserCollection();
        userCollection.setCollectronicuser(user);
        userCollection.setTitle(userCollectionDTO.getTitle());
        userCollection.setDescription(userCollectionDTO.getDescription());
        LOG.info("Saving collection for User: {}", user.getUsername());
        return userCollectionRepository.save(userCollection);

    }

    public List<UserCollection> getAllUserCollection() {
        return userCollectionRepository.findAllByOrderByCreatedDate();
    }

    public UserCollection getUserCollectionById(Long userCollectionId, Principal principal) {
        User user = getUserByPrincipal(principal);
        return userCollectionRepository.findUserCollectionByIdAndCollectronicuser(userCollectionId, user)
                .orElseThrow(() -> new UserCollectionNotFoundException(("Collection can not be found for user: "+ user.getUsername())));
    }

    public List<UserCollection> getAllUserCollectionForUser(Principal principal) {
        User user = getUserByPrincipal(principal);
        return userCollectionRepository.findAllByCollectronicuserOrderByCreatedDateDesc(user);
    }

    public void deleteUserCollection(Long userCollectionId, Principal principal) throws IOException {
        UserCollection userCollection = getUserCollectionById(userCollectionId, principal);
        Optional<ImageModel> imageModel = imageRepository.findByUserCollectionId(userCollection.getId());
        for (Item item: userCollection.getItems()) {
            itemService.deleteItem(item.getId(), principal);
        }
        if(imageModel.isPresent()){
            imageService.delete(imageModel.get().getId());
        }
        userCollectionRepository.delete(userCollection);

    }

    public UserCollection updateUserCollection(UserCollectionDTO userCollectionDTO, Long collectionId, Principal principal){
        UserCollection userCollection = getUserCollectionById(collectionId, principal);
        userCollection.setTitle(userCollectionDTO.getTitle());
        userCollection.setDescription(userCollectionDTO.getDescription());
        return userCollectionRepository.save(userCollection);

    }

    public User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(("User with username " + username + " not found")));

    }

}

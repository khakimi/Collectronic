package com.example.collectronic.services;

import com.example.collectronic.dto.UserCollectionDTO;
import com.example.collectronic.entity.User;
import com.example.collectronic.entity.UserCollection;
import com.example.collectronic.exceptions.UserCollectionNotFoundException;
import com.example.collectronic.repository.ItemRepository;
import com.example.collectronic.repository.UserCollectionRepository;
import com.example.collectronic.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class UserCollectionService {
    public static final Logger LOG = LoggerFactory.getLogger(UserCollectionService.class);

    private final UserCollectionRepository userCollectionRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public UserCollectionService(UserCollectionRepository userCollectionRepository,
                                 UserRepository userRepository,
                                 ItemRepository itemRepository) {
        this.userCollectionRepository = userCollectionRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    public UserCollection createUserCollection(UserCollectionDTO userCollectionDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        UserCollection userCollection = new UserCollection();
        userCollection.setUser(user);
        userCollection.setTitle(userCollectionDTO.getTitle());
        userCollection.setImageURL(userCollectionDTO.getImageURL());
        userCollection.setDescription(userCollectionDTO.getDescription());
        userCollection.getSubjects().add(userCollectionDTO.getESubject());
        LOG.info("Creating collection for User: {}", user.getUsername());
        return userCollectionRepository.save(userCollection);

    }

    public List<UserCollection> getAllPost() {
        return userCollectionRepository.findAllByOrderByCreatedDate();
    }

    public UserCollection getUserCollectionById(Long userCollectionId, Principal principal) {
        User user = getUserByPrincipal(principal);
        return userCollectionRepository.findUserCollectionByIdAndUser(userCollectionId, user)
                .orElseThrow(() -> new UserCollectionNotFoundException(("Collection can not be found for user: "+ user.getUsername())));
    }

    public List<UserCollection> getAllUserCollection(Principal principal) {
        User user = getUserByPrincipal(principal);
        return userCollectionRepository.findAllByUserOrderByCreatedDateDesc(user);
    }

    public void deleteUserCollection(Long userCollectionId, Principal principal){
        UserCollection userCollection = getUserCollectionById(userCollectionId, principal);

    }


    public User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(("User with username " + username + " not found")));

    }

}

package com.example.collectronic.services;

import com.example.collectronic.repository.ItemRepository;
import com.example.collectronic.repository.UserCollectionRepository;
import com.example.collectronic.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCollectionService {
    public static final Logger LOG = LoggerFactory.getLogger(UserCollectionService.class);

    private final UserCollectionRepository userCollectionRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public UserCollectionService(UserCollectionRepository userCollectionRepository,
                                 UserRepository userRepository,
                                 ItemRepository itemRepository){
        this.userCollectionRepository = userCollectionRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

}

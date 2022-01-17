package com.example.collectronic.services;


import com.example.collectronic.entity.ImageModel;
import com.example.collectronic.entity.User;
import com.example.collectronic.repository.ImageRepository;
import com.example.collectronic.repository.ItemRepository;
import com.example.collectronic.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.ObjectInput;
import java.security.Principal;

@Service
public class ImageService {
    public static final Logger LOG = LoggerFactory.getLogger(ImageService.class);
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository, UserRepository userRepository, ItemRepository itemRepository) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }


//    public ImageModel uploadImageToUser(MultipartFile multipartFile, Principal principal) throws IOException{
//        User user = getUserByPrincipal(principal);
//        LOG.info("Uploading image profile to User {}", user.getUsername());
//        ImageModel userProfileImage = imageRepository.findByUserId(user.getId()).orElse(null);
//        if(!ObjectUtils.isEmpty(userProfileImage)){
//            imageRepository.delete(userProfileImage);
//        }
//        ImageModel imageModel = new ImageModel();
//        imageModel.setUserId(user.getId());
//
//    }


    public User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(("User with username " + username + " not found")));

    }

}

package com.example.collectronic.services;


import com.example.collectronic.entity.ImageModel;
import com.example.collectronic.entity.User;
import com.example.collectronic.exceptions.ImageNotFoundException;
import com.example.collectronic.repository.ImageRepository;
import com.example.collectronic.repository.ItemRepository;
import com.example.collectronic.repository.UserCollectionRepository;
import com.example.collectronic.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ImageService {
    public static final Logger LOG = LoggerFactory.getLogger(ImageService.class);
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final UserCollectionRepository userCollectionRepository;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public ImageService(ImageRepository imageRepository,
                        UserRepository userRepository,
                        ItemRepository itemRepository,
                        UserCollectionRepository userCollectionRepository,
                        CloudinaryService cloudinaryService) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.userCollectionRepository = userCollectionRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public boolean exists(long id){
        return imageRepository.existsById(id);
    }

    public ImageModel findById(long id){
        return imageRepository.findById(id).orElseThrow(()-> new ImageNotFoundException("Image not found"));
    }

    public void delete(long id) throws IOException {
        Optional <ImageModel> imageModel = imageRepository.findById(id);
        if(imageModel.isPresent()) {
            cloudinaryService.delete(imageModel.get().getPublic_id());
            imageRepository.deleteById(id);
        }
    }


    public ImageModel uploadImageToUser(MultipartFile file, Principal principal, Map result) throws IOException {
        User user = getUserByPrincipal(principal);
        LOG.info("Uploading image profile to User {}", user.getUsername());

        ImageModel userProfileImage = imageRepository.findByUserId(user.getId()).orElse(null);
        if (!ObjectUtils.isEmpty(userProfileImage)) {
            imageRepository.delete(userProfileImage);
        }
        ImageModel imageModel = new ImageModel();
        imageModel.setPublic_id((String)result.get("public_id"));
        imageModel.setUrl((String)result.get("url"));
        imageModel.setUserId(user.getId());
        return imageRepository.save(imageModel);
    }

    public ImageModel getImageToUser(Principal principal) {
        User user = getUserByPrincipal(principal);

        ImageModel imageModel = imageRepository.findByUserId(user.getId()).orElse(null);
        if (!ObjectUtils.isEmpty(imageModel)) {
            //imageModel.setImageBytes(decompressBytes(imageModel.getImageBytes()));
        }

        return imageModel;
    }
    public ImageModel getImageToItem(Long itemId) {
        ImageModel imageModel = imageRepository.findByItemId(itemId)
                .orElseThrow(() -> new ImageNotFoundException("Cannot find image to Post: " + itemId));
        if (!ObjectUtils.isEmpty(imageModel)) {
            //imageModel.setImageBytes(decompressBytes(imageModel.getImageBytes()));
        }

        return imageModel;
    }
    public ImageModel getImageToUserCollection(Long userCollectionId) {
        ImageModel imageModel = imageRepository.findByUserCollectionId(userCollectionId)
                .orElseThrow(() -> new ImageNotFoundException("Cannot find image to UserCollection: " + userCollectionId));
        if (!ObjectUtils.isEmpty(imageModel)) {
            //imageModel.setImageBytes(decompressBytes(imageModel.getImageBytes()));
        }

        return imageModel;
    }
    public User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(("User with username " + username + " not found")));

    }
    private <T> Collector<T, ?, T> toSingleItemCollector() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }
        );
    }


}

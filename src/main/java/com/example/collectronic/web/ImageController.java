package com.example.collectronic.web;

import com.example.collectronic.dto.ItemDTO;
import com.example.collectronic.entity.ImageModel;
import com.example.collectronic.entity.Item;
import com.example.collectronic.payload.response.MessageResponse;
import com.example.collectronic.repository.ImageRepository;
import com.example.collectronic.services.CloudinaryService;
import com.example.collectronic.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/image")
@CrossOrigin
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadImageToUser(@RequestParam("file") MultipartFile file,
                                                             Principal principal) throws IOException {

        imageService.uploadImageToUser(file, principal);
        return ResponseEntity.ok(new MessageResponse("Image Uploaded Successfully"));
    }

    @PostMapping("/{collectionId}/{itemId}/upload")
    public ResponseEntity<ImageModel> uploadImageToItem(@PathVariable("itemId") String itemId,
                                                             @PathVariable("collectionId") String collectionId,
                                                             @RequestParam("file") MultipartFile file,
                                                             Principal principal) throws IOException {
        ImageModel itemImage = imageService.uploadImageToItem(file, principal, Long.parseLong(itemId), Long.parseLong(collectionId));
        return new ResponseEntity<>(itemImage, HttpStatus.OK);
    }

    @PostMapping("/{collectionId}/upload")
    public ResponseEntity<ImageModel> uploadImageToCollection(@PathVariable("collectionId") String collectionId,
                                                             @RequestParam("file") MultipartFile file,
                                                             Principal principal) throws IOException {
        ImageModel userCollectionImage = imageService.uploadImageToUserCollection(file, principal, Long.parseLong(collectionId));
        return new ResponseEntity<>(userCollectionImage, HttpStatus.OK);
    }


    @GetMapping("/profile/image")
    public ResponseEntity<ImageModel> getImageForUser(Principal principal) {
        ImageModel userImage = imageService.getImageToUser(principal);
        return new ResponseEntity<>(userImage, HttpStatus.OK);
    }

    @GetMapping("/{itemId}/item")
    public ResponseEntity<ImageModel> getImageToItem(@PathVariable("itemId") String itemId) {
        ImageModel itemImage = imageService.getImageToItem(Long.parseLong(itemId));
        return new ResponseEntity<>(itemImage, HttpStatus.OK);
    }

    @GetMapping("/{collectionId}/collection")
    public ResponseEntity<ImageModel> getImageToUserCollection(@PathVariable("collectionId") String collectionId) {
        ImageModel userCollectionImage = imageService.getImageToUserCollection(Long.parseLong(collectionId));
        return new ResponseEntity<>(userCollectionImage, HttpStatus.OK);
    }

}

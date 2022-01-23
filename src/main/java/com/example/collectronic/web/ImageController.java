package com.example.collectronic.web;

import com.example.collectronic.entity.ImageModel;
import com.example.collectronic.payload.response.MessageResponse;
import com.example.collectronic.repository.ImageRepository;
import com.example.collectronic.services.CloudinaryService;
import com.example.collectronic.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
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
    private CloudinaryService cloudinaryService;



    @Autowired
    private ImageService imageService;


    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadImageToUser(@RequestParam("file") MultipartFile file,
                                                             Principal principal) throws IOException {

        Map result = cloudinaryService.upload(file);
        imageService.uploadImageToUser(file, principal, result);
        return ResponseEntity.ok(new MessageResponse("Image Uploaded Successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id)throws IOException {
        if(!imageService.exists(Long.parseLong(id)))
            return new ResponseEntity(new MessageResponse("Image not found"), HttpStatus.NOT_FOUND);
        ImageModel image = imageService.findById(Long.parseLong(id));
        imageService.delete(Long.parseLong(id));
        return new ResponseEntity(new MessageResponse("Image deleted"), HttpStatus.OK);
    }
}

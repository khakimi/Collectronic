package com.example.collectronic.web;

import com.example.collectronic.dto.UserCollectionDTO;
import com.example.collectronic.entity.UserCollection;
import com.example.collectronic.facade.UserCollectionFacade;
import com.example.collectronic.payload.response.MessageResponse;
import com.example.collectronic.services.UserCollectionService;
import com.example.collectronic.validations.ResponseErrorValidation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/collection")
@CrossOrigin
public class UserCollectionController {

    @Autowired
    private UserCollectionFacade userCollectionFacade;
    @Autowired
    private UserCollectionService userCollectionService;
    @Autowired
    private ResponseErrorValidation responseErrorValidation;

    @PostMapping("/create")
    public ResponseEntity<Object> createPost(@Valid @RequestBody UserCollectionDTO userCollectionDTO,
                                             BindingResult bindingResult,
                                             Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        UserCollection userCollection = userCollectionService.createUserCollection(userCollectionDTO, principal);
        UserCollectionDTO createdUserCollection = userCollectionFacade.userCollectionToUserCollectionDTO(userCollection);

        return new ResponseEntity<>(createdUserCollection, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserCollectionDTO>> getAllPosts() {
        List<UserCollectionDTO> postDTOList = userCollectionService.getAllUserCollection()
                .stream()
                .map(userCollectionFacade::userCollectionToUserCollectionDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @GetMapping("/user/collections")
    public ResponseEntity<List<UserCollectionDTO>> getAllCollectionsForUser(Principal principal) {
        List<UserCollectionDTO> userCollectionDTOS = userCollectionService.getAllUserCollectionForUser(principal)
                .stream()
                .map(userCollectionFacade::userCollectionToUserCollectionDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(userCollectionDTOS, HttpStatus.OK);
    }

    @PostMapping("/{collectionId}/delete")
    public ResponseEntity<MessageResponse> deletePost(@PathVariable("collectionId") String collectionId, Principal principal) throws IOException {
        userCollectionService.deleteUserCollection(Long.parseLong(collectionId), principal);
        return new ResponseEntity<>(new MessageResponse("Collection was deleted"), HttpStatus.OK);
    }
}

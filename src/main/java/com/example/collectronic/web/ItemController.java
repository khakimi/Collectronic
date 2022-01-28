package com.example.collectronic.web;

import com.example.collectronic.dto.ItemDTO;
import com.example.collectronic.dto.UserCollectionDTO;
import com.example.collectronic.entity.Item;
import com.example.collectronic.facade.ItemFacade;
import com.example.collectronic.payload.response.MessageResponse;
import com.example.collectronic.services.ItemService;
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
@RequestMapping("api/item")
@CrossOrigin
public class ItemController {

    @Autowired
    private ItemFacade itemFacade;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ResponseErrorValidation responseErrorValidation;

    @PostMapping("/{collectionId}/create")
    public ResponseEntity<Object> createItem(@Valid @RequestBody ItemDTO itemDTO,
                                             @PathVariable("collectionId") String collectionId,
                                             BindingResult bindingResult,
                                             Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Item item = itemService.createItem(itemDTO, Long.parseLong(collectionId), principal);
        ItemDTO createdItem = itemFacade.itemToItemDTO(item);

        return new ResponseEntity<>(createdItem, HttpStatus.OK);
    }

    @GetMapping("/{collectionId}/items")
    public ResponseEntity<List<ItemDTO>> getAllItemsToCollection(@PathVariable("collectionId") String collectionId,
                                                                 Principal principal) {
        List<ItemDTO> itemDTOList = itemService.getAllItemsToUserCollection(Long.parseLong(collectionId))
                .stream()
                .map(itemFacade::itemToItemDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(itemDTOList, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        List<ItemDTO> itemDTOList = itemService.getAllItems()
                .stream()
                .map(itemFacade::itemToItemDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(itemDTOList, HttpStatus.OK);
    }

    @PostMapping("/{itemId}/{username}/like")
    public ResponseEntity<ItemDTO> likeItem(@PathVariable("itemId") String itemId,
                                            @PathVariable("username") String username) {
        Item item = itemService.likeItem(Long.parseLong(itemId), username);
        ItemDTO itemDTO = itemFacade.itemToItemDTO(item);

        return new ResponseEntity<>(itemDTO, HttpStatus.OK);
    }

    @PostMapping("/{itemId}/delete")
    public ResponseEntity<MessageResponse> deleteItem(@PathVariable("itemId") String itemId, Principal principal) throws IOException {
        itemService.deleteItem(Long.parseLong(itemId), principal);
        return new ResponseEntity<>(new MessageResponse("Item was deleted"), HttpStatus.OK);
    }
}

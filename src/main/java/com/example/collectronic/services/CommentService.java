package com.example.collectronic.services;

import com.example.collectronic.dto.CommentDTO;
import com.example.collectronic.entity.Comment;
import com.example.collectronic.entity.Item;
import com.example.collectronic.entity.User;
import com.example.collectronic.exceptions.ItemNotFoundException;
import com.example.collectronic.repository.CommentRepository;
import com.example.collectronic.repository.ItemRepository;
import com.example.collectronic.repository.UserCollectionRepository;
import com.example.collectronic.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public static final Logger LOG = LoggerFactory.getLogger(CommentService.class);

    public CommentService(CommentRepository commentRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public Comment saveComment(CommentDTO commentDTO, Long itemId, Principal principal) {
        User user = getUserByPrincipal(principal);
        Item item = itemRepository.findById(itemId).orElseThrow(() ->
                new ItemNotFoundException("Item cannot be found for user: " + user.getUsername()));
        Comment comment = new Comment();
        comment.setItem(item);
        comment.setUsername(user.getUsername());
        comment.setMessage(commentDTO.getMessage());
        comment.setUserId(user.getId());
        LOG.info("Saving comment for item: {}", item.getId());
        return commentRepository.save(comment);
    }

    public List<Comment> getAllCommentsForItem(Long itemId){
        Item item = itemRepository.findById(itemId).orElseThrow(() ->
                new ItemNotFoundException("Item cannot be found"));
        List<Comment> comments = commentRepository.findAllByItem(item);
        return comments;
    }

    public void deleteComment(Long commentId){
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isPresent())
            commentRepository.delete(comment.get());
    }

    public User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(("User with username " + username + " not found")));

    }
}

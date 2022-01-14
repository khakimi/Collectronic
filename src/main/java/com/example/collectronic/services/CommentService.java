package com.example.collectronic.services;

import com.example.collectronic.entity.User;
import com.example.collectronic.repository.CommentRepository;
import com.example.collectronic.repository.ItemRepository;
import com.example.collectronic.repository.UserCollectionRepository;
import com.example.collectronic.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;



    public User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(("User with username " + username + " not found")));

    }
}

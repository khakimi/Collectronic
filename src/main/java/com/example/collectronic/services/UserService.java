package com.example.collectronic.services;

import com.example.collectronic.dto.UserDTO;
import com.example.collectronic.entity.User;
import com.example.collectronic.entity.enums.ERole;
//import com.example.collectronic.entity.enums.Role;
import com.example.collectronic.exceptions.UserExistException;
import com.example.collectronic.payload.request.SignupRequest;
import com.example.collectronic.repository.UserRepository;
import com.example.collectronic.security.JWTTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService {
    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    public final UserRepository userRepository;
    public final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(SignupRequest userIn) {
        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setName(userIn.getName());
        user.setLastname(userIn.getLastname());
        user.setUsername(userIn.getUsername());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        //user.getRoles().add(new Role(ERole.ROLE_USER));

        try {
            LOG.info("Saving User {}", userIn.getEmail());
            return userRepository.save(user);
        } catch (Exception e) {
            LOG.error("Error during registration. {}", e.getMessage());
            throw new UserExistException("The user " + user.getUsername() + " already exist. Please check credentials");
        }
    }


    public User updateUser(UserDTO userDTO, Principal principal){
        User user = getUserByPrincipal(principal);
        user.setName(userDTO.getName());
        user.setName(user.getLastname());
        return userRepository.save(user);

    }

    public User getCurrentUser(Principal principal){
        return getUserByPrincipal(principal);
    }

    public User getUserByPrincipal(Principal principal){
        String username = principal.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException(("User with username "+ username +" not found")));

    }

    public User getUserById(Long userId) {
        return userRepository.findUserById(userId).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
}

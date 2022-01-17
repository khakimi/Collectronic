package com.example.collectronic.facade;

import com.example.collectronic.dto.UserDTO;
import com.example.collectronic.entity.User;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {
    public UserDTO userToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setId(user.getId());
        userDTO.setLastname(user.getLastname());
        userDTO.setName(user.getName());
        return userDTO;
    }
}

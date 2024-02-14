package com.aasjunior.userapi.converter;

import com.aasjunior.userapi.dto.UserDTO;
import com.aasjunior.userapi.model.User;

public class DTOConverter {
    public static UserDTO convert(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setRegistrationDate(user.getRegistrationDate());
        return userDTO;
    }
}

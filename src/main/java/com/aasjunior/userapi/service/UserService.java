package com.aasjunior.userapi.service;

import com.aasjunior.userapi.converter.DTOConverter;
import com.aasjunior.userapi.dto.UserDTO;
import com.aasjunior.userapi.model.User;
import com.aasjunior.userapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserDTO> getAll(){
        List<User> users = userRepository.findAll();
        return users
                .stream()
                .map(DTOConverter::convert)
                .collect(Collectors.toList());
    }

    public UserDTO findByUsername(String username){
        User user = (User) userRepository.findByUsername(username);
        return DTOConverter.convert(user);
    }

    public User findByUsernameSecret(String username){
        User user = (User) userRepository.findByUsername(username);
        return user;
    }

    public UserDTO delete(String userId){
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        userRepository.delete(user);
        return DTOConverter.convert(user);
    }
}

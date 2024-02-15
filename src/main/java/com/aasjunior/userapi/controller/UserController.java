package com.aasjunior.userapi.controller;

import com.aasjunior.userapi.dto.UserDTO;
import com.aasjunior.userapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/list")
    public List<UserDTO> getUsers(){
        return userService.getAll();
    }

    @GetMapping("/{username}")
    public UserDTO getUserByUsername(@PathVariable String username){
        return userService.findByUsername(username);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserDTO delete(@PathVariable String id){
        return userService.delete(id);
    }
}

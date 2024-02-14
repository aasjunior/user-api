package com.aasjunior.userapi.controller;

import com.aasjunior.userapi.dto.AuthenticationDTO;
import com.aasjunior.userapi.dto.UserDTO;
import com.aasjunior.userapi.model.User;
import com.aasjunior.userapi.repository.UserRepository;
import com.aasjunior.userapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/test")
    public String test(){
        return "Hi";
    }

    @GetMapping("/users")
    public List<UserDTO> getUsers(){
        return userService.getAll();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                data.getUsername(),
                data.getPassword()
        );
        var auth = this.authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UserDTO userDTO){
        if(this.userRepository.findByUsername(userDTO.getUsername()) != null){
            return ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.getPassword());
        userDTO.setRegistrationDate(LocalDateTime.now());
        User user = User.convert(userDTO, encryptedPassword);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}

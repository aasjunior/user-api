package com.aasjunior.userapi.controller;

import com.aasjunior.userapi.dto.AuthenticationDTO;
import com.aasjunior.userapi.dto.LoginResponseDTO;
import com.aasjunior.userapi.dto.UserDTO;
import com.aasjunior.userapi.dto.UserRegistrationDTO;
import com.aasjunior.userapi.model.User;
import com.aasjunior.userapi.repository.UserRepository;
import com.aasjunior.userapi.service.TokenService;
import com.aasjunior.userapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(
                    data.getUsername(),
                    data.getPassword()
            );
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User) auth.getPrincipal());
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Erro durante a autenticação: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UserRegistrationDTO userDTO){
        if(this.userRepository.findByUsername(userDTO.getUsername()) != null){
            return ResponseEntity.badRequest().build();
        }
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(null);
        userDTO.setRegistrationDate(LocalDateTime.now());
        User user = User.convert(userDTO, encryptedPassword);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/user/{username}")
    public User getUserByUsername(@PathVariable String username){
        return userService.findByUsernameSecret(username);
    }

}

package com.aasjunior.userapi.dto;

import com.aasjunior.userapi.constants.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String name;
    private String username;
    private String email;
    private String password;
    private UserRole role;
}

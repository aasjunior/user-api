package com.aasjunior.userapi.repository;

import com.aasjunior.userapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByUsername(String username);
}

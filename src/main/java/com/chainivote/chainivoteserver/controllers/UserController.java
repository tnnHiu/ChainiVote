package com.chainivote.chainivoteserver.controllers;

import com.chainivote.chainivoteserver.dtos.response.UserResponseDTO;
import com.chainivote.chainivoteserver.entities.UserEntity;
import com.chainivote.chainivoteserver.repositories.UserRepository;
import com.chainivote.chainivoteserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }

}

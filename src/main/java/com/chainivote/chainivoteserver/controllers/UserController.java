package com.chainivote.chainivoteserver.controllers;

import com.chainivote.chainivoteserver.dtos.request.UpdateWaAddrReqDTO;
import com.chainivote.chainivoteserver.dtos.response.AuthResponseDTO;
import com.chainivote.chainivoteserver.dtos.response.UserResponseDTO;
import com.chainivote.chainivoteserver.security.JwtGenerator;
import com.chainivote.chainivoteserver.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;
    private final JwtGenerator jwtGenerator;

    @Autowired
    public UserController(UserService userService, JwtGenerator jwtGenerator) {
        this.userService = userService;
        this.jwtGenerator = jwtGenerator;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/update-wallet")
    public AuthResponseDTO updateWalletAddress(
            HttpServletRequest request,
            @Valid @RequestBody UpdateWaAddrReqDTO requestDTO
    ) {
        String token = jwtGenerator.getJwtFromRequest(request);
        String username = jwtGenerator.getUsernameFromJWT(token);
        return userService.updateWalletAddress(username, requestDTO);
    }




}

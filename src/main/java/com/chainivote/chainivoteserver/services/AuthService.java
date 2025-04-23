package com.chainivote.chainivoteserver.services;

import com.chainivote.chainivoteserver.dtos.response.AuthResponseDTO;
import com.chainivote.chainivoteserver.dtos.request.LoginRequestDTO;
import com.chainivote.chainivoteserver.dtos.request.RegisterRequestDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    AuthResponseDTO login(LoginRequestDTO loginRequestDTO);

    ResponseEntity<String> register(RegisterRequestDTO registerRequestDTO);
}

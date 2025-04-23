package com.chainivote.chainivoteserver.services;

import com.chainivote.chainivoteserver.dtos.request.UpdateWalletAddressRequestDTO;
import com.chainivote.chainivoteserver.dtos.response.UserResponseDTO;
import org.springframework.http.ResponseEntity;


import java.util.List;


public interface UserService {

    List<UserResponseDTO> getAllUser();

    ResponseEntity<String> updateWalletAddress(UpdateWalletAddressRequestDTO requestDTO);
}

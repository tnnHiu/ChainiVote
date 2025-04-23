package com.chainivote.chainivoteserver.services;

import com.chainivote.chainivoteserver.dtos.request.UpdateWaAddrReqDTO;
import com.chainivote.chainivoteserver.dtos.response.AuthResponseDTO;
import com.chainivote.chainivoteserver.dtos.response.UserResponseDTO;

import java.util.List;


public interface UserService {

    List<UserResponseDTO> getAllUser();

    AuthResponseDTO updateWalletAddress(String username, UpdateWaAddrReqDTO requestDTO);
}

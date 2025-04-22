package com.chainivote.chainivoteserver.services;

import com.chainivote.chainivoteserver.dtos.response.UserResponseDTO;


import java.util.List;


public interface UserService {
    List<UserResponseDTO> getAllUser();
}

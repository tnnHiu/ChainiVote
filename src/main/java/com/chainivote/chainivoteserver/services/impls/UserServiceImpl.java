package com.chainivote.chainivoteserver.services.impls;

import com.chainivote.chainivoteserver.dtos.request.UpdateWalletAddressRequestDTO;
import com.chainivote.chainivoteserver.dtos.response.UserResponseDTO;
import com.chainivote.chainivoteserver.entities.RoleEntity;
import com.chainivote.chainivoteserver.entities.UserEntity;
import com.chainivote.chainivoteserver.repositories.UserRepository;
import com.chainivote.chainivoteserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//Test

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponseDTO> getAllUser() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponseDTO(
                        user.getUsername(),
                        user.getRoles()
                                .stream()
                                .map(RoleEntity::getName)
                                .collect(Collectors.joining(", "))
                ))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<String> updateWalletAddress(UpdateWalletAddressRequestDTO requestDTO) {
        String username = requestDTO.getUsername();
        String newWalletAddress = requestDTO.getWalletAddress();

        UserEntity user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        int rowsUpdated = userRepository.updateWalletAddress(username, newWalletAddress);

        if (rowsUpdated > 0) {
            return new ResponseEntity<>("Wallet address updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update wallet address", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

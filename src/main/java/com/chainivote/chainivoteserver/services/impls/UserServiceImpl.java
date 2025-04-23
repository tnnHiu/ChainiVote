package com.chainivote.chainivoteserver.services.impls;

import com.chainivote.chainivoteserver.dtos.request.UpdateWaAddrReqDTO;
import com.chainivote.chainivoteserver.dtos.response.AuthResponseDTO;
import com.chainivote.chainivoteserver.dtos.response.UserResponseDTO;
import com.chainivote.chainivoteserver.entities.RoleEntity;
import com.chainivote.chainivoteserver.entities.UserEntity;
import com.chainivote.chainivoteserver.repositories.UserRepository;
import com.chainivote.chainivoteserver.security.JwtGenerator;
import com.chainivote.chainivoteserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//Test

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtGenerator jwtGenerator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtGenerator jwtGenerator) {
        this.userRepository = userRepository;
        this.jwtGenerator = jwtGenerator;
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
    public AuthResponseDTO updateWalletAddress(String username, UpdateWaAddrReqDTO requestDTO) {
        String newWalletAddress = requestDTO.getWalletAddress();

        if (newWalletAddress != null && userRepository.existsByWalletAddress(newWalletAddress)) {
            throw new IllegalArgumentException("This wallet address is already in use by another user.");
        }

        UserEntity user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        user.setWalletAddress(newWalletAddress);
        userRepository.save(user);

        String roles = user.getRoles().stream()
                .map(RoleEntity::getName)
                .collect(Collectors.joining(", "));
        String newToken = jwtGenerator.generateToken(user.getUsername(), roles, newWalletAddress);

        return new AuthResponseDTO(newToken);
    }

}

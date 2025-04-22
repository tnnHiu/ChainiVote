package com.chainivote.chainivoteserver.services.impls;

import com.chainivote.chainivoteserver.dtos.response.UserResponseDTO;
import com.chainivote.chainivoteserver.entities.RoleEntity;
import com.chainivote.chainivoteserver.entities.UserEntity;
import com.chainivote.chainivoteserver.repositories.UserRepository;
import com.chainivote.chainivoteserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
}

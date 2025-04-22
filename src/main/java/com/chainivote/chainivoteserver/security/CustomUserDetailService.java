package com.chainivote.chainivoteserver.security;

import com.chainivote.chainivoteserver.entities.RoleEntity;
import com.chainivote.chainivoteserver.entities.UserEntity;
import com.chainivote.chainivoteserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Xác thực người dùng
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findUserByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User " + username + " not found")
        );
        // Trả về đối tượng User của Spring Security
        return new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    // Chuyển đổi List RoleEntity sang List quyền GrantedAuthority
    private Collection<GrantedAuthority> mapRolesToAuthorities(List<RoleEntity> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(
                        role.getName()))
                .collect(Collectors.toList());
    }
}

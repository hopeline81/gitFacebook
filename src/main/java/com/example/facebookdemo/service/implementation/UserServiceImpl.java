package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.dto.RegisterDTO;
import com.example.facebookdemo.entity.Role;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.repository.UserRepository;
import com.example.facebookdemo.service.contrack.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleServiceImpl roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleServiceImpl roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User register(RegisterDTO registerDTO) {
        if (!registerDTO.getPasswordRepeat().equals(registerDTO.getPassword())) {
            throw new IllegalArgumentException("Passwords are different");
        }

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(registerDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getUserRole());
        user.setRoles(roles);

        userRepository.save(user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findFirstByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
        return user;
    }
}
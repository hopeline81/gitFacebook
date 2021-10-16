package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.dto.UserDTO;
import com.example.facebookdemo.entity.Role;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.repository.UserRepository;
import com.example.facebookdemo.service.contrack.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleServiceImpl roleService;
    private final ProfileServiceImpl profileService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleServiceImpl roleService, ProfileServiceImpl profileService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.profileService = profileService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User register(UserDTO userDTO) {
        if (!userDTO.getPasswordRepeat().equals(userDTO.getPassword())) {
            throw new IllegalArgumentException("Passwords are different");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setAge(userDTO.getAge());
        user.setRegisterDate(LocalDateTime.now());
        user.setProfile(profileService.createProfile(userDTO));

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getUserRole());
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        userRepository.findById(user.getId()).ifPresent(u -> {
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        });
        userRepository.save(user);
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findFirstByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
        return user;
    }
}
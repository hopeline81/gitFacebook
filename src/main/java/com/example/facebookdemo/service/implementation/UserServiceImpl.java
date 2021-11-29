package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.dto.UserDTO;
import com.example.facebookdemo.entity.FriendRequest;
import com.example.facebookdemo.entity.Role;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.exception.InvalidEmailException;
import com.example.facebookdemo.exception.InvalidPasswordException;
import com.example.facebookdemo.repository.UserRepository;
import com.example.facebookdemo.service.contrack.FriendRequestService;
import com.example.facebookdemo.service.contrack.UserService;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
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
    public void register(UserDTO userDTO) throws InvalidEmailException, InvalidPasswordException {
        if (userDTO.getEmail() == null) {
            throw new InvalidEmailException("Email not provided");
        }
        if (userDTO.getPassword() == null) {
            throw new InvalidPasswordException("Password not provided");
        }
        if (!userDTO.getPasswordRepeat().equals(userDTO.getPassword())) {
            throw new IllegalArgumentException("Passwords are different");
        }

        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getFirstName(), userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setAge(userDTO.getAge());
        user.setRegisterDate(LocalDateTime.now());
        user.setProfile(profileService.createProfile(userDTO));

        String randomCode = RandomString.make(32);
        user.setVerificationCode(randomCode);

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getUserRole());
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public UserDTO createNewUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setUsername(user.getFirstName(), userDTO.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setAddress(user.getProfile().getAddress());

        return userDTO;
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

        return userRepository.findFirstByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public List<User> searchByNameAndSort(String name, Sort sort) {

        return userRepository.searchByNameAndSort(name, sort);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.deleteById(user.getId());
    }
}
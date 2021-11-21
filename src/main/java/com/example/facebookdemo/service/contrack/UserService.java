package com.example.facebookdemo.service.contrack;

import com.example.facebookdemo.dto.UserDTO;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.exception.InvalidEmailException;
import com.example.facebookdemo.exception.InvalidPasswordException;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    User register(UserDTO userDTO) throws InvalidEmailException, InvalidPasswordException;
    UserDTO createNewUserDTO(User user);
    void updatePassword(User user, String newPassword);
    List<User> searchByNameAndSort(String name, Sort sort);
    void deleteUser(User user);
    User loadUserByUsername(String email) throws UsernameNotFoundException;
}

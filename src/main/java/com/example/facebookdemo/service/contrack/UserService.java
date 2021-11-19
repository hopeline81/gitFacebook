package com.example.facebookdemo.service.contrack;

import com.example.facebookdemo.dto.UserDTO;
import com.example.facebookdemo.entity.Post;
import com.example.facebookdemo.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    User register(UserDTO userDTO);
    UserDTO createNewUserDTO(User user);
    void updatePassword(User user, String newPassword);
    List<User> searchByNameAndSort(String name, Sort sort);
    void deleteUser(User user);
    User loadUserByUsername(String email) throws UsernameNotFoundException;
}

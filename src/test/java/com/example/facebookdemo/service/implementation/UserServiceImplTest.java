package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.dto.UserDTO;
import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.entity.Role;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.exception.InvalidEmailException;
import com.example.facebookdemo.exception.InvalidPasswordException;
import com.example.facebookdemo.repository.UserRepository;
import com.example.facebookdemo.service.contrack.FriendRequestService;
import com.example.facebookdemo.service.contrack.UserService;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleServiceImpl roleService;
    @Mock
    private ProfileServiceImpl profileService;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserServiceImpl userServiceImplUnderTest;


    @BeforeEach
    void setUp() {
        userServiceImplUnderTest = new UserServiceImpl(userRepository,
                roleService,
                profileService,
                bCryptPasswordEncoder);
    }

    @Test
    void canRegisterNewUser() throws InvalidPasswordException, InvalidEmailException {
        UserDTO userDTO = new UserDTO();
        String email = "hopeliness@yahoo.com";
        userDTO.setFirstName("Nadezhda");
        userDTO.setLastName("Vacheva");
        userDTO.setEmail(email);
        userDTO.setPassword("aaaaa");
        userDTO.setPasswordRepeat("aaaaa");
        userDTO.setAge(Integer.valueOf("40"));

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

        userServiceImplUnderTest.register(userDTO);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
    }

    @Test
    @Disabled
    void updatePassword() {
    }

    @Test
    @Disabled
    void canLoadUserByUsername() {
        String debug = "";
        userServiceImplUnderTest.loadUserByUsername("hopeliness@yahoo.com");
        verify(userRepository).findFirstByEmail("hopeliness@yahoo.com");
    }

    @Test
    @Disabled
    void searchByNameAndSort() {
        userServiceImplUnderTest.searchByNameAndSort("Nadezhda", Sort.by(Sort.Direction.ASC, "firstName"));
    }

    @Test
    @Disabled
    void deleteUser() {
    }
}
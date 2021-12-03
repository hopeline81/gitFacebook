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
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private RoleServiceImpl roleService;
    @Mock private ProfileServiceImpl profileService;
    @Mock private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserServiceImpl userServiceImplUnderTest;

    private UserDTO userDTO;

    private User user;

    @BeforeEach
    void setUp() {
        userServiceImplUnderTest = new UserServiceImpl(userRepository,
                roleService,
                profileService,
                bCryptPasswordEncoder);
    }

    @BeforeEach
    private void getUserDTO() {
        this.userDTO = new UserDTO();
        String email = "hopeliness@yahoo.com";
        userDTO.setFirstName("Nadezhda");
        userDTO.setLastName("Vacheva");
        userDTO.setEmail(email);
        userDTO.setPassword("aaaaa");
        userDTO.setPasswordRepeat("aaaaa");
        userDTO.setAge(Integer.valueOf("40"));

        this.user = new User();
        user.setId(1L);
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
    }

    @Test
    void canRegisterNewUser() throws InvalidPasswordException, InvalidEmailException {
        userServiceImplUnderTest.register(userDTO);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());
    }

    @Test()
    void isThrowInvalidEmailExceptionWhenEmailNotProvided() throws InvalidPasswordException, InvalidEmailException {
        userServiceImplUnderTest.register(userDTO);
        userDTO.setEmail(null);
        user.setEmail(null);

        assertThrows(InvalidEmailException.class, () -> userServiceImplUnderTest.register(userDTO));
    }

    @Test()
    void isThrowInvalidPasswordExceptionWhenPasswordNotProvided() throws InvalidPasswordException, InvalidEmailException {
        userServiceImplUnderTest.register(userDTO);
        userDTO.setPassword(null);
        user.setPassword(null);

        assertThrows(InvalidPasswordException.class, () -> userServiceImplUnderTest.register(userDTO));
    }

    @Test
    void isThrowIllegalArgumentExceptionWhenPasswordDoesNotMatch() throws InvalidPasswordException, InvalidEmailException {
        userServiceImplUnderTest.register(userDTO);
        userDTO.setPassword("bbbbb");
        user.setPassword("bbbbb");

        assertThrows(IllegalArgumentException.class, () -> userServiceImplUnderTest.register(userDTO));
    }

    @Test
    void IsThrowUsernameNotFoundExceptionWhenUserDidNotLoad() throws UsernameNotFoundException {
       userServiceImplUnderTest.loadUserByUsername("hope@mail.bg");

        assertThrows(UsernameNotFoundException.class,
                () -> userServiceImplUnderTest.loadUserByUsername(user.getEmail()));
    }
    @Test
    void canUpdatePassword() {
        userServiceImplUnderTest.updatePassword(user, "sssss");
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());
    }

    @Test
    void canLoadUserByUsername() {
        Optional<User> optionalUser = Optional.of(user);
        Mockito.when(userRepository.findFirstByEmail("hopeliness@yahoo.com")).thenReturn(optionalUser);
        userServiceImplUnderTest.loadUserByUsername(optionalUser.get().getEmail());

        assertEquals(optionalUser.get().getEmail(), user.getEmail());
    }

    @Test
    void searchByNameAndSort() {
        List<User> actualUsers = userRepository.searchByNameAndSort("na", Sort.by(Sort.Direction.ASC, "firstName"));
        List<User> sortedUsers = userServiceImplUnderTest.searchByNameAndSort("na", Sort.by(Sort.Direction.ASC, "firstName"));

        assertEquals(sortedUsers.size(),actualUsers.size());
    }

    @Test
    void deleteUser() {
        Optional<User> optionalUser = Optional.of(user);
        userServiceImplUnderTest.deleteUser(optionalUser.get());

        verify(userRepository, times(1)).deleteById(optionalUser.get().getId());
    }
}
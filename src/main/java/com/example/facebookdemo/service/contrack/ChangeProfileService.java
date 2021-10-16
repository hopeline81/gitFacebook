package com.example.facebookdemo.service.contrack;

import com.example.facebookdemo.dto.UserDTO;
import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.entity.User;

public interface ChangeProfileService {
    Profile updateProfileDetails(User user, Profile profile, UserDTO userDTO);
}

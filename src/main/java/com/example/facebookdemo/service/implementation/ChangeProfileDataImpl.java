package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.repository.ProfileRepository;
import com.example.facebookdemo.repository.UserRepository;
import com.example.facebookdemo.service.contrack.ChangeProfileData;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ChangeProfileDataImpl implements ChangeProfileData {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public ChangeProfileDataImpl(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    public void updateProfileDetails(String email) {
        Profile profile = profileRepository.findFirstByEmail(email);
        profile.setFullName(profile.getFullName());
        profile.setEmail(profile.getEmail());
        profileRepository.save(profile);

    }
}

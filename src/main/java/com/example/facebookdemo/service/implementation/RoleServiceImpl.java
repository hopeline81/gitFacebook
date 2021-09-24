package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.entity.Role;
import com.example.facebookdemo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getUserRole(){
        Role userRole = roleRepository.findFirstByAuthority("ROLE_USER")
                .orElseThrow(()-> new IllegalStateException("User role not found"));
        return userRole;
    }
}

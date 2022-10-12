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

    public Role createUserRole() {
        Role userRole = new Role();
        userRole.setAuthority("ROLE_USER");
        roleRepository.save(userRole);
        return userRole;
    }

    public Role getUserRole() {
        return roleRepository.findFirstByAuthority("ROLE_USER")
                .orElse(createUserRole());
    }
}

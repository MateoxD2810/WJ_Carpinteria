package com.wjcarpi.config;

import com.wjcarpi.modelo.Role;
import com.wjcarpi.repositorio.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        List<Role> roles = List.of(
            new Role(1, "ROLE_ADMIN"),
            new Role(2, "ROLE_USER"),
            new Role(3, "ROLE_CARPINTERO")
        );

        for (Role role : roles) {
            roleRepository.findById(role.getId()).orElseGet(() -> {
                return roleRepository.save(role);
            });
        }

        System.out.println("Roles verificados e insertados si no existían.");
    }
}
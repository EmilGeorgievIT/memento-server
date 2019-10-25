package com.memento.service.runner;

import com.memento.model.Role;
import com.memento.model.RoleName;
import com.memento.model.User;
import com.memento.repository.UserRepository;
import com.memento.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MementoCommandLineRunner {

    @Order(1)
    @Component
    private static class RoleCommandLineRunner implements CommandLineRunner {

        private final RoleService roleService;

        @Autowired
        public RoleCommandLineRunner(final RoleService roleService) {
            this.roleService = roleService;
        }

        @Override
        public void run(String... args) {
            final Set<Role> roles = roleService.getAll();

            if (CollectionUtils.isEmpty(roles)) {
                roleService.saveRoles(Arrays.stream(RoleName.values())
                        .map(r -> Role.builder().roleName(r).build())
                        .collect(Collectors.toSet()));
                return;
            }

            final Set<Role> newRoles = new HashSet<>();
            for (RoleName roleName : RoleName.values()) {
                boolean isExists = false;
                for (Role role : roles) {
                    if (role.getRoleName().equals(roleName)) {
                        isExists = true;
                        break;
                    }

                }

                if (!isExists) {
                    newRoles.add(Role.builder().roleName(roleName).build());
                }
            }

            roleService.saveRoles(newRoles);
        }
    }

    @Order(2)
    @Component
    private static class UserCommandLineRunner implements CommandLineRunner {

        private final UserRepository userRepository;
        private final RoleService roleService;
        private final BCryptPasswordEncoder bCryptPasswordEncoder;

        private static final String FIRST_AND_LAST_NAME = "admin";
        private static final String PASSWORD = "admin";
        private static final String EMAIL = "ddd.bg@gmail.com";

        @Autowired
        public UserCommandLineRunner(final UserRepository userRepository,
                                     final RoleService roleService,
                                     final BCryptPasswordEncoder bCryptPasswordEncoder) {
            this.userRepository = userRepository;
            this.roleService = roleService;
            this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        }

        @Override
        public void run(String... args) {
            if (userRepository.findByEmail(EMAIL).isEmpty()) {
                User user = User.builder()
                        .firstName(FIRST_AND_LAST_NAME)
                        .lastName(FIRST_AND_LAST_NAME)
                        .email(EMAIL)
                        .password(bCryptPasswordEncoder.encode(PASSWORD))
                        .role(roleService.findRoleByRoleName(RoleName.ADMIN))
                        .build();

                userRepository.save(user);
            }
        }
    }
}

package com.memento.service;

import com.memento.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    Set<User> getAll();

    User findById(Long id);

    void register(User user);

    User update(User user);

    User findByEmail(String email);
}

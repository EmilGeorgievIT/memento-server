package com.memento.web.endpoint;

import com.memento.model.User;
import com.memento.service.UserService;
import com.memento.web.dto.UserRegisterRequest;
import com.memento.web.endpoint.api.UserApi;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/user", produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserApiController implements UserApi {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserApiController(final UserService userService,
                             final ModelMapper modelMapper,
                             final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/all")
    public ResponseEntity<Set<UserRegisterRequest>> getAllUsers() {
        Set<UserRegisterRequest> response = userService.getAll().stream().map(u -> modelMapper.map(u, UserRegisterRequest.class)).collect(Collectors.toSet());
        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping(value = "/register")
    public ResponseEntity<HttpStatus> register(@Valid @RequestBody final UserRegisterRequest userRegisterRequest) {
        final User user = modelMapper.map(userRegisterRequest, User.class);
        userService.register(user);
        return ResponseEntity.ok().build();
    }
}

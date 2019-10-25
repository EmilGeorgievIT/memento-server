package com.memento.web.endpoint.api;

import com.memento.web.dto.UserRegisterRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

@Api(value = "User API")
public interface UserApi {

    @ApiOperation(value = "Get all users", response = Set.class)
    ResponseEntity<Set<UserRegisterRequest>> getAllUsers();

    @ApiOperation(value = "Register new user", response = HttpStatus.class)
    ResponseEntity<HttpStatus> register(UserRegisterRequest userRegisterRequest);
}

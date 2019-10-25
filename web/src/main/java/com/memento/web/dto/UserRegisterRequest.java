package com.memento.web.dto;

import com.memento.model.RoleName;
import com.memento.shared.annotation.PasswordMatches;
import com.memento.shared.annotation.ValidEmail;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@EqualsAndHashCode
@ToString
@PasswordMatches(password = "password", confirmPassword = "confirmPassword")
public class UserRegisterRequest {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    private RoleName roleName;

    @NotNull
    private String password;

    @NotNull
    private String confirmPassword;
}

package com.example.collectronic.payload.request;

import com.example.collectronic.annotations.PasswordMatches;
import com.example.collectronic.annotations.ValidEmail;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class SignupRequest {
    @NotEmpty(message = "Please enter your username")
    private String username;
    @NotEmpty(message = "Please enter your name")
    private String name;
    @NotEmpty(message = "Please enter your lastname")
    private String lastname;
    @Email(message = "It should have email format")
    @NotBlank(message = "User's email is required")
    @ValidEmail
    private String email;
    @NotEmpty(message = "Password is required")
    @Size(min = 6)
    private String password;
    private String confirmPassword;
}

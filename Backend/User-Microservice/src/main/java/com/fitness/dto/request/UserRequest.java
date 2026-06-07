package com.fitness.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    @NotBlank(message = "Email id should not be blank")
    @Email(message = "Email format is invalid")
    private String email;

    @NotBlank(message = "Password should not be blank")
    @Size(min = 6, message = "Password should contains atleast 6 characters")
    private String password;

    @NotBlank(message = "Username should not be blank")
    @Size(min = 2, message = "Username should contains atleast 2 characters")
    private String firstName;

    private String lastName;
}

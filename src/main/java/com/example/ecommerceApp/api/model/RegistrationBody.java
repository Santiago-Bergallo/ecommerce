package com.example.ecommerceApp.api.model;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegistrationBody {
    @Getter @Setter @NotNull @NotBlank @Size(max = 32, min = 6)
    private String userName;
    @Getter @Setter @Email @NotBlank @NotNull
    private String email;

    @Getter @Setter @NotBlank @NotBlank @Size(max = 32, min = 6) @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    private String password;
    @Getter @Setter
    private String firstName;

    @Getter @Setter
    private String lastName;


}

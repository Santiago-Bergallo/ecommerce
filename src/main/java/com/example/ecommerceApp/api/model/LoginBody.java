package com.example.ecommerceApp.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class LoginBody {

    @Getter @Setter @NotBlank @NotNull
    private String username;

    @Getter @Setter @NotBlank @NotNull
    private String password;

}

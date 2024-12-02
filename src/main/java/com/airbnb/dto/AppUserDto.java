package com.airbnb.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AppUserDto {

    private Long id;
    @NotEmpty
    @Size(min=2,message="Should be more than 2 characters")
    private String name;
    @Email(message="Invalid email format")
    @NotEmpty
    private String email;
    @NotEmpty
    @Size(min=3,message = "Username must be more than 3 characters")
    private String username;
    @Size(min=2,max=20)
    private String password;

    private String role;
}

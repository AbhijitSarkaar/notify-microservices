package com.microservice.users_service.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;

    private List<String> roles;

}

package com.microservice.users_service.payload;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LogInRequestDTO {
    @NotNull
    private String username;

    @NotNull
    private String password;
}

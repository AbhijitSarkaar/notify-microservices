package com.microservices.auth_service.client;

import com.microservices.auth_service.external.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "USERS-SERVICE",
        url = "${user-service.url}"
)
public interface UserClient {
    @GetMapping("/api/users/verify")
    UserDTO verify(@RequestHeader("Cookie") String cookie);
}

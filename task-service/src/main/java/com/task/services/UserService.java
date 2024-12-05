package com.task.services;

import com.task.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USER-SERVICE",url = "http://localhost:5001")
public interface UserService {

    @GetMapping("/api/user/profile")
    User getUserProfileByJwtToken(@RequestHeader("Authorization") String jwt);

}

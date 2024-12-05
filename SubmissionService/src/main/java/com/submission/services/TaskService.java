package com.submission.services;

import com.submission.models.Task;
import org.bson.types.ObjectId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "SUBMISSION-SERVICE",url = "http://localhost:5002/")
public interface TaskService {

    @GetMapping("/api/tasks/{id}")
    Task getTaskById(@PathVariable ObjectId id, @RequestHeader("Authorization")String jwt) throws Exception;

    @PutMapping("api/tasks/{id}/complete")
    Task completeTask(@PathVariable ObjectId id) throws Exception;

}

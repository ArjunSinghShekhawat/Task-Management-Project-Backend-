package com.task.service;

import com.task.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class findByAssignedUserId {
    @Autowired

    private TaskRepository taskRepository;

    @Test
    public void AssignedUserId(){
        System.out.println(taskRepository.findByAssignedUserId(1l));
        assertNotNull(taskRepository.findByAssignedUserId(1l));
    }
}

package com.task.controllers;

import com.task.domains.TaskStatus;
import com.task.models.Task;
import com.task.models.User;
import com.task.services.TaskService;
import com.task.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody Task task, @RequestHeader("Authorization")String jwt) throws Exception {
        User user = userService.getUserProfileByJwtToken(jwt);
        Task createdTask = null;
        System.out.println("user "+user);
        if (user != null) {
            createdTask = taskService.createTask(task,user.getRole());
        }
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Task>getTaskById(@PathVariable ObjectId id, @RequestHeader("Authorization")String jwt) throws Exception {

        User user = userService.getUserProfileByJwtToken(jwt);
        Task task = taskService.getTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
    @GetMapping("/user")
    public ResponseEntity<List<Task>>getAssignUserTask(@RequestParam(required = false) TaskStatus status, @RequestHeader("Authorization")String jwt) throws Exception {
        User user = userService.getUserProfileByJwtToken(jwt);

        if (user != null) {
             List<Task> tasks = taskService.assignedUserTask(user.getId(), status);
             return new ResponseEntity<>(tasks, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @GetMapping("/{id}/user/{userId}")
    public ResponseEntity<Task>assignTaskToUser(@PathVariable ObjectId id,@PathVariable Long userId, @RequestHeader("Authorization")String jwt) throws Exception {
        User user = userService.getUserProfileByJwtToken(jwt);
        Task task = taskService.assignedUser(userId, id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
    @GetMapping("/get-all")
    public ResponseEntity<List<Task>>getAllTask(@RequestParam(required = false)TaskStatus status){
        return new ResponseEntity<>(taskService.getAllTask(status),HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Task>updateTask(@PathVariable ObjectId id,@RequestBody Task task, @RequestHeader("Authorization")String jwt) throws Exception {
        User user = userService.getUserProfileByJwtToken(jwt);
        Task updatedTask=null;
        if (user != null) {
             updatedTask = taskService.updateTask(id,task,user.getId());
        }
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }
    @PutMapping("/{id}/complete")
    public ResponseEntity<Task>completeTask(@PathVariable ObjectId id) throws Exception {
        Task task = taskService.completeTask(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>deleteTask(@PathVariable ObjectId id) throws Exception {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

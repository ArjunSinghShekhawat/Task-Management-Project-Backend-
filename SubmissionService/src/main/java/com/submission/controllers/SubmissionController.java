package com.submission.controllers;

import com.submission.models.Submission;
import com.submission.models.User;
import com.submission.services.SubmissionService;
import com.submission.services.TaskService;
import com.submission.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submission")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<Submission>submitTask(@RequestParam(required = false)ObjectId taskId,
                                                @RequestParam String githubLink,
                                                @RequestHeader("Authorization")String jwt)throws Exception{
        User user = userService.getUserProfileByJwtToken(jwt);
        Submission submission = submissionService.submitTask(taskId, githubLink, user.getId(), jwt);
        return new ResponseEntity<>(submission, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Submission>getSubmissionById(@PathVariable ObjectId id,
                                                       @RequestHeader("Authorization")String jwt)throws Exception{
        User user = userService.getUserProfileByJwtToken(jwt);
        Submission submission = submissionService.getTaskSubmissionById(id);
        return new ResponseEntity<>(submission, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<Submission>>getAllSubmission(@RequestHeader("Authorization")String jwt){
        User user = userService.getUserProfileByJwtToken(jwt);
        List<Submission> submissions = submissionService.getAllTaskSubmission();
        return new ResponseEntity<>(submissions, HttpStatus.OK);
    }
    @GetMapping("/task/{id}")
    public ResponseEntity<List<Submission>>getAllSubmissionOfTask(@PathVariable ObjectId id,
                                                       @RequestHeader("Authorization")String jwt){
        User user = userService.getUserProfileByJwtToken(jwt);
        List<Submission> submissions = submissionService.getTaskSubmissionByTaskId(id);
        return new ResponseEntity<>(submissions, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Submission>acceptOrDeclineSubmission(@PathVariable ObjectId id,
                                                                  @RequestParam("status")String status,
                                                                  @RequestHeader("Authorization")String jwt)throws Exception{
        User user = userService.getUserProfileByJwtToken(jwt);
        Submission submission = submissionService.acceptDeclineSubmission(id,status);
        return new ResponseEntity<>(submission, HttpStatus.OK);
    }
}

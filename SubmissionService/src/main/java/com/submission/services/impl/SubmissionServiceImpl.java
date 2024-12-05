package com.submission.services.impl;

import com.submission.models.Submission;
import com.submission.models.Task;
import com.submission.repositories.SubmissionRepository;
import com.submission.services.SubmissionService;
import com.submission.services.TaskService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private TaskService taskService;


    @Override
    public Submission submitTask(ObjectId taskId, String githubLink, Long userId,String jwt) throws Exception {
        Task task = taskService.getTaskById(taskId, jwt);
        if(task!=null){
            Submission submission = new Submission();
            submission.setTaskId(taskId);
            submission.setUserId(userId);
            submission.setGithubLink(githubLink);
            submission.setSubmissionTime(LocalDateTime.now());
            return submissionRepository.save(submission);
        }
        throw new Exception("Task not found with id "+taskId);
    }

    @Override
    public Submission getTaskSubmissionById(ObjectId SubmissionId) throws Exception {
        return submissionRepository.findById(SubmissionId).orElseThrow(()->new RuntimeException("Submission is not found "+SubmissionId));
    }

    @Override
    public List<Submission> getAllTaskSubmission() {
        return submissionRepository.findAll();
    }

    @Override
    public List<Submission> getTaskSubmissionByTaskId(ObjectId taskId) {
        return submissionRepository.findByTaskId(taskId);
    }

    @Override
    public Submission acceptDeclineSubmission(ObjectId id, String status) throws Exception {
        Submission submission = getTaskSubmissionById(id);
        submission.setStatus(status);
        if(status.equalsIgnoreCase("ACCEPT")){
            Task task = taskService.completeTask(submission.getTaskId());
        }
        return submissionRepository.save(submission);
    }
}

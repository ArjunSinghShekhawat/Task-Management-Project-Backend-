package com.submission.services;

import com.submission.models.Submission;
import org.bson.types.ObjectId;

import java.util.List;

public interface SubmissionService {
    Submission submitTask(ObjectId taskId,String githubLink,Long userId,String jwt)throws Exception;
    Submission getTaskSubmissionById(ObjectId SubmissionId)throws Exception;
    List<Submission> getAllTaskSubmission();
    List<Submission>getTaskSubmissionByTaskId(ObjectId taskId);
    Submission acceptDeclineSubmission(ObjectId id,String status)throws Exception;
}

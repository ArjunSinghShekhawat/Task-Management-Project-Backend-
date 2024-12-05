package com.submission.repositories;

import com.submission.models.Submission;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends MongoRepository<Submission, ObjectId> {
    List<Submission> findByTaskId(ObjectId taskId);

}

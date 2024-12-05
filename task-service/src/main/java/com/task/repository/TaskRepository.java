package com.task.repository;

import com.task.models.Task;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, ObjectId> {
    List<Task>findByAssignedUserId(Long userId);
}

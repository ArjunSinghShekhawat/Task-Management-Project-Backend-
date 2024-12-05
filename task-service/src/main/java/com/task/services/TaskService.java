package com.task.services;

import com.task.domains.TaskStatus;
import com.task.models.Task;
import org.bson.types.ObjectId;

import java.util.List;

public interface TaskService {
    Task createTask(Task task,String requestRole) throws Exception;
    Task getTaskById(ObjectId taskId)throws Exception;
    List<Task> getAllTask(TaskStatus status);
    Task updateTask(ObjectId taskId,Task updatedTask,Long userId) throws Exception;
    void deleteTask(ObjectId taskId)throws Exception;
    Task assignedUser(Long userId,ObjectId taskId) throws Exception;
    List<Task>assignedUserTask(Long userId,TaskStatus status);
    Task completeTask(ObjectId taskId)throws Exception;
}

package com.task.services.impl;

import com.task.domains.TaskStatus;
import com.task.models.Task;
import com.task.repository.TaskRepository;
import com.task.services.TaskService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task createTask(Task task, String requestRole)throws Exception {
        if(!requestRole.equalsIgnoreCase("ADMIN")){
            throw new Exception("only admin create task !");
        }
        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(ObjectId taskId) throws Exception{
        return taskRepository.findById(taskId).orElseThrow(()->new Exception("task not found with id "+taskId));
    }

    @Override
    public List<Task> getAllTask(TaskStatus status) {
        List<Task>allTasks = taskRepository.findAll();
        return allTasks.stream().filter(task->status==null || task.getStatus().name().equalsIgnoreCase(status.toString())).toList();
    }

    @Override
    public Task updateTask(ObjectId taskId, Task updatedTask, Long userId) throws Exception{
        Task existingTask = getTaskById(taskId);

        existingTask.setTitle(updatedTask.getTitle()!=null && !updatedTask.getTitle().isEmpty()? updatedTask.getTitle() : existingTask.getTitle());
        existingTask.setImage(updatedTask.getImage()!=null && !updatedTask.getImage().isEmpty()?updatedTask.getImage(): existingTask.getImage());
        existingTask.setDescription(updatedTask.getDescription()!=null && !updatedTask.getDescription().isEmpty()?updatedTask.getDescription():existingTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus()!=null && !updatedTask.getStatus().toString().isEmpty()?updatedTask.getStatus():existingTask.getStatus());
        existingTask.setDeadline(updatedTask.getDeadline()!=null && !updatedTask.getDeadline().toString().isEmpty()?updatedTask.getDeadline():existingTask.getDeadline());

        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(ObjectId taskId) throws Exception{
        Task task = getTaskById(taskId);
        if(task==null){
            throw new Exception("Task not found id "+taskId);
        }
        taskRepository.deleteById(taskId);
    }

    @Override
    public Task assignedUser(Long userId, ObjectId taskId)throws Exception {
        Task task = getTaskById(taskId);
        task.setAssignedUserId(userId);
        task.setStatus(TaskStatus.DONE);
        return taskRepository.save(task);
    }

    @Override
    public List<Task> assignedUserTask(Long userId, TaskStatus status) {
        return taskRepository.findByAssignedUserId(userId);
    }

    @Override
    public Task completeTask(ObjectId taskId)throws Exception {
        Task task = getTaskById(taskId);
        task.setStatus(TaskStatus.DONE);
        return taskRepository.save(task);
    }
}

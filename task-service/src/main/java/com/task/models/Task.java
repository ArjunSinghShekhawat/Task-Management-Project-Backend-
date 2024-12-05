package com.task.models;

import com.task.domains.TaskStatus;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    private ObjectId id;

    private String title;

    private String description;

    private String image;

    private Long assignedUserId;

    private List<String>tags=new ArrayList<>();

    private LocalDateTime deadline;

    private LocalDateTime createdAt;

    private TaskStatus status;
}

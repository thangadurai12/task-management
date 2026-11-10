package com.taskmanagement.dto;

import com.taskmanagement.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public class TaskRequest {

    private TaskStatus status;

    private String title;

@NotBlank( message = "Description is required")
    private String description;
    private Date dueDate;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private Date createdDate;




}

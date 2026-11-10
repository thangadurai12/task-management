package com.taskmanagement.dto;

import com.taskmanagement.enums.TaskStatus;

import java.util.Date;

public class TaskResponse {
    private TaskStatus status;

    private String title;
    private String description;
    private Date dueDate;
    private Date createdDate;
    private Long id;

    public TaskResponse(Date createdDate, String description, Date dueDate, TaskStatus status, String title,Long id) {
        this.createdDate = createdDate;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.title = title;
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getDescription() {
        return description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Long getId() {
        return id;
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

}

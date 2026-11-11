package com.taskmanagement.taskService;

import com.taskmanagement.dto.TaskRequest;
import com.taskmanagement.dto.TaskResponse;
import com.taskmanagement.entity.ActivityLog;
import com.taskmanagement.entity.TaskEntity;
import com.taskmanagement.entity.UserEntity;
import com.taskmanagement.enums.TaskStatus;
import com.taskmanagement.exception.TaskNotFoundException;
import com.taskmanagement.taskRepo.ActivityLogRepository;
import com.taskmanagement.taskRepo.TaskRepository;
import com.taskmanagement.taskRepo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class TskService {

TaskRepository taskRepository;
UserRepo userRepo;
ActivityLogRepository activityLogRepository;
public TskService(TaskRepository taskRepository,
                  UserRepo userRepo,
                  ActivityLogRepository activityLogRepository){
    this.taskRepository = taskRepository;
    this.userRepo =  userRepo;
    this.activityLogRepository = activityLogRepository;
}

    public TaskResponse create(TaskRequest taskRequest, Long userId) {


        UserEntity user = userRepo.findById(userId)
                .orElseThrow(()->new TaskNotFoundException("User not found"));

        TaskEntity taskEntity = new TaskEntity();

        if (taskRequest.getStatus() == null){
            taskEntity.setStatus(TaskStatus.TODO);
        }
        else {
            taskEntity.setStatus(taskRequest.getStatus());
        }
        taskEntity.setCreatedDate(new Date());
        taskEntity.setTitle(taskRequest.getTitle());
        taskEntity.setDueDate(taskRequest.getDueDate());
        taskEntity.setDescription(taskRequest.getDescription());

        taskEntity.setUser(user);

      TaskEntity savedUser =   taskRepository.save(taskEntity);

      return new TaskResponse(
              savedUser.getCreatedDate(),
              savedUser.getDescription(),
              savedUser.getDueDate(),
              savedUser.getStatus(),
              savedUser.getTitle(),
              savedUser.getId()
      );

    }

    public Page<TaskResponse> getAllTasks(Pageable pageable, TaskStatus status){

        Page<TaskEntity> tasks;


        if (status == null){
            tasks = taskRepository.findAll(pageable);
        }else {
            tasks =taskRepository.findByStatus(status,pageable);
        }


        return tasks.map(task -> new TaskResponse(
                task.getCreatedDate(),
                task.getDescription(),
                task.getDueDate(),
                task.getStatus(),
                task.getTitle(),
                task.getId()
        ));

    }

    public TaskResponse getTaskById(Long id){

    TaskEntity  taskEntity =  taskRepository.findById(id).orElseThrow(()->
            new TaskNotFoundException("Task not found with id: "+ id));

     return new TaskResponse(taskEntity.getCreatedDate(),
                             taskEntity.getDescription(),
             taskEntity.getDueDate(),
             taskEntity.getStatus(),
             taskEntity.getTitle(),
             taskEntity.getId());
    }

    public TaskResponse upDateTask(Long id, TaskRequest taskRequest) {

    TaskEntity taskEntity = taskRepository.findById(id).
            orElseThrow(()->new TaskNotFoundException(
                    "Task not found with id: "+id));

    taskEntity.setStatus(taskRequest.getStatus());
    taskEntity.setDescription(taskRequest.getDescription());
    taskEntity.setTitle(taskRequest.getTitle());
    taskEntity.setDueDate(taskRequest.getDueDate());

    TaskEntity updateTask = taskRepository.save(taskEntity);

    return  new TaskResponse(
            updateTask.getCreatedDate(),
            updateTask.getDescription(),
            updateTask.getDueDate(),
            updateTask.getStatus(),
            updateTask.getTitle(),
            updateTask.getId()
    );
    }

    public void deletTask(Long id){
    TaskEntity task = taskRepository.findById(id).
            orElseThrow(()->new TaskNotFoundException(
                    "Task not found with id :"+ id
            ));

    taskRepository.delete(task);
    }

    @Transactional
    public TaskResponse createTaskWithLog(TaskRequest request, Long userId){

    TaskResponse savedTask = create(request, userId);

        ActivityLog log = new ActivityLog();

        log.setMessage("Task Created: "+ savedTask.getTitle());
        log.setCreatedAt(new Date());

        activityLogRepository.save(log);

//        throw new RuntimeException("Testing rollback");

        return savedTask;


    }
}

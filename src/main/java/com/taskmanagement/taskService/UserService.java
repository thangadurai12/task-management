package com.taskmanagement.taskService;

import com.taskmanagement.dto.TaskResponse;
import com.taskmanagement.dto.UserRequest;
import com.taskmanagement.dto.UserResponse;
import com.taskmanagement.entity.UserEntity;
import com.taskmanagement.exception.TaskNotFoundException;
import com.taskmanagement.taskRepo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    UserRepo userRepo;
    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public UserResponse createUser(UserRequest request){
        UserEntity user = new UserEntity();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        UserEntity savedUser = userRepo.save(user);

        return new UserResponse(
                savedUser.getEmail(),
                savedUser.getId(),
                savedUser.getName()

        );
    }

    public List<TaskResponse> getUserTasks(Long userId) {

        UserEntity user = userRepo.findById(userId)
                .orElseThrow(()-> new TaskNotFoundException("The user not found"));

        return user.getTasks().stream()
                .map(task-> new TaskResponse(
                        task.getCreatedDate(),
                        task.getDescription(),
                        task.getDueDate(),
                        task.getStatus(),
                        task.getTitle(),
                        task.getId()
                )).toList();
    }
}

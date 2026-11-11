package com.taskmanagement.taskController;

import com.taskmanagement.dto.TaskResponse;
import com.taskmanagement.dto.UserRequest;
import com.taskmanagement.dto.UserResponse;
import com.taskmanagement.taskRepo.UserRepo;
import com.taskmanagement.taskService.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/create")
    public UserResponse createUser(@RequestBody UserRequest request){
        return userService.createUser(request);

    }

    @GetMapping("/{userId}/tasks")
    public List<TaskResponse> getUserTasks(@PathVariable Long userId){
        return userService.getUserTasks(userId);
    }
}

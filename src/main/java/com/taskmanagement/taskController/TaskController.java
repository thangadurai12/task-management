package com.taskmanagement.taskController;

import com.taskmanagement.dto.TaskRequest;
import com.taskmanagement.dto.TaskResponse;
import com.taskmanagement.enums.TaskStatus;
import com.taskmanagement.taskService.TskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TskService tskService;

    public TaskController(TskService tskService){
        this.tskService = tskService;
    }


    @GetMapping("/test")
    public String test(){
        return "Hello this thangadurai";
    }

   @PostMapping("/create")
    public TaskResponse create(@Valid @RequestBody TaskRequest taskRequest,
                                 @RequestParam Long userId){
        return tskService.create(taskRequest, userId);
    }

    @GetMapping("/getalltaks")
    public Page<TaskResponse> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false)TaskStatus status){

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size, sort);

        return tskService.getAllTasks(pageable,status);
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id){
        return tskService.getTaskById(id); 
    }

    @PutMapping("/{id}")
    public TaskResponse upDateTask(
            @PathVariable Long id,
            @RequestBody TaskRequest taskRequest
    ){
    return tskService.upDateTask(id,taskRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletTask(@PathVariable Long id){
        tskService.deletTask(id);
        return ResponseEntity.noContent().build();

    }

}
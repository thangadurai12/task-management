package com.taskmanagement;



import com.taskmanagement.dto.TaskResponse;
import com.taskmanagement.entity.TaskEntity;
import com.taskmanagement.enums.TaskStatus;
import com.taskmanagement.exception.TaskNotFoundException;
import com.taskmanagement.taskRepo.TaskRepository;
import com.taskmanagement.taskService.TskService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TskService tskService;

    @Test
    void testGetTaskById() {
   Long taskId = 1L;

        TaskEntity task = new TaskEntity();
        task.setId(taskId);
        task.setTitle("Learn Unit Testing");
        task.setDescription("Practice JUnit and Mockito");
        task.setStatus(TaskStatus.TODO);
        when(taskRepository.findById(taskId))
                .thenReturn(Optional.of(task));
        TaskResponse response = tskService.getTaskById(taskId);
        assertEquals(taskId, response.getId());
        assertEquals("Learn Unit Testing", response.getTitle());
        verify(taskRepository).findById(taskId);
    }
    @Test
    void testGetTaskByIdNotFound() {
        Long taskId = 99L;

        when(taskRepository.findById(taskId))
                .thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> {
            tskService.getTaskById(taskId);
        });

        verify(taskRepository).findById(taskId);

    }

}
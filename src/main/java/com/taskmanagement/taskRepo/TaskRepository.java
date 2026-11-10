package com.taskmanagement.taskRepo;

import com.taskmanagement.entity.TaskEntity;
import com.taskmanagement.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity,Long> {

    Page<TaskEntity> findByStatus(TaskStatus status, Pageable pageable);

}

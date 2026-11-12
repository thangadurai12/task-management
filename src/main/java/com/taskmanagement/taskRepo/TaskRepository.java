package com.taskmanagement.taskRepo;

import com.taskmanagement.entity.TaskEntity;
import com.taskmanagement.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity,Long> {

    Page<TaskEntity> findByStatus(TaskStatus status, Pageable pageable);

    @Query("SELECT t FROM TaskEntity t " +
            "WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<TaskEntity> searchByTitle(@Param("keyword") String keyword);

    @Query("""
    SELECT t FROM TaskEntity t
    WHERE t.user.id = :userId
    AND t.status = :status
    AND LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
""")
    List<TaskEntity> searchTasks(
            @Param("userId") Long userId,
            @Param("status") TaskStatus status,
            @Param("keyword") String keyword
    );

    @Query(
            value = """
        SELECT * FROM task_entity
        WHERE user_id = :userId
        AND status = :status
        AND LOWER(title) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """,
            nativeQuery = true
    )
    List<TaskEntity> searchTasksNative(
            @Param("userId") Long userId,
            @Param("status") String status,
            @Param("keyword") String keyword
    );
}

package com.taskmanager.server.repository;

import com.taskmanager.server.entity.Notification;
import com.taskmanager.server.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserId(Long userId);

    boolean existsByTaskId(Long taskId);      //prevents duplicates

    void deleteByTask(Task task);        //deletes notifications when task deleted

}

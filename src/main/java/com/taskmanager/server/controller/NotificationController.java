package com.taskmanager.server.controller;

import com.taskmanager.server.entity.Notification;
import com.taskmanager.server.repository.NotificationRepository;
import com.taskmanager.server.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/notifications")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("/{userId}")
    public List<Notification> getNotifications(@PathVariable Long userId){

        return notificationService.getNotifications(userId);

    }

}

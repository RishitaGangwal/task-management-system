package com.taskmanager.server.service;

import com.taskmanager.server.entity.Notification;
import com.taskmanager.server.entity.Task;
import com.taskmanager.server.repository.NotificationRepository;
import com.taskmanager.server.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Scheduled(fixedRate = 60000)
    public void checkDueTasks() {

        System.out.println("Scheduler running");

        //Tomorrow Datw
        LocalDate tomorrow =
                LocalDate.now().plusDays(1);

        //Fetch all tasks
        List<Task> tasks =
                taskRepository.findAll();

        //Loop through tasks
        for (Task task : tasks) {

            //Check due date
            if (task.getDueDate() != null &&
                    task.getDueDate().equals(tomorrow)) {

                String message = "Task deadline tomorrow: " + task.getTitle();

                //prevent duplicates
                boolean alreadyExists =
                        notificationRepository
                                .existsByTaskId(task.getId());

                if (!alreadyExists) {

                    Notification notification =
                            new Notification();

                    notification.setMessage(message);

                    notification.setUser(
                            task.getUser()
                    );

                    notification.setTask(task);

                    notificationRepository
                            .save(notification);

                    System.out.println(
                            "Notification saved"
                    );
                }
            }
        }
    }


    //get user notifications
    public List<Notification> getNotifications(Long userId) {                    //frontend will fetch notification
        return notificationRepository.findByUserId(userId);

    }


}

package com.taskmanager.server.dto;


import com.taskmanager.server.entity.Comment;
import com.taskmanager.server.enums.Priority;
import com.taskmanager.server.enums.Status;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TaskDTO {

    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 3, message = "Title must be at least 3 characters")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull
    private Status status;

    private Long userId;

    @NotNull
    private Priority priority;

    @NotNull
    @Future(message = "Due date must be future")
    private LocalDate dueDate;

    private String attachment;

//    private UserDTO user;

    private List<Comment> comments;


}

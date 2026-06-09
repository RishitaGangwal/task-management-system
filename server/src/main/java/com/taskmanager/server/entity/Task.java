package com.taskmanager.server.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.taskmanager.server.enums.Priority;
import com.taskmanager.server.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne                         // many tasks belong to one user
    @JoinColumn(name = "user_id")      // creates foreign key
    private User user;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    private LocalDate dueDate;

    private String attachment;         // DB stores path/fileName

    @OneToMany(mappedBy = "task",
            cascade = CascadeType.ALL)

    @JsonManagedReference
    private List<Comment> comments;

    @JsonIgnore
    @OneToMany(
            mappedBy = "task",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Notification> notifications;

}

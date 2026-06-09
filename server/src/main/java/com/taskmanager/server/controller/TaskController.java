package com.taskmanager.server.controller;

import com.taskmanager.server.dto.CommentDTO;
import com.taskmanager.server.dto.TaskDTO;
import com.taskmanager.server.entity.Comment;
import com.taskmanager.server.entity.Task;
import com.taskmanager.server.enums.Priority;
import com.taskmanager.server.enums.Status;
import com.taskmanager.server.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

//Task management APIs
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public TaskDTO createTask(@Valid @RequestBody TaskDTO dto){

        return taskService.createTask(dto);
    }

    @GetMapping(  "/{userId}")
    public List<TaskDTO> getTasks(@PathVariable Long userId){
        return taskService.getTasksByUser(userId);
    }

    @PatchMapping("/{id}")
    public TaskDTO updateTask(@PathVariable Long id, @RequestBody TaskDTO dto){
        return taskService.updateTask(id,dto);
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return "Task Deleted Successfully";
    }

    @GetMapping
    public Page<TaskDTO> getTasks(Pageable pageable){
        return taskService.getTasks(pageable);
    }

    @GetMapping("/search")
    public Page<Task> searchTasks(@RequestParam(required = false) String query, Pageable pageable){
        return taskService.searchTasks(query, pageable);
    }

    @GetMapping("/filter")
    public Page<Task> filterTasks(@RequestParam(required = false) Status status, @RequestParam(required = false) Priority priority,Pageable pageable){
        return taskService.filterTasks(status, priority, pageable);
    }

    @PostMapping("/{id}/upload")
    public TaskDTO uploadFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException{
             return taskService.uploadFile(id,file);
    }

    @PostMapping("/comments")
    public Comment addComment(@RequestBody CommentDTO dto){
        return taskService.addComment(dto);
    }

//    @GetMapping("/cache-test")
//    public List<TaskDTO> cacheTest() {
//        return taskService.getCachedTasks();
//    }

}

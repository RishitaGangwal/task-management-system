package com.taskmanager.server.service;

import com.taskmanager.server.dto.CommentDTO;
import com.taskmanager.server.dto.TaskDTO;
import com.taskmanager.server.dto.UserDTO;
import com.taskmanager.server.entity.Comment;
import com.taskmanager.server.entity.Task;
import com.taskmanager.server.entity.User;
import com.taskmanager.server.enums.Priority;
import com.taskmanager.server.enums.Status;
import com.taskmanager.server.repository.CommentRepository;
import com.taskmanager.server.repository.NotificationRepository;
import com.taskmanager.server.repository.TaskRepository;
import com.taskmanager.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @CacheEvict(value = "tasks", allEntries = true)
    public TaskDTO createTask(TaskDTO dto){
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setUser(user);
        task.setPriority(dto.getPriority());
        task.setDueDate(dto.getDueDate());

        Task saved = taskRepository.save(task);
        return mapToDTO(saved);
    }

//    @Cacheable(value = "tasks", key="#userId")
    public List<TaskDTO> getTasksByUser(Long userId){

        System.out.println("DB Hit");

        return taskRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

//    @CacheEvict(value = "tasks", allEntries = true)
    public TaskDTO updateTask(Long id, TaskDTO dto){

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if(dto.getStatus() != null){
            task.setStatus(dto.getStatus());
        }

        if(dto.getPriority() != null){
            task.setPriority(dto.getPriority());
        }

        if(dto.getDueDate() != null){
            task.setDueDate(dto.getDueDate());
        }

        Task updated = taskRepository.save(task);

        return mapToDTO(updated);
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public void deleteTask(Long id){

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

//        String message = "Task deadline tomorrow" + task.getTitle();
//        delete related notifications
//        notificationRepository.deleteByTask(task);

         taskRepository.delete(task);

    }


    public Page<TaskDTO> getTasks(Pageable pageable){

//        Pageable pageable = PageRequest.of(page,size);

        return taskRepository.findAll(pageable).map(this::mapToDTO);
    }

//    @Cacheable(value = "tasks")
//    public List<TaskDTO> getCachedTasks(){
//
//        System.out.println("DB HIT");
//
//
//        return taskRepository.findAll()
//                .stream()
//                .map(this::mapToDTO)
//                .toList();
//    }

    public Page<Task> searchTasks(String keyword,Pageable pageable){

        if(keyword == null || keyword.trim().isEmpty()){
            return taskRepository.findAll(pageable);
        }
        return taskRepository.findByTitleContainingIgnoreCase(keyword,pageable);
    }

    public Page<Task> filterTasks(Status status, Priority priority, Pageable pageable){

        //BOTH
        if(status != null && priority != null){
            return taskRepository.findByStatusAndPriority(status,priority,pageable);
        }

        //ONLY STATUS
        if(status != null){
            return taskRepository.findByStatus(status,pageable);
        }

        //ONlY PRIORITY
        if(priority != null){
            return taskRepository.findByPriority(priority,pageable);
        }

        //NONE
        return taskRepository.findAll(pageable);
    }

    public TaskDTO uploadFile(Long id, MultipartFile file) throws IOException {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        String fileName = file.getOriginalFilename();       //gets original filename

        String uploadDir = "uploads/";                      //path

        Path path = Paths.get(uploadDir + fileName);    //create path

        Files.createDirectories(path.getParent());           //create parent folder if doesn't exist

        Files.write(path, file.getBytes());                  //save file

        task.setAttachment(fileName);                        //save file in DB

        Task saved = taskRepository.save(task);

        return mapToDTO(saved);

    }

    public Comment addComment(CommentDTO dto){

        Task task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new RuntimeException("Task not found"));


        Comment comment = new Comment();

        comment.setText(dto.getText());

        comment.setTask(task);

        return commentRepository.save(comment);
    }

    public TaskDTO mapToDTO(Task task) {

        TaskDTO dto = new TaskDTO();

        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setPriority(task.getPriority());
        dto.setDueDate(task.getDueDate());
        dto.setAttachment(task.getAttachment());
        dto.setUserId(task.getUser().getId());
        dto.setComments(task.getComments());
        UserDTO userDTO = new UserDTO();
        userDTO.setId(task.getUser().getId());
        userDTO.setName(task.getUser().getName());
        userDTO.setEmail(task.getUser().getEmail());

//        dto.setUser(userDTO);

        return dto;
    }


}


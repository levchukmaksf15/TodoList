package com.softserve.itacademy.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;

import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;

@Controller
@RequestMapping("/todos")
public class ToDoController {

    @Autowired
    private ToDoService toDoService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private TaskService taskService;
    
    @GetMapping("/create/users/{owner_id}")
    public String create(@PathVariable("owner_id") Long ownerId, Model model) {
        ToDo todo = new ToDo();
        model.addAttribute("ownerId", ownerId);
        model.addAttribute("todo", todo);
        return "create-todo";
    }

    @PostMapping("/create/users/{owner_id}")
    public String create(@PathVariable("owner_id") Long ownerId, @ModelAttribute ToDo todo) {
            User owner = userService.readById(ownerId);
          
            todo.setOwner(owner);
            todo.setCreatedAt(LocalDateTime.now());

            List<User> collaborators = new ArrayList<>();
            todo.setCollaborators(collaborators);
            List<Task> tasks = new ArrayList<>();
            todo.setTasks(tasks);

            toDoService.create(todo);
        return "redirect:/todos/all/users/"+ownerId;
    }

    @GetMapping("/{id}/tasks")
    public String read(@PathVariable("id") Long todoId, Model model) {
        List<Task> tasks = taskService.getByTodoId(todoId);
        model.addAttribute("tasks", tasks);
        model.addAttribute("todoId",todoId);
        return "todo-tasks";
    }

    @GetMapping("/{todo_id}/update/users/{owner_id}")
    public String update(@PathVariable("todo_id") Long toDoId, @PathVariable("owner_id") Long ownerId, Model model) {
        ToDo todo = toDoService.readById(toDoId);
        User owner = userService.readById(ownerId);
        List<User> users = userService.getAll();
        model.addAttribute("todo", todo);
        model.addAttribute("users", users);
        model.addAttribute("owner", owner);
        return "update-todo";
    }

    @PostMapping("/{todo_id}/update/users/{owner_id}")
    public String update( @PathVariable("owner_id") Long ownerId, @ModelAttribute ToDo todo) {
        todo.setCreatedAt(LocalDateTime.now());
        toDoService.update(todo);
        return "redirect:/todos/all/users/"+ownerId;
    }

    @GetMapping("/{todo_id}/delete/users/{owner_id}")
    public String delete(@PathVariable("todo_id") Long toDoId, @PathVariable("owner_id") Long ownerId, Model model) {
        toDoService.delete(toDoId)   ;             
        return "redirect:/todos/all/users/"+ownerId;
    }

    @GetMapping("/all/users/{user_id}")
    public String getAll(@PathVariable("user_id") Long userId, Model model) {
        List<ToDo> todos = toDoService.getByUserId(userId);
        model.addAttribute("todos", todos);
        return "todos-user";
    }

    @GetMapping("/{todo_id}/add/{user_id}")
    public String addCollaborator(@PathVariable("todo_id") Long todoId, @PathVariable("user_id") Long userId) {
        ToDo todo = toDoService.readById(todoId);
        User collaborator = userService.readById(userId);
        
        List<User> collaborators = todo.getCollaborators();
        collaborators.add(collaborator);
        
        todo.setCollaborators(collaborators);
        toDoService.update(todo);
        
        return "redirect:/todos/"+ todoId +"/update/users/" + userId ;
    }

    @GetMapping("/{todo_id}/remove/{user_id}")
    public String removeCollaborator(@PathVariable("todo_id") Long todoId, @PathVariable("user_id") Long userId) {
        ToDo todo = toDoService.readById(todoId);
        User collaborator = userService.readById(userId);
        
        List<User> collaborators = todo.getCollaborators();
        collaborators.remove(collaborator);
        
        todo.setCollaborators(collaborators);
        toDoService.update(todo);
        
        return "redirect:/todos/"+ todoId +"/update/users/" + userId;
    }
  
}

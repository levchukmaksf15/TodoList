package com.softserve.itacademy.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;

@Controller
@RequestMapping("/todos")
public class ToDoController {

    @Autowired
    private ToDoService toDoService;
    
    @Autowired
    private UserService userService;
    
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
        return "redirect:/";
    }
/*
    @GetMapping("/{id}/tasks")
    public String read(//add needed parameters) {
        //ToDo
        return " ";
    }

    @GetMapping("/{todo_id}/update/users/{owner_id}")
    public String update(//add needed parameters) {
        //ToDo
        return " ";
    }

    @PostMapping("/{todo_id}/update/users/{owner_id}")
    public String update(//add needed parameters) {
        //ToDo
        return " ";
    }

    @GetMapping("/{todo_id}/delete/users/{owner_id}")
    public String delete(//add needed parameters) {
                         // ToDo
        return " ";
    }
*/
    @GetMapping("/all/users/{user_id}")
    public String getAll(@PathVariable("user_id") Long userId, Model model) {
        List<ToDo> todos = toDoService.getByUserId(userId);
        model.addAttribute("todos", todos);
        return "todos-user";
    }
/*
    @GetMapping("/{id}/add")
    public String addCollaborator(//add needed parameters) {
        //ToDo
        return " ";
    }

    @GetMapping("/{id}/remove")
    public String removeCollaborator(//add needed parameters) {
        //ToDo
        return " ";
    }
    */
}

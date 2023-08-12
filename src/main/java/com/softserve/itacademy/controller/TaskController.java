package com.softserve.itacademy.controller;


import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.service.StateService;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    
    @Autowired
    private TaskService taskService;
    @Autowired
    private ToDoService toDoService;
    @Autowired
    private StateService stateService;

    @GetMapping("/create/todos/{todo_id}")
    public String saveTask(@PathVariable Integer todo_id, Model model) {
        Task task = new Task();
        task.setTodo(toDoService.readById(todo_id));

        model.addAttribute("task", task);
        model.addAttribute("todo_id", todo_id);

        return "create-task";
    }

    @PostMapping("/create/todos/{todo_id}")
    public String createTask(@ModelAttribute(name="task") Task task, @PathVariable Integer todo_id,  Model model) {
        task.setTodo(toDoService.readById(todo_id));
        taskService.create(task);
        return "redirect:/todos/" + todo_id + "/tasks";
    }

    @GetMapping("/{task_id}/update/todos/{todo_id}")
    public String updateTask(@PathVariable Integer task_id, @PathVariable Integer todo_id, Model model) {
        Task task = taskService.readById(task_id);
        task.setTodo(toDoService.readById(todo_id));

        model.addAttribute("task", task);
        model.addAttribute("todo_id", todo_id);
        model.addAttribute("task_id", task_id);

        return "update-task";
    }

    @PostMapping("/{task_id}/update/todos/{todo_id}")
    public String saveUpdatedTask(@ModelAttribute(name="task") Task task,
                                  @PathVariable Integer todo_id) {
        task.setState(stateService.getByName(task.getState().getName()));
        task.setTodo(toDoService.readById(todo_id));
        taskService.update(task);
        System.out.println(task);
        return "redirect:/todos/" + todo_id + "/tasks";
    }

    @GetMapping("/{task_id}/delete/todos/{todo_id}")
    public String delete(@PathVariable Integer task_id,
                         @PathVariable Integer todo_id) {
        taskService.delete(task_id);
        return "redirect:/todos/" + todo_id + "/tasks";
    }
}
package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.repository.StateRepository;
import com.softserve.itacademy.repository.TaskRepository;
import com.softserve.itacademy.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;
    private StateRepository stateRepository;

    public TaskServiceImpl(TaskRepository taskRepository, StateRepository stateRepository) {
        this.taskRepository = taskRepository;
        this.stateRepository = stateRepository;
    }

    @Override
    public Task create(Task task) {
        task.setState(stateRepository.getByName("New"));
        return taskRepository.save(task);
    }

    @Override
    public Task readById(long id) {
        Optional<Task> optional = taskRepository.findById(id);
            return optional.get();
    }

    @Override
    public Task update(Task task) {
            if (!taskRepository.existsById(task.getId())) {
                throw new NoSuchElementException();
            }
            return taskRepository.save(task);
    }

    @Override
    public void delete(long id) {
        Task task = readById(id);
            taskRepository.delete(task);
    }

    @Override
    public List<Task> getAll() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.isEmpty() ? new ArrayList<>() : tasks;
    }

    @Override
    public List<Task> getByTodoId(long todoId) {
        List<Task> tasks = taskRepository.getByTodoId(todoId);
        return tasks.isEmpty() ? new ArrayList<>() : tasks;
    }
}

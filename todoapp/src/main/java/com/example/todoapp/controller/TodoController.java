package com.example.todoapp.controller;

import com.example.todoapp.model.Todo;
import com.example.todoapp.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TodoController {
    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home(@RequestParam(required = false) String filter, Model model) {
        if ("DONE".equalsIgnoreCase(filter))
            model.addAttribute("todos", service.getByStatus(Todo.Status.DONE));
        else if ("PENDING".equalsIgnoreCase(filter))
            model.addAttribute("todos", service.getByStatus(Todo.Status.PENDING));
        else
            model.addAttribute("todos", service.getAll());
        return "index";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("todo", new Todo());
        return "form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Todo todo) {
        service.save(todo);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("todo", service.getById(id));
        return "form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/";
    }

    @GetMapping("/done/{id}")
    public String markDone(@PathVariable Long id) {
        Todo todo = service.getById(id);
        todo.setStatus(Todo.Status.DONE);
        service.save(todo);
        return "redirect:/";
    }
}

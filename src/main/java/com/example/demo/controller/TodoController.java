package com.example.demo.controller;

import com.example.demo.entity.Todo;
import com.example.demo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<Todo> list() {
        return todoService.listAll();
    }

    @GetMapping("/{id}")
    public Todo get(@PathVariable Long id) {
        return todoService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo add(@RequestBody TodoCreateRequest request) {
        return todoService.create(request.getTitle());
    }

    @PutMapping("/{id}")
    public Todo update(@PathVariable Long id, @RequestBody TodoUpdateRequest request) {
        return todoService.update(id, request.getTitle(), request.getCompleted());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        todoService.delete(id);
    }

    public static class TodoCreateRequest {
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class TodoUpdateRequest {
        private String title;
        private Boolean completed;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Boolean getCompleted() {
            return completed;
        }

        public void setCompleted(Boolean completed) {
            this.completed = completed;
        }
    }
}

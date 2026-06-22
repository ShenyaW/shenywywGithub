package com.example.demo.service;

import com.example.demo.entity.Todo;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.TodoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Transactional(readOnly = true)
    public List<Todo> listAll() {
        return todoRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Transactional(readOnly = true)
    public Todo getById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found: " + id));
    }

    public Todo create(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title must not be blank");
        }
        return todoRepository.save(new Todo(title));
    }

    public Todo update(Long id, String title, Boolean completed) {
        if (title != null && title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title must not be blank");
        }
        if (title == null && completed == null) {
            throw new IllegalArgumentException("At least one field must be provided");
        }
        Todo todo = getById(id);
        if (title != null) {
            todo.setTitle(title);
        }
        if (completed != null) {
            todo.setCompleted(completed);
        }
        return todoRepository.save(todo);
    }

    public void delete(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Todo not found: " + id);
        }
        todoRepository.deleteById(id);
    }
}

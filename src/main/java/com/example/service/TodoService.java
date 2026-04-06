package com.example.service;

import com.example.Todo;
import com.example.model.TodoDto;
import com.example.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for Todo business logic.
 */
@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    /**
     * Get all todos.
     *
     * @return List of TodoDto
     */
    public List<TodoDto> getAllTodos() {
        return todoRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Get todo by id.
     *
     * @param id the todo id
     * @return Optional of TodoDto
     */
    public Optional<TodoDto> getTodoById(Long id) {
        return todoRepository.findById(id).map(this::convertToDto);
    }

    /**
     * Create a new todo.
     *
     * @param todoDto the todo dto
     * @return TodoDto
     */
    public TodoDto createTodo(TodoDto todoDto) {
        Todo todo = convertToEntity(todoDto);
        Todo savedTodo = todoRepository.save(todo);
        return convertToDto(savedTodo);
    }

    /**
     * Update an existing todo.
     *
     * @param id the todo id
     * @param todoDto the todo dto
     * @return Optional of TodoDto
     */
    public Optional<TodoDto> updateTodo(Long id, TodoDto todoDto) {
        if (todoRepository.existsById(id)) {
            Todo todo = convertToEntity(todoDto);
            todo.setId(id);
            Todo updatedTodo = todoRepository.save(todo);
            return Optional.of(convertToDto(updatedTodo));
        }
        return Optional.empty();
    }

    /**
     * Delete a todo by id.
     *
     * @param id the todo id
     * @return true if deleted, false otherwise
     */
    public boolean deleteTodo(Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Convert Todo entity to TodoDto.
     *
     * @param todo the todo entity
     * @return TodoDto
     */
    private TodoDto convertToDto(Todo todo) {
        return new TodoDto(todo.getId(), todo.getTitle(), todo.getDescription(), todo.isCompleted());
    }

    /**
     * Convert TodoDto to Todo entity.
     *
     * @param todoDto the todo dto
     * @return Todo
     */
    private Todo convertToEntity(TodoDto todoDto) {
        return new Todo(todoDto.getTitle(), todoDto.getDescription(), todoDto.isCompleted());
    }
}
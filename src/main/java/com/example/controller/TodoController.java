package com.example.controller;

import com.example.model.TodoDto;
import com.example.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for Todo operations.
 */
@CrossOrigin(origins = "https://todo-frontend-293g.onrender.com")
@RestController
@RequestMapping("/api/todos")
@Tag(name = "Todo API", description = "API for managing todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    /**
     * Get all todos.
     *
     * @return List of TodoDto
     */
    @GetMapping
    @Operation(summary = "Get all todos")
    public ResponseEntity<List<TodoDto>> getAllTodos() {
        List<TodoDto> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    /**
     * Get todo by id.
     *
     * @param id the todo id
     * @return TodoDto or 404 if not found
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get todo by ID")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable Long id) {
        Optional<TodoDto> todo = todoService.getTodoById(id);
        return todo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new todo.
     *
     * @param todoDto the todo dto
     * @return Created TodoDto
     */
    @PostMapping
    @Operation(summary = "Create a new todo")
    public ResponseEntity<TodoDto> createTodo(@RequestBody TodoDto todoDto) {
        TodoDto createdTodo = todoService.createTodo(todoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }

    /**
     * Update an existing todo.
     *
     * @param id the todo id
     * @param todoDto the todo dto
     * @return Updated TodoDto or 404 if not found
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing todo")
    public ResponseEntity<TodoDto> updateTodo(@PathVariable Long id, @RequestBody TodoDto todoDto) {
        Optional<TodoDto> updatedTodo = todoService.updateTodo(id, todoDto);
        return updatedTodo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete a todo by id.
     *
     * @param id the todo id
     * @return 204 if deleted, 404 if not found
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a todo by ID")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        boolean deleted = todoService.deleteTodo(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
package com.example;

import com.example.model.TodoDto;
import com.example.repository.TodoRepository;
import com.example.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for TodoService.
 */
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTodos() {
        // Given
        Todo todo1 = new Todo("Title1", "Description1", false, "medium");
        todo1.setId(1L);
        Todo todo2 = new Todo("Title2", "Description2", true, "low");
        todo2.setId(2L);
        when(todoRepository.findAll()).thenReturn(Arrays.asList(todo1, todo2));

        // When
        List<TodoDto> todos = todoService.getAllTodos();

        // Then
        assertThat(todos).hasSize(2);
        assertThat(todos.get(0).getTitle()).isEqualTo("Title1");
        assertThat(todos.get(1).getTitle()).isEqualTo("Title2");
        verify(todoRepository, times(1)).findAll();
    }

    @Test
    void testGetTodoById() {
        // Given
        Todo todo = new Todo("Title", "Description", false, "medium");
        todo.setId(1L);
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        // When
        Optional<TodoDto> result = todoService.getTodoById(1L);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Title");
        verify(todoRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateTodo() {
        // Given
        TodoDto todoDto = new TodoDto(null, "New Title", "New Description", false, "medium");
        Todo savedTodo = new Todo("New Title", "New Description", false, "medium");
        savedTodo.setId(1L);
        when(todoRepository.save(any(Todo.class))).thenReturn(savedTodo);

        // When
        TodoDto result = todoService.createTodo(todoDto);

        // Then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("New Title");
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    void testUpdateTodo() {
        // Given
        TodoDto todoDto = new TodoDto(null, "Updated Title", "Updated Description", true, "high");
        Todo updatedTodo = new Todo("Updated Title", "Updated Description", true, "high");
        updatedTodo.setId(1L);
        when(todoRepository.existsById(1L)).thenReturn(true);
        when(todoRepository.save(any(Todo.class))).thenReturn(updatedTodo);

        // When
        Optional<TodoDto> result = todoService.updateTodo(1L, todoDto);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Updated Title");
        verify(todoRepository, times(1)).existsById(1L);
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    void testDeleteTodo() {
        // Given
        when(todoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(todoRepository).deleteById(1L);

        // When
        boolean result = todoService.deleteTodo(1L);

        // Then
        assertThat(result).isTrue();
        verify(todoRepository, times(1)).existsById(1L);
        verify(todoRepository, times(1)).deleteById(1L);
    }
}
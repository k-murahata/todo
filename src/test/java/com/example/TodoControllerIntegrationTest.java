package com.example;

import com.example.model.TodoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for TodoController.
 */
@SpringBootTest
@AutoConfigureWebMvc
class TodoControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testCreateTodo() throws Exception {
        TodoDto todoDto = new TodoDto(null, "Test Todo", "Test Description", false);

        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todoDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Todo"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void testGetAllTodos() throws Exception {
        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetTodoById() throws Exception {
        // First create a todo
        TodoDto todoDto = new TodoDto(null, "Test Todo", "Test Description", false);
        String todoJson = objectMapper.writeValueAsString(todoDto);

        String response = mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(todoJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        TodoDto createdTodo = objectMapper.readValue(response, TodoDto.class);
        Long id = createdTodo.getId();

        // Then get it by id
        mockMvc.perform(get("/api/todos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value("Test Todo"));
    }

    @Test
    void testUpdateTodo() throws Exception {
        // First create a todo
        TodoDto todoDto = new TodoDto(null, "Test Todo", "Test Description", false);
        String todoJson = objectMapper.writeValueAsString(todoDto);

        String response = mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(todoJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        TodoDto createdTodo = objectMapper.readValue(response, TodoDto.class);
        Long id = createdTodo.getId();

        // Then update it
        TodoDto updateDto = new TodoDto(null, "Updated Todo", "Updated Description", true);
        String updateJson = objectMapper.writeValueAsString(updateDto);

        mockMvc.perform(put("/api/todos/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Todo"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void testDeleteTodo() throws Exception {
        // First create a todo
        TodoDto todoDto = new TodoDto(null, "Test Todo", "Test Description", false);
        String todoJson = objectMapper.writeValueAsString(todoDto);

        String response = mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(todoJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        TodoDto createdTodo = objectMapper.readValue(response, TodoDto.class);
        Long id = createdTodo.getId();

        // Then delete it
        mockMvc.perform(delete("/api/todos/{id}", id))
                .andExpect(status().isNoContent());

        // Verify it's deleted
        mockMvc.perform(get("/api/todos/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetTodoByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/todos/{id}", 999L))
                .andExpect(status().isNotFound());
    }
}
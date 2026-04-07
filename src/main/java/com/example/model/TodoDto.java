package com.example.model;

/**
 * DTO class for Todo requests and responses.
 */
public class TodoDto {

    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private String priority;

    // Default constructor
    public TodoDto() {
    }

    // Constructor with parameters
    public TodoDto(Long id, String title, String description, boolean completed, String priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.priority = priority;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
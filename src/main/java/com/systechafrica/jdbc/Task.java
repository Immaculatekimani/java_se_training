package com.systechafrica.jdbc;

import java.time.LocalDate;

public class Task {
        private int id;
    private String title;
    private LocalDate startDate;
    private LocalDate dueDate;
    private int taskStatus;
    private int priority;
    private String description;

    

    public Task() {
    }
    
    public Task(int id, String title, LocalDate startDate, LocalDate dueDate, int taskStatus, int priority,
            String description) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.taskStatus = taskStatus;
        this.priority = priority;
        this.description = description;
    }
    

    public Task(String title, LocalDate startDate, LocalDate dueDate, int taskStatus, int priority, String description) {
        this.title = title;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.taskStatus = taskStatus;
        this.priority = priority;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public int getTaskStatus() {
        return taskStatus;
    }
    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }
    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return "Task [id=" + id + ", title=" + title + ", startDate=" + startDate + ", dueDate=" + dueDate
                + ", taskStatus=" + taskStatus + ", priority=" + priority + ", description=" + description + "]";
    }

}

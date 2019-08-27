package com.example.todolist.model;
import com.example.todolist.persistance.entity.Category;
import com.example.todolist.persistance.entity.Priority;
import com.example.todolist.persistance.entity.TodoList;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sun.xml.internal.ws.developer.Serialization;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


public class TodoListDto {


    private Long id;

    @NotNull
    @Size(min = 1, message = "Empty Field")
    private String task;

    @NotNull
    private String priority;

    @NotNull
    private String category;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) //new
    private Date deadline;

    public TodoListDto(Long id, String task, String priority, String category, Date deadline,  Integer status) {
        this.id = id;
        this.task = task;
        this.priority = priority;
        this.deadline = deadline;
        this.category = category;
        this.status = status;
    }

    public Integer getStatus() {

        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private Integer status=0;


    @Override
    public String toString() {
        return "TodoListDto{" +
                "id=" + id +
                ", task='" + task + '\'' +
                ", priority=" + priority +
                ", category=" + category +
                ", deadline=" + deadline +
                ", status=" + status +
                '}';
    }

//
//    public PriorityDto a_p(Priority t) {
//        return priority.adapter(t);
//
//    }
//    public CategoryDto a_c(Category t) {
//        return category.adapter(t);
//
//    }

    public TodoListDto adapter(TodoList todoList){
        TodoListDto todoListDto=new TodoListDto(todoList.getId(), todoList.getTask(), todoList.getPriority().getName(), todoList.getCategory().getName() /*a_p(todoList.getPriority()), a_c(todoList.getCategory())*/, todoList.getDeadline(), todoList.getStatus());
        return todoListDto;
    }

    public TodoListDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getPriority() {
        return priority;
    }

    public String getCategory() { return category; }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}

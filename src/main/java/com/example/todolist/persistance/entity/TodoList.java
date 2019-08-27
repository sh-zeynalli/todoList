package com.example.todolist.persistance.entity;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
@Table(name = "todo_list")
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority")
    private Priority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    private Category category;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) //new
    private Date deadline;

    public TodoList(Long id, String task, Priority priority, Category category, Date deadline,  Integer status) {
        this.id=id;
        this.task = task;
        this.priority = priority;
        this.deadline = deadline;
        this.category = category;
        this.status = status;
    }

    @Override
    public String toString() {
        return "TodoList{" +
                "id=" + id +
                ", task='" + task + '\'' +
                ", priority=" + priority +
                ", deadline=" + deadline +
                ", category=" + category +
                ", status=" + status +
                '}';
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private Integer status=0;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public TodoList() {
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

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}



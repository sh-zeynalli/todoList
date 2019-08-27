package com.example.todolist.persistance.entity;

import javax.persistence.*;

@Entity
@Table(name = "priority")
public class Priority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //pay attention db id serial type
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;

    @Override
    public String toString() {
        return "PriorityDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Priority(Long id, String name) {
        this.id=id;
        this.name = name;
    }

    public Priority(){

    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


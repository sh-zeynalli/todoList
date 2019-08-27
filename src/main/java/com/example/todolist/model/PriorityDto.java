package com.example.todolist.model;

import com.example.todolist.persistance.entity.Priority;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PriorityDto {

    private Long id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @Override
    public String toString() {
        return "PriorityDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public PriorityDto() {
    }

    public PriorityDto(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public PriorityDto adapter(Priority p){
        PriorityDto priorityDto=new PriorityDto(p.getId(), p.getName());
        return priorityDto;
    }
}

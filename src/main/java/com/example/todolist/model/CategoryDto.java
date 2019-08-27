package com.example.todolist.model;

import com.example.todolist.persistance.entity.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CategoryDto {

    private Long id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @Override
    public String toString() {
        return "CategoryDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

   public CategoryDto adapter(Category c){
        CategoryDto categoryDto=new CategoryDto(c.getId(), c.getName());
        return categoryDto;
   }


    public CategoryDto() {
    }

    public CategoryDto(Long id, String name) {
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
}

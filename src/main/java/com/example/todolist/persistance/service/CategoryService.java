package com.example.todolist.persistance.service;

import com.example.todolist.model.CategoryDto;
import com.example.todolist.persistance.entity.Category;

import java.util.List;


public interface CategoryService {

    public List<CategoryDto> findAll();

    public CategoryDto save(CategoryDto category);

    public CategoryDto findByName(String name);

    public void delete(Long id);

    public void update(CategoryDto categoryDto);


}

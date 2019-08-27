package com.example.todolist.persistance.service;

import com.example.todolist.model.CategoryDto;
import com.example.todolist.model.PriorityDto;
import com.example.todolist.persistance.entity.Priority;

import java.util.List;

public interface PriorityService {

    public List<PriorityDto> findAll();

    public PriorityDto save(PriorityDto priority);

    public PriorityDto findByName(String name);

    public void delete(Long id);

    public void update(PriorityDto priorityDto);

}

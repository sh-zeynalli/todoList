package com.example.todolist.persistance.service;

import com.example.todolist.model.TodoListDto;
import com.example.todolist.persistance.entity.TodoList;
import com.example.todolist.search.SearchReq;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface TodoListService {


    public List<TodoListDto> findAll();

    public List<TodoListDto> findAll(SearchReq searchReq);

    public TodoListDto findById(Long id);

    public void delete(Long id);

    public TodoListDto save(TodoListDto todoList);

    public void update(TodoListDto todoList);

    public void changeStatustoOne(Long id);

    public Date doneTaskDate(Long id, LocalDateTime date);

    public void changeStatustoZero(Long id);

    public String noDoneTaskInfo();

   // public TodoListDto update()

    public String noTaskInfo();


//    public List<String> checkNull(TodoListDto dto);
    }


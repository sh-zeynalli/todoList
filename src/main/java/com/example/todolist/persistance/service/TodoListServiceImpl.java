package com.example.todolist.persistance.service;

import com.example.todolist.model.CategoryDto;
import com.example.todolist.model.PriorityDto;
import com.example.todolist.model.TodoListDto;
import com.example.todolist.persistance.entity.Category;
import com.example.todolist.persistance.entity.Priority;
import com.example.todolist.persistance.entity.TodoList;
import com.example.todolist.persistance.repository.CategoryRepository;
import com.example.todolist.persistance.repository.PriorityRepository;
import com.example.todolist.persistance.repository.TodoListRepository;
import com.example.todolist.search.SearchReq;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoListServiceImpl implements TodoListService {

    @Autowired
    private TodoListRepository todoListRepository;

    @Autowired
    EntityManager em;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Date doneTaskDate(Long id, LocalDateTime localDate) {

        String sDate = localDate.toString();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("done date errorrrrrrr");
        }
        todoListRepository.findById(id).get().setDeadline(date);
        System.out.println(todoListRepository.findById(id).get().getDeadline());
        return date;
    }

    @Override
    public void changeStatustoOne(Long id) {

        todoListRepository.changeStatustoOne(id);

    }

    @Override
    public void changeStatustoZero(Long id) {

        todoListRepository.changeStatustoZero(id);

    }

    @Autowired
    private PriorityRepository priorityRepository;


    @Override
    public String noDoneTaskInfo() {
        List<TodoList> todos = todoListRepository.findAll();
        List<TodoListDto> todoListDtos = todos.stream().map(t -> new TodoListDto(
                t.getId(),
                t.getTask(),
                t.getPriority().getName(),
                t.getCategory().getName(),
                t.getDeadline(),
                t.getStatus()
        )).collect(Collectors.toList());

        int s = 0;
        String message = null;
        for (int i = 0; i < todoListDtos.size(); i++) {
            s += todoListDtos.get(i).getStatus();
        }
        if (s == 0) {
            message = "Yo do not have any completed task";
        }
        return message;
    }

    @Override
    public String noTaskInfo() {
        List<TodoList> tl = todoListRepository.findAll();
        List<TodoListDto> tld = tl.stream().map(t -> new TodoListDto(
                t.getId(),
                t.getTask(),
                t.getPriority().getName(),
                t.getCategory().getName(),
                t.getDeadline(),
                t.getStatus()
        )).collect(Collectors.toList());

        int s = 0;
        String message = null;
        for (int i = 0; i < tld.size(); i++) {
            s += tld.get(i).getStatus();
        }

        if (s == tld.size()) {
            message = "You do not have any task";
        }
        return message;
    }

    @Override
    public String noSearchResult(SearchReq searchReq) {
      String message=null;
       if(searchReq.getTask().isEmpty()){
           message="No results.\nWe couldn't find anything related to your search.";
       }
       return message;
    }



    @Override
    public List<TodoListDto> findAll() {
        List<TodoList> todos = todoListRepository.findAll();
        List<TodoListDto> todoListDtos = todos.stream().map(t -> new TodoListDto(
                t.getId(),
                t.getTask(),
                t.getPriority().getName(),
                t.getCategory().getName(),
                t.getDeadline(),
                t.getStatus()
        )).collect(Collectors.toList());

        return todoListDtos;
    }

    @Override
    public List<TodoListDto> findAll(SearchReq searchReq) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<TodoList> criteriaQuery = criteriaBuilder.createQuery(TodoList.class);
        Root<TodoList> root = criteriaQuery.from(TodoList.class);
        Join<TodoList, Priority> priorityJoin = root.join("priority", JoinType.LEFT);
        Join<TodoList, Category> categoryJoin = root.join("category", JoinType.LEFT);

        List<Predicate> conditions = new ArrayList<>();

        if(searchReq.getTask()!="") {
            Predicate task = criteriaBuilder.equal(root.get("task"), searchReq.getTask());
            conditions.add(task);
        }

        if(searchReq.getStatus()!=1) {
            Predicate status = criteriaBuilder.equal(root.get("status"), searchReq.getStatus());
            conditions.add(status);
        }

        if(searchReq.getCategory()!="") {
            Predicate category = criteriaBuilder.equal(categoryJoin.get("name"), searchReq.getCategory());
            conditions.add(category);
        }
        if(searchReq.getPriority()!="") {
            Predicate priority = criteriaBuilder.equal(priorityJoin.get("name"), searchReq.getPriority());
            conditions.add(priority);
        }
        if(!searchReq.getDate().equals(LocalDateTime.now())) {
            Predicate date = criteriaBuilder.lessThan(root.get("deadline"), searchReq.getDate());
            conditions.add(date);
        }

        criteriaQuery.where(conditions.toArray(new Predicate [conditions.size()]));

        List<TodoList> list= em.createQuery(criteriaQuery).getResultList();

        List<TodoListDto> todoListDtos = list.stream().map(t -> new TodoListDto(
                t.getId(),
                t.getTask(),
                t.getPriority().getName(),
                t.getCategory().getName(),
                t.getDeadline(),
                t.getStatus()
        )).collect(Collectors.toList());

        return todoListDtos;
    }


    @Override
    public TodoListDto findById(Long id) {

        TodoList list = todoListRepository.findById(id).get();
        TodoListDto listDto = new TodoListDto().adapter(list);
        return listDto;

    }

    @Override
    public void delete(Long id) {

        todoListRepository.deleteById(id);
    }

    @Override
    public TodoListDto save(TodoListDto todoList) {

        Date date = null;

        String sDate = todoList.getDeadline().toString();
        try {
            date = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy").parse(sDate);
        } catch (ParseException e) {
            try {
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate);
            } catch (ParseException e1) {
                // e1.printStackTrace();
            }
        }

        Priority priority = priorityRepository.findbyName(todoList.getPriority());
        Category category = categoryRepository.findByName(todoList.getCategory());
        TodoList todos = new TodoList(todoList.getId(), todoList.getTask(),
                priority, category, date, todoList.getStatus());
        todos = todoListRepository.save(todos);
        todoList.adapter(todos);

        return todoList;
    }

    @Override
    public void update(TodoListDto todoList) {


        Priority p = priorityRepository.findbyName(todoList.getPriority());
        Category c = categoryRepository.findByName(todoList.getCategory());

        todoListRepository.update(
                todoList.getId(),
                todoList.getTask(),
                p,
                c,
                todoList.getDeadline(),
                todoList.getStatus());
    }
}

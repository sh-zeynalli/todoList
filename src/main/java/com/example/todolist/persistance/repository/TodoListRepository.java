package com.example.todolist.persistance.repository;

import com.example.todolist.model.TodoListDto;
import com.example.todolist.persistance.entity.Category;
import com.example.todolist.persistance.entity.Priority;
import com.example.todolist.persistance.entity.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public interface TodoListRepository extends JpaRepository<TodoList, Long> {


    @Modifying
    @Query(value="update TodoList t set t.status=1 where t.id=?1")
    public void changeStatustoOne(@Param("id") Long id);

    @Modifying
    @Query(value="update TodoList t set t.status=0 where t.id=?1")
    public void changeStatustoZero(@Param("id") Long id);


    @Query(value = "select t from TodoList t where t.priority=:priority")
    public List<TodoList> findByPriority(@Param("priority") Priority priority);

    @Query(value = "select t from TodoList t where t.category=:category")
    public List<TodoList> findByCategory(@Param("category") Category category);


    @Query(value="select t.task, t.priority, t.category, t.deadline from TodoList t where task=:task and t.priority=:priority  and t.category=:category  and t.deadline=:deadline  ")
    public List<TodoList> advancedSearch(@Param("task") String task, @Param("priority") Priority priority, @Param("category") Category category, @Param("deadline") Date deadline);

    @Modifying
    @Query("update TodoList t set t.task =:task, t.priority =:priority, t.category = :category, t.deadline =:deadline, t.status =:status  where t.id = :id")
    public void update(@Param("id") Long id, @Param("task") String task, @Param("priority") Priority priority, @Param("category") Category category, @Param("deadline") Date deadline, @Param("status") Integer status);
}



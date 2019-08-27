package com.example.todolist.persistance.repository;

import com.example.todolist.persistance.entity.Category;
import com.example.todolist.persistance.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface PriorityRepository extends JpaRepository<Priority, Long> {

    @Query("select p from Priority p where p.name=:name")
    Priority findbyName(@Param("name") String name);

    @Modifying
    @Query("update Priority p set p.name=:name  where p.id = :id")
    public void update(@Param("id") Long id, @Param("name") String name);

}

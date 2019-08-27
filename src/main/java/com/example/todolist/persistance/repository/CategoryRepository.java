package com.example.todolist.persistance.repository;

import com.example.todolist.persistance.entity.Category;
import com.example.todolist.persistance.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c where c.name=:name")
    Category findByName(@Param("name") String name);

    @Modifying
    @Query("update Category c set c.name=:name  where c.id = :id")
    public void update(@Param("id") Long id, @Param("name") String name);

}

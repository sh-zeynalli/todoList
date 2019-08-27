package com.example.todolist.persistance.service;

import com.example.todolist.model.CategoryDto;
import com.example.todolist.persistance.entity.Category;
import com.example.todolist.persistance.entity.TodoList;
import com.example.todolist.persistance.repository.CategoryRepository;
import com.example.todolist.persistance.repository.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository repository;
    @Autowired
    TodoListRepository listRepository;

    @Override
    public List<CategoryDto> findAll() {

        List<Category> category = repository.findAll();
        List<CategoryDto> categoryDtos = category.stream()
                .map(c -> new CategoryDto(c.getId(), c.getName())).collect(Collectors.toList());

        return categoryDtos;
    }

    @Override
    public CategoryDto findByName(String name) {

        Category category = repository.findByName(name);
        CategoryDto categoryDto = new CategoryDto().adapter(category);
        return categoryDto;

    }

    @Override
    public CategoryDto save(CategoryDto category) {

        Category savecategory = new Category(category.getId(), category.getName());
        savecategory = repository.save(savecategory);
        category.adapter(savecategory);

        return category;

    }

    @Override
    public void delete(Long id) {
        Category c = repository.findById(id).get();
        System.out.println(c);
        // x TodoList t = listRepository.findByPriority(p.getName());
        CategoryDto cdto = new CategoryDto().adapter(c);
        System.out.println("dto   " + cdto);

        List<TodoList> list = listRepository.findByCategory(c);
        if (Objects.nonNull(list)) {

            for(int i=0;i<list.size();i++) {
                listRepository.deleteById(list.get(i).getId());
            }
            repository.deleteById(id);
        }
    }

    @Override
    public void update(CategoryDto categoryDto) {
        repository.update(categoryDto.getId(), categoryDto.getName());
    }
}


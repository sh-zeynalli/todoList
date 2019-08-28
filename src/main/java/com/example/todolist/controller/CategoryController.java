package com.example.todolist.controller;

import com.example.todolist.handling.exception.Response;
import com.example.todolist.model.CategoryDto;
import com.example.todolist.model.PriorityDto;
import com.example.todolist.model.TodoListDto;
import com.example.todolist.persistance.service.CategoryServiceImpl;
import com.example.todolist.persistance.service.PriorityServiceImpl;
import com.example.todolist.persistance.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class CategoryController {

    @Autowired
    private TodoListService todoListService;

    @Autowired
    private CategoryServiceImpl categoryService;


    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ModelAndView category(ModelAndView modelAndView) {
        modelAndView.addObject("todo", new TodoListDto());
        modelAndView.addObject("todoList", todoListService.findAll());
        modelAndView.addObject("categoryObject", new CategoryDto());
        modelAndView.addObject("category", categoryService.findAll());
        modelAndView.setViewName("category");
        return modelAndView;
    }

    @RequestMapping(value = "/categories/addCategory", method = RequestMethod.POST)
    @ResponseBody
    public Response addCategory(@ModelAttribute @Valid CategoryDto categoryDto, BindingResult errors) {

        Response myResponse=new Response();
        if (!errors.hasErrors()) {
            categoryService.save(categoryDto);
            myResponse.setMessage("Category was successfully added");
            myResponse.setCode(200);

        } else {
            myResponse.setMessage("Please add category");
            myResponse.setCode(500);
        }

        return myResponse;
    }

    @RequestMapping(value = "/categories/deleteCategory/{id}", method = RequestMethod.GET)
    public ModelAndView deleteCategory(@PathVariable("id") Long id, ModelAndView modelAndView) {
        categoryService.delete(id);
        modelAndView.addObject("categoryObject", new CategoryDto());
        modelAndView.addObject("category", categoryService.findAll());
        modelAndView.setViewName("category");
        modelAndView.setViewName("redirect:/categories");
        return modelAndView;
    }

    @Transactional
    @RequestMapping(value = "/categories/editCategory", method = RequestMethod.GET)
    @ResponseBody
    public Response editCategory(@ModelAttribute @Valid CategoryDto updatedcategorydto, BindingResult errors) {
        Response myResponse=new Response();
        if (!errors.hasErrors()) {
            categoryService.update(updatedcategorydto);
            myResponse.setMessage("Category was successfully updated");
            myResponse.setCode(200);

        } else {
            myResponse.setMessage("Please add category");
            myResponse.setCode(500);

        }

        return myResponse;
    }

}

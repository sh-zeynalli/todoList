package com.example.todolist.controller;

import com.example.todolist.handling.exception.Response;
import com.example.todolist.model.CategoryDto;
import com.example.todolist.model.PriorityDto;
import com.example.todolist.model.TodoListDto;
import com.example.todolist.persistance.service.CategoryServiceImpl;
import com.example.todolist.persistance.service.PriorityServiceImpl;
import com.example.todolist.persistance.service.TodoListService;
import com.example.todolist.search.SearchReq;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;


@Controller
public class MainController {

    @Autowired
    private TodoListService todoListService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private PriorityServiceImpl priorityService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(ModelAndView modelAndView) {

        modelAndView.addObject("todo", new TodoListDto());
        modelAndView.addObject("todoList", todoListService.findAll());
        modelAndView.addObject("categoryObject", new CategoryDto());
        modelAndView.addObject("category", categoryService.findAll());
        modelAndView.addObject("priorityObject", new PriorityDto());
        modelAndView.addObject("priority", priorityService.findAll());

        modelAndView.setViewName("index");
        modelAndView.addObject("message", todoListService.noTaskInfo());
        return modelAndView;
    }

    @PostMapping(value = "/addTodo")
    @ResponseBody
    public Response addTodo(@ModelAttribute @Valid TodoListDto todoListDto, BindingResult errors) {

        Response myResponse = new Response();

        System.out.println(todoListDto);


        if (todoListDto.getCategory().equals("null") || todoListDto.getCategory().equals("null")) {
            myResponse.setCode(503);
            myResponse.setMessage("Please fill all fields");
            System.out.println("1 if");
        } else {
            if (!errors.hasErrors()) {
                todoListService.save(todoListDto);
                myResponse.setCode(200);
                myResponse.setMessage("You have new task!");

            } else {
                myResponse.setCode(500);
                myResponse.setMessage("Please fill all fields");
            }
        }

        return myResponse;

    }

    @RequestMapping(value = "/deleteTodo/{id}", method = RequestMethod.GET)
    public ModelAndView deleteTodo(@PathVariable("id") Long id, ModelAndView modelAndView) {
        todoListService.delete(id);
        modelAndView.addObject("todo", new TodoListDto());
        modelAndView.addObject("todoList", todoListService.findAll());
        modelAndView.setViewName("index");
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public  ModelAndView  search(@ModelAttribute SearchReq input, ModelAndView view){
        List<TodoListDto> list = todoListService.findAll(input);
        view.addObject("todoListSearch", list);
        view.addObject("todo", new TodoListDto());
        view.setViewName("search");
        view.addObject("noResult", todoListService.noSearchResult(input));
        return  view;
    }

    @ResponseBody
    @Transactional
    @GetMapping(value = "/editTodo")
    public Response editTodo(@ModelAttribute @Valid TodoListDto updatedDto, BindingResult errors) {

        Response myResponse = new Response();

        if (updatedDto.getCategory().equals("null") || updatedDto.getCategory().equals("null")) {
            myResponse.setCode(503);
            myResponse.setMessage("Please fill all fields");
            System.out.println("1 if");
        } else {
            if (errors.hasErrors()) {
                myResponse.setMessage("Please fill all fields");
                myResponse.setCode(500);

            } else {

                todoListService.update(updatedDto);
                myResponse.setMessage("Task was successfully updated");
                myResponse.setCode(200);
            }
        }
        return myResponse;
    }

}



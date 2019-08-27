package com.example.todolist.controller;

import com.example.todolist.handling.exception.Response;
import com.example.todolist.model.CategoryDto;
import com.example.todolist.model.PriorityDto;
import com.example.todolist.model.TodoListDto;
import com.example.todolist.persistance.service.CategoryServiceImpl;
import com.example.todolist.persistance.service.PriorityServiceImpl;
import com.example.todolist.persistance.service.TodoListService;
import com.example.todolist.search.SearchReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
        System.out.println(errors.hasErrors());
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

    @GetMapping(value = "/search")
    public Response search(@RequestBody SearchReq input){

        System.out.println(input);
        return  new Response();
    }

//    @GetMapping(value = "/search")
//    public String search(ModelAndView modelAndView, String task, String priority, String category, String deadline){
//        System.out.println("aaaaaaaaaaaa");
//
//        Date date=new Date();
//        try {
//            date = new SimpleDateFormat("MM/dd/yyyy").parse(deadline);
//        } catch (ParseException e) {
//
//            try {
//                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(deadline);
//            } catch (ParseException e1) {
//
//            }
//        }
//
//        List<TodoListDto> searchResult = todoListService.advancedSearch(task, priority, category, date);
//
//        System.out.println(searchResult);
//        return "index";
//    }

    @ResponseBody
    @Transactional
    @GetMapping(value = "/editTodo")
    public Response editTodo(@ModelAttribute @Valid TodoListDto updatedDto, BindingResult errors) {

        System.out.println(updatedDto.getDeadline());
        Date date = new Date();

        try {
            date = new SimpleDateFormat("MM/dd/yyyy").parse(updatedDto.getDeadline().toString());
        } catch (ParseException e) {

            try {
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(updatedDto.getDeadline().toString());
            } catch (ParseException e1) {

            }
        }

        updatedDto.setDeadline(date);
        System.out.println(updatedDto);

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



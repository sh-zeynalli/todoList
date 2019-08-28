package com.example.todolist.controller;

import com.example.todolist.handling.exception.Response;
import com.example.todolist.model.CategoryDto;
import com.example.todolist.model.PriorityDto;
import com.example.todolist.model.TodoListDto;
import com.example.todolist.persistance.service.CategoryServiceImpl;
import com.example.todolist.persistance.service.PriorityServiceImpl;
import com.example.todolist.persistance.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
@Controller
public class PriorityController {

    @Autowired
    private TodoListService todoListService;

    @Autowired
    private PriorityServiceImpl priorityService;

    public List<PriorityDto> priorityDtoList() {

        List<PriorityDto> priorityDtoList = priorityService.findAll().stream()
                .map(p -> new PriorityDto(p.getId(), p.getName())).collect(Collectors.toList());

        return priorityDtoList;
    }

    @RequestMapping(value = "/priorities", method = RequestMethod.GET)
    public ModelAndView priority(ModelAndView modelAndView) {

        modelAndView.addObject("todo", new TodoListDto());
        modelAndView.addObject("todoList", todoListService.findAll());
        modelAndView.addObject("priorityObject", new PriorityDto());
        modelAndView.addObject("priority", priorityDtoList());
        modelAndView.setViewName("priority");
        return modelAndView;
    }

    @RequestMapping(value = "/priorities/addPriority", method = RequestMethod.POST)
    @ResponseBody
    public Response addPriority(@ModelAttribute @Valid PriorityDto priorityDto, BindingResult errors) {

        Response myResponse=new Response();
        if (!errors.hasErrors()) {
            priorityService.save(priorityDto);
            myResponse.setMessage("Priority was successfully added");
            myResponse.setCode(200);

        } else {
            myResponse.setMessage("Please add priority");
            myResponse.setCode(500);
        }

        return myResponse;
    }

    @RequestMapping(value = "/priorities/deletePriority/{id}", method = RequestMethod.GET)
    public ModelAndView deletePriority(@PathVariable("id") Long id, ModelAndView modelAndView) {
        priorityService.delete(id);
        modelAndView.addObject("priorityObject", new PriorityDto());
        modelAndView.addObject("priority", priorityDtoList());
        modelAndView.setViewName("priority");
        modelAndView.setViewName("redirect:/priorities");
        return modelAndView;
    }

    @Transactional
    @RequestMapping(value = "/priorities/editPriority", method = RequestMethod.GET)
    @ResponseBody
    public Response editCategory(@ModelAttribute @Valid PriorityDto updatedpriorityDto, BindingResult errors) {
        Response myResponse=new Response();
        if (!errors.hasErrors()) {
            priorityService.update(updatedpriorityDto);
            myResponse.setMessage("Priority was successfully updated");
            myResponse.setCode(200);

        } else {
            myResponse.setCode(500);
            myResponse.setMessage("Please add priority");
        }

        return myResponse;
    }


}

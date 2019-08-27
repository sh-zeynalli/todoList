package com.example.todolist.controller;

import com.example.todolist.model.TodoListDto;
import com.example.todolist.persistance.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.time.LocalDateTime;

@Controller
public class DoneController {


    @Autowired
    private TodoListService todoListService;

    @Transactional
    @RequestMapping(value = "/done/{id}", method = RequestMethod.GET)
    public ModelAndView doneTask(@PathVariable("id") Long id, ModelAndView modelAndView) {

        LocalDateTime dateNow = LocalDateTime.now();
        todoListService.changeStatustoOne(id);
        todoListService.save(todoListService.findById(id));
        modelAndView.addObject("todo", new TodoListDto());
        modelAndView.addObject("todoList", todoListService.findAll());
        modelAndView.addObject("endDate", todoListService.doneTaskDate(id, dateNow));
        modelAndView.setViewName("index");
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @RequestMapping(value = "/done", method = RequestMethod.GET)
    public ModelAndView done(ModelAndView modelAndView) {

        modelAndView.addObject("todo", new TodoListDto());
        modelAndView.addObject("todoList", todoListService.findAll());
        modelAndView.setViewName("done");
        for (int i = 0; i < todoListService.findAll().size(); i++) {
            modelAndView.addObject("endDate", todoListService.findAll().get(i).getDeadline());
        }
        modelAndView.addObject("doneMessage", todoListService.noDoneTaskInfo());
        return modelAndView;
    }

    @Transactional
    @RequestMapping(value = "/done/restore/{id}", method = RequestMethod.GET)
    public ModelAndView restore(@PathVariable("id") Long id, ModelAndView modelAndView) {

        todoListService.changeStatustoZero(id);
        todoListService.save(todoListService.findById(id));
        modelAndView.addObject("todo", new TodoListDto());
        modelAndView.addObject("todoList", todoListService.findAll());
        modelAndView.setViewName("done");
        modelAndView.setViewName("redirect:/done");
        return modelAndView;
    }

}

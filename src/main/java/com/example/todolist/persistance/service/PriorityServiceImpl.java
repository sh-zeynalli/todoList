package com.example.todolist.persistance.service;

import com.example.todolist.model.CategoryDto;
import com.example.todolist.model.PriorityDto;
import com.example.todolist.model.TodoListDto;
import com.example.todolist.persistance.entity.Priority;
import com.example.todolist.persistance.entity.TodoList;
import com.example.todolist.persistance.repository.PriorityRepository;
import com.example.todolist.persistance.repository.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PriorityServiceImpl implements PriorityService {

    @Autowired
    private PriorityRepository repository;

    @Autowired
    private TodoListRepository listRepository;

    @Override
    public void delete(Long id) {
        {
            Priority p = repository.findById(id).get();
            System.out.println(p);
            // x TodoList t = listRepository.findByPriority(p.getName());
            PriorityDto pdto = new PriorityDto().adapter(p);
            System.out.println("dto   " + pdto);
            List<TodoList> list = listRepository.findByPriority(p);
            if (Objects.nonNull(list)) {
                for(int i=0;i<list.size();i++) {
                    listRepository.deleteById(list.get(i).getId());
                }
                repository.deleteById(id);
            }
        }
    }



    @Override
    public PriorityDto findByName(String name) {
        Priority find=repository.findbyName(name);
        PriorityDto findDto=new PriorityDto().adapter(find);
        return findDto;
    }

    @Override
    public PriorityDto save(PriorityDto p) {

        Priority savepriority=new Priority(p.getId(), p.getName());
        savepriority=repository.save(savepriority);
        p.adapter(savepriority);

        return p;
    }

    @Override
    public void update(PriorityDto priorityDto) {
        repository.update(priorityDto.getId(), priorityDto.getName());
    }

    @Override
    public List<PriorityDto> findAll() {

        List<Priority> priorityList = repository.findAll();
        List<PriorityDto> priorityDtoList=priorityList.stream().map(p -> new PriorityDto(
                p.getId(),
                p.getName()
        )).collect(Collectors.toList());

        return priorityDtoList;
    }
}

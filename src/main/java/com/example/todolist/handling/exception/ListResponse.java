//package com.example.todolist.handling.exception;
//
//import com.example.todolist.model.TodoListDto;
//
//import java.util.List;
//
//public class ListResponse extends Response {
//
//    private TodoListDto todoInfo;
//
//    private List<TodoListDto> list;
//
//    public ListResponse(ErrorCodes codes, TodoListDto todoInfo) {
//        super(codes);
//        this.todoInfo = todoInfo;
//
//    }
//
//    public ListResponse(ErrorCodes errorCodes, List<TodoListDto> list) {
//        super(errorCodes);
//        this.list = list;
//    }
//
//    public ListResponse() {
//    }
//
//    public TodoListDto getTodoInfo() {
//        return todoInfo;
//    }
//
//    public void setTodoInfo(TodoListDto todoInfo) {
//        this.todoInfo = todoInfo;
//    }
//
//    public List<TodoListDto> getList() {
//        return list;
//    }
//
//    public void setList(List<TodoListDto> list) {
//        this.list = list;
//    }
//}

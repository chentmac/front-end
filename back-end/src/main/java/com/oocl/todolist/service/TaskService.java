package com.oocl.todolist.service;


import com.oocl.todolist.vo.TaskVo;

import java.util.List;

public interface TaskService {
    boolean save(TaskVo taskVo);
    List<TaskVo> findAll();
}

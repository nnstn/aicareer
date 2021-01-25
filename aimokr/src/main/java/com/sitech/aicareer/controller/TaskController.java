package com.sitech.aicareer.controller;

import com.sitech.aicareer.bean.JsonData;
import com.sitech.aicareer.bean.PageResult;
import com.sitech.aicareer.bean.query.TaskPageQuery;
import com.sitech.aicareer.pojo.Task;
import com.sitech.aicareer.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("task")
public class TaskController {
    @Autowired
    TaskService taskService;

    @PostMapping("getall")
    public JsonData getAllTask(@RequestBody TaskPageQuery pageQuery) {
        PageResult<Task> pageResult = taskService.getAllTask(pageQuery);
        return JsonData.success(pageResult);
    }
    @PostMapping("schedule")
    public JsonData getSchedule(@RequestBody TaskPageQuery pageQuery) {
        List<Task> schedules = taskService.getTaskSchedule(pageQuery);
        return JsonData.success(schedules);
    }

    @PostMapping("insert")
    public JsonData insertTask(@RequestBody Task task) {
        taskService.insertTask(task);
        return JsonData.success("添加成功");
    }

    @PostMapping("update")
    public JsonData updateTask(@RequestBody Task task) {
        taskService.updateTask(task);
        return JsonData.success("更新成功");
    }

    @PostMapping("delete/{id}")
    public JsonData deleteTask(@PathVariable("id") Long taskId) {
        taskService.deleteTask(taskId);
        return JsonData.success("删除成功");
    }
    @PostMapping("donetask")
    public JsonData doneTask(@RequestBody  List<Long> ids) {
        taskService.updateTaskStateBatch(ids,3);
        return JsonData.success("删除成功");
    }

}

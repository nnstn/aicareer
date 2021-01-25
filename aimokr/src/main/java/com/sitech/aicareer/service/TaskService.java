package com.sitech.aicareer.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sitech.aicareer.bean.PageResult;
import com.sitech.aicareer.bean.query.TaskPageQuery;
import com.sitech.aicareer.mapper.TaskMapper;
import com.sitech.aicareer.pojo.Task;
import com.sitech.aicareer.pojo.User;
import com.sitech.aicareer.web.handler.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class TaskService {

    @Resource
    TaskMapper taskMapper;

    /**
     * 分页查询
     */
    public PageResult<Task> getAllTask(TaskPageQuery pageQuery) {
        // 分页
        PageHelper.startPage(pageQuery.getPageNo(), pageQuery.getPageSize(), pageQuery.getSortBy() + (pageQuery.getDesc() ? " DESC" : " ASC"));
        // 查询
        List<Task> tasks = taskMapper.getAllTask(pageQuery);

        PageInfo<Task> info = new PageInfo<>(tasks);
        // 解析分页结果
        return new PageResult<Task>(info.getTotal(), tasks);

    }

    public List<Task> getTaskSchedule(TaskPageQuery pageQuery) {
        List<Task> tasks = taskMapper.getAllTask(pageQuery);
        return tasks;
    }

    public int insertTask(Task task) {
        User user = RequestHolder.getCurrentUser();
        HttpServletRequest request = RequestHolder.getCurrentRequest();

        if (null == task.getDispatcher()) {
            task.setDispatcher(user.getUserCode());
        }
        if (null == task.getTasker()) {
            task.setTasker(user.getUserCode());
        }
        if (0 == task.getState()) {
            /** 任务初始状态为 **/
            task.setState(1);
        }

        task.setCreateTime(new Date());
        task.setLastUpdateTime(new Date());
        task.setOpeationIp(request.getRemoteAddr());
        return taskMapper.insert(task);
    }

    public int updateTask(Task task) {
        task.setLastUpdateTime(new Date());
        return taskMapper.updateByPrimaryKey(task);
    }

    public int deleteTask(Long taskId) {
        return taskMapper.deleteByPrimaryKey(taskId);
    }

    public void updateTaskStateBatch(List<Long> ids, Integer state) {
        log.info("更新了: " + taskMapper.updateTaskStateBatch(ids, state) + " 条数据");
    }
}

package com.sitech.aicareer.mapper;

import com.sitech.aicareer.bean.query.TaskPageQuery;
import com.sitech.aicareer.pojo.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface TaskMapper extends Mapper<Task> {

    @SelectProvider(type = DynamicTaskProvider.class, method = "getAllTaskByCondition")
    List<Task> getAllTask(TaskPageQuery pageQuery);

    @UpdateProvider(type = DynamicTaskProvider.class, method = "updateTaskStateBatch")
    int updateTaskStateBatch(List<Long> ids, Integer state);

    class DynamicTaskProvider{
        public String getAllTaskByCondition(TaskPageQuery pageQuery){
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("select * from task where 1=1");
            if(StringUtils.isNoneBlank(pageQuery.getTaskName())){ //任务模糊搜索
                sqlBuilder.append(" and  task_name like CONCAT('%','"+pageQuery.getTaskName()+"','%')");
            }
            if(StringUtils.isNoneBlank(pageQuery.getTasker())){ //按任务所有者搜索
                sqlBuilder.append(" and  tasker ='"+pageQuery.getTasker().trim()+"'");
            }
            if(StringUtils.isNoneBlank(pageQuery.getState())){ //按任务任务状态
                sqlBuilder.append(" and  state ='"+pageQuery.getState().trim()+"'");
            }
            if(null!=pageQuery.getStartTime()){
                sqlBuilder.append(" and  end_date > '"+pageQuery.getStartTime()+"' ");
            }
            if(null!=pageQuery.getEndTime()){
                sqlBuilder.append(" and  start_date < '"+pageQuery.getEndTime()+"' ");
            }
            return sqlBuilder.toString();
        }

        public String updateTaskStateBatch(List<Long> ids,Integer state){
            if (null==ids||ids.size()==0||null==state){
                throw new RuntimeException("SQL参数有误");
            }
            StringBuilder updateSql = new StringBuilder();
            updateSql.append("update task set state = "+state+" where id in (");
            for (int i=0 ;i < ids.size();i++){
                updateSql.append(ids.get(i)+", ");
            }
            updateSql.append("0 )");
            return updateSql.toString();
        }
    }
}

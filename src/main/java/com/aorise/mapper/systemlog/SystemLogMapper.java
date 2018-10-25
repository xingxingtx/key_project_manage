package com.aorise.mapper.systemlog;

import com.aorise.model.systemlog.SystemLogModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author:Huangdong
 * @Date:2018/8/28 10:12
 * @Version V1.0
 */
@Mapper
@Component(value = "SystemLogMapper")
public interface SystemLogMapper {
    /**
     * @Author:Huangdong
     * @Desicription:
     * @param log 系统日志实体
     * @return
     */
   void saveUser(SystemLogModel log);
    /**
     * @Author:Huangdong
     * @Desicription:查询系统日志总数
     * @return
     */
    public Long SumSystemLog();
    /**
     * @Author:Huangdong
     * @Desicription:分页查询系统日志信息
     * @param map 查询参数
     * @return List<SystemLogModel>
     * @throws Exception
     */
    List<SystemLogModel> getSystemLog(Map<String, Object> map);
}

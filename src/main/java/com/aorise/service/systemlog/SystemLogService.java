package com.aorise.service.systemlog;


import com.aorise.model.systemlog.SystemLogModel;
import com.aorise.utils.page.Page;
import java.util.Map;
import java.util.List;

/**
 * @Description:
 * @Author:Huangdong
 * @Date:2018/8/28 9:57
 * @Version V1.0
 */
public interface SystemLogService {
    /**
     * @Author:Huangdong
     * @Desicription:
     * @param log 系统日志实体
     * @return
     */
    public void saveUser(SystemLogModel log);

    /**
     * @Author:Huangdong
     * @Desicription:分页查询系统日志信息
     * @param map 查询参数
     * @return List<SystemLogModel>
     * @throws Exception
     */
    public List<SystemLogModel> getSystemLog(Map<String, Object> map,Page page)throws Exception;
}

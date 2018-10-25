package com.aorise.service.systemlog.impl;


import com.aorise.mapper.systemlog.SystemLogMapper;
import com.aorise.model.systemlog.SystemLogModel;
import com.aorise.service.systemlog.SystemLogService;
import com.aorise.utils.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author:Huangdong
 * @Date:2018/8/28 9:58
 * @Version V1.0
 */
@Service
public class SystemLogServiceImpl implements SystemLogService {
    @Autowired
    SystemLogMapper systemLogMapper;

    /**
     * @Author:Huangdong
     * @Desicription:
     * @param log 系统日志实体
     * @return
     */
    @Override
    public void saveUser(SystemLogModel log) {
        systemLogMapper.saveUser(log);
    }
    /**
     * @Author:Huangdong
     * @Desicription:分页查询系统日志信息
     * @param map 查询参数
     * @return List<SystemLogModel>
     * @throws Exception
     */
    @Override
    public List<SystemLogModel> getSystemLog(Map<String, Object> map, Page page) throws Exception {
        List<SystemLogModel> list = null;
        /**查询总数*/
        Long count = systemLogMapper.SumSystemLog();

        if (count > 0) {
            page.setTotalCount(Long.valueOf(count));
            page.setTotalPage(Long.valueOf(count), page.getEveryPage());
            if (page.getCurrentPage() > 0 && page.getEveryPage() > 0) {
                /**查询分页条数*/
                list = systemLogMapper.getSystemLog(map);
            }
        } else {
            page.setTotalCount(0L);
        }
        return list;
    }
}

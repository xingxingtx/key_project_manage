package com.aorise.mapper.notification;


import com.aorise.model.information.InformationModel;
import com.aorise.model.notification.NotificationModel;
import com.aorise.utils.validation.DataValidation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author:Shenzhiwei
 * @Desicription:通知管理数据持久化层
 * @Date:Created in 2018-09-05 14:59
 * @Modified By:
 */
@Mapper
@Component
public interface NotificationMapper {

    /**
     *@Author:Shenzhiwei
     *@Desicription:添加通知管理数据
      * @param model 通知管理实体对象
     *@Date:Created in 15:27 2018/9/5
     *@Return:返回受影响的行数
     * @Modified By:
     */
    int insertObject(NotificationModel model) throws DataAccessException;
    /**
     *@Author:Shenzhiwei
     *@Desicription:根据id查询通知管理实体
      * @param id 主键id
     *@Date:Created in 15:27 2018/9/5
     *@Return:返回实体对象
     * @Modified By:
     */
    NotificationModel getDetail(int id) throws DataAccessException;
    /**
     *@Author:Shenzhiwei
     *@Desicription:分页查询通知管理数据
      * @param map 查询条件
     *@Date:Created in 15:28 2018/9/5
     *@Return:返回数据集合
     * @Modified By:
     */
    List<NotificationModel> getAllByPage(Map<String, Object> map) throws DataAccessException;
    int getAllByPageCount(Map<String, Object> map) throws DataAccessException;
    /**
     *@Author:Shenzhiwei
     *@Desicription:修改通知管理数据
     * @param model 通知管理实体对象
     *@Date:Created in 15:27 2018/9/5
     *@Return:返回受影响的行数
     * @Modified By:
     */
    int updateStatus(NotificationModel model) throws DataAccessException;
    /**
     *@Author:Shenzhiwei
     *@Desicription:根据id查询资讯管理实体
     * @param id 主键id
     *@Date:Created in 15:27 2018/9/5
     *@Return:返回实体对象
     * @Modified By:
     */
    NotificationModel getOutDetail(int id)throws DataAccessException;
}

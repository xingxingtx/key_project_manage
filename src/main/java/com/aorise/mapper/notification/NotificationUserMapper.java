package com.aorise.mapper.notification;

import com.aorise.model.notification.NotificationModel;
import com.aorise.model.notification.NotificationUserModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author:Shenzhiwei
 * @Desicription:通知用户数据库持久层
 * @Date:Created in 2018-09-17 09:48
 * @Modified By:
 */
@Component
@Mapper
public interface NotificationUserMapper {

    /**
     *@Author:Shenzhiwei
     *@Desicription:添加通知用户关系数据
      * @param model 实体对象
     *@Date:Created in 10:04 2018/9/17
     *@Return:返回受影响的行数
     * @Modified By:
     */
    int insertObject(NotificationUserModel model)throws DataAccessException;
    /**
     *@Author:Shenzhiwei
     *@Desicription:修改已读状态
     * @param map 修改条件参数
     *@Date:Created in 10:04 2018/9/17
     *@Return:返回受影响的行数
     * @Modified By:
     */
    int updateStatus(Map<String, Object> map)throws DataAccessException;

    /**
     *@Author:Shenzhiwei
     *@Desicription:根据用户id删除数据
     * @param model 删除条件参数
     *@Date:Created in 10:04 2018/9/17
     *@Return:返回受影响的行数
     * @Modified By:
     */
    int deleteByUserId(NotificationUserModel model)throws DataAccessException;
    /**
     *@Author:Shenzhiwei
     *@Desicription:根据通知id删除数据
     * @param notificationId 通知id
     *@Date:Created in 10:04 2018/9/17
     *@Return:返回受影响的行数
     * @Modified By:
     */
    int deleteByNotificationId(int notificationId)throws DataAccessException;
    /**
     *@Author:Shenzhiwei
     *@Desicription:根据用户id查询未读消息
     * @param userId 用户id
     *@Date:Created in 10:04 2018/9/17
     *@Return:返回受影响的行数
     * @Modified By:
     */
    int getByOnRead(int userId) throws DataAccessException;

    /**
     *@Author:Shenzhiwei
     *@Desicription:根据用户id查询收件箱
      * @param map 查询条件
     *@Date:Created in 9:43 2018/9/21
     *@Return:返回数据集合
     * @Modified By:
     */
    List<NotificationModel> getAllByPage(Map<String, Object> map)throws DataAccessException;
    int getAllByPageCount(Map<String, Object> map)throws DataAccessException;
}

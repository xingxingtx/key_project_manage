package com.aorise.service.notification;

import com.aorise.model.information.InformationModel;
import com.aorise.model.notification.NotificationModel;
import com.aorise.utils.page.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author:Shenzhiwei
 * @Desicription:
 * @Date:Created in 2018-09-20 09:19
 * @Modified By:
 */
public interface NotificationService {
    /**
     *@Author:Shenzhiwei
     *@Desicription:添加通知管理数据
     * @param model 通知管理实体对象
     *@Date:Created in 15:27 2018/9/5
     *@Return:返回受影响的行数
     * @Modified By:
     */
    int insertObject(NotificationModel model);
    /**
     *@Author:Shenzhiwei
     *@Desicription:根据id查询通知管理实体
     * @param id 主键id
     *@Date:Created in 15:27 2018/9/5
     *@Return:返回实体对象
     * @Modified By:
     */
    NotificationModel getDetail(int id,int userId);
    /**
     *@Author:Shenzhiwei
     *@Desicription:根据id查询资讯管理实体
     * @param id 主键id
     *@Date:Created in 15:27 2018/9/5
     *@Return:返回实体对象
     * @Modified By:
     */
    NotificationModel getOutDetail(int id);
    /**
     *@Author:Shenzhiwei
     *@Desicription:查询收件箱
     * @param map 查询条件
     *@Date:Created in 15:28 2018/9/5
     *@Return:返回数据集合
     * @Modified By:
     */
    List<NotificationModel> myInbox(Map<String, Object> map, Page page);
    /**
     *@Author:Shenzhiwei
     *@Desicription:查询发件箱
     * @param map 查询条件
     *@Date:Created in 15:28 2018/9/5
     *@Return:返回数据集合
     * @Modified By:
     */
    List<NotificationModel> myOutbox(Map<String, Object> map, Page page);
    /**
     *@Author:Shenzhiwei
     *@Desicription:修改通知管理数据
     * @param userId 用户id
     * @param notificationId 通知id
     * @param type 1=收件箱，2=发件箱
     *@Date:Created in 15:27 2018/9/5
     *@Return:返回受影响的行数
     * @Modified By:
     */
    int updateStatus(int userId,String notificationId,int type);
}

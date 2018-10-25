package com.aorise.service.notification.impl;

import com.aorise.exceptions.SendMsgExeption;
import com.aorise.exceptions.ServiceException;
import com.aorise.mapper.notification.NotificationMapper;
import com.aorise.mapper.notification.NotificationUserMapper;
import com.aorise.mapper.system.SysUserMapper;
import com.aorise.model.information.InformationModel;
import com.aorise.model.notification.NotificationModel;
import com.aorise.model.notification.NotificationUserModel;
import com.aorise.model.system.SysUserModel;
import com.aorise.service.notification.NotificationService;
import com.aorise.utils.PushMessage;
import com.aorise.utils.define.ConstDefine;
import com.aorise.utils.page.Page;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:Shenzhiwei
 * @Desicription:通知管理业务逻辑类
 * @Date:Created in 2018-09-20 09:20
 * @Modified By:
 */
@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationUserMapper notificationUserMapper;
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private PushMessage pushMessage;
    /**
     * 日志打印器
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * @param model 通知管理实体对象
     * @Author:Shenzhiwei
     * @Desicription:添加通知管理数据
     * @Date:Created in 15:27 2018/9/5
     * @Return:返回受影响的行数
     * @Modified By:
     */
    @Override
    @Transactional
    public int insertObject(NotificationModel model) {
         notificationMapper.insertObject(model);
       int notificationId=model.getId();
            String[] groupIds = model.getGroupIds().split(",");
            for (String groupId : groupIds) {
                NotificationUserModel notificationUserModel = new NotificationUserModel();
                notificationUserModel.setIsRead(0);//设置状态未读
                notificationUserModel.setUserId(Integer.parseInt(groupId));
                notificationUserModel.setState(ConstDefine.STATE_ABLE);
                notificationUserModel.setNotificationId(notificationId);

                notificationUserMapper.insertObject(notificationUserModel);
                //通知推送
                try {
                    pushMessage.PushMessage(groupId,model.getTitle());
                }catch (SendMsgExeption exeption){
                    throw new ServiceException(exeption.getMessage());
                }
            }
        return notificationId;
    }

    /**
     * @param userId 用户id
     * @param id 通知id
     * @Author:Shenzhiwei
     * @Desicription:根据id查询通知管理实体
     * @Date:Created in 15:27 2018/9/5
     * @Return:返回实体对象
     * @Modified By:
     */
    @Override
    public NotificationModel getDetail(int id,int userId) {
        //更改已读通知
        Map<String, Object> map = new HashMap<>(3);
        map.put("isRead",1);
        map.put("notificationId",id);
        map.put("userId",userId);
        notificationUserMapper.updateStatus(map);
        return notificationMapper.getDetail(id);
    }

    /**
     * @param id 主键id
     * @Author:Shenzhiwei
     * @Desicription:根据id查询资讯管理实体
     * @Date:Created in 15:27 2018/9/5
     * @Return:返回实体对象
     * @Modified By:
     */
    @Override
    public NotificationModel getOutDetail(int id) {
        return  notificationMapper.getOutDetail(id);
    }

    /**
     * @param map 查询条件
     * @Author:Shenzhiwei
     * @Desicription:查询收件箱
     * @Date:Created in 15:28 2018/9/5
     * @Return:返回数据集合
     * @Modified By:
     */
    @Override
    public List<NotificationModel> myInbox(Map<String, Object> map, Page page) {
        List<NotificationModel> list = null;
        /**查询总数*/
        Integer count = notificationUserMapper.getAllByPageCount(map);
        if (count > 0) {
            page.setTotalCount(Long.valueOf(count));
            page.setTotalPage(Long.valueOf(count), page.getEveryPage());
            if (page.getCurrentPage() > 0 && page.getEveryPage() > 0) {
                /**查询分页条数*/
                list = notificationUserMapper.getAllByPage(map);
            }
        } else {
            page.setTotalCount(0L);
        }
        return list;
    }

    /**
     * @param map  查询条件
     * @param page
     * @Author:Shenzhiwei
     * @Desicription:查询发件箱
     * @Date:Created in 15:28 2018/9/5
     * @Return:返回数据集合
     * @Modified By:
     */
    @Override
    public List<NotificationModel> myOutbox(Map<String, Object> map, Page page) {
        List<NotificationModel> list = null;
        /**查询总数*/
        Integer count = notificationMapper.getAllByPageCount(map);
        if (count > 0) {
            page.setTotalCount(Long.valueOf(count));
            page.setTotalPage(Long.valueOf(count), page.getEveryPage());
            if (page.getCurrentPage() > 0 && page.getEveryPage() > 0) {
                /**查询分页条数*/
                list = notificationMapper.getAllByPage(map);
            }
        } else {
            page.setTotalCount(0L);
        }
        return list;
    }

    /**
     * @param userId         用户id
     * @param notificationId 通知id
     * @param type           1=收件箱，2=发件箱
     * @Author:Shenzhiwei
     * @Desicription:修改通知管理数据
     * @Date:Created in 15:27 2018/9/5
     * @Return:返回受影响的行数
     * @Modified By:
     */
    @Override
    @Transactional
    public int updateStatus(int userId, String notificationId, int type) {
        try {
            String[] nId=notificationId.split(",");//分隔通知id数组
            int ret=0;//返回值
            for (String s : nId) {//循环id
                if (!Strings.isBlank(s)) { //判断是否为空
                    if (type == 1) {//删除收件箱
                        NotificationUserModel notificationUserModel = new NotificationUserModel();
                        notificationUserModel.setUserId(userId);
                        notificationUserModel.setNotificationId(Integer.parseInt(s));
                        notificationUserModel.setState(ConstDefine.STATE_DISABLE);
                        notificationUserMapper.deleteByUserId(notificationUserModel);
                    } else {//删除发件箱
                        //先删除通知数据
                        NotificationModel notificationModel = new NotificationModel();
                        notificationModel.setId(Integer.parseInt(s));
                        notificationModel.setState(ConstDefine.STATE_DISABLE);
                        notificationMapper.updateStatus(notificationModel);
                        //删除通知用户关系表数据
                        notificationUserMapper.deleteByNotificationId(Integer.parseInt(s));
                    }
                }
                ret++;
            }
            return ret;
        } catch (Exception e) {
            logger.error("error:" + e.getMessage());
            throw new ServiceException("删除失败");
        }
    }
}

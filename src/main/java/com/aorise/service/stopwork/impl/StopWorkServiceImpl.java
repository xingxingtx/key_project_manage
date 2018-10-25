package com.aorise.service.stopwork.impl;


import com.aorise.mapper.stopwork.StopWorkMapper;
import com.aorise.mapper.system.SysUserMapper;
import com.aorise.model.notification.NotificationModel;
import com.aorise.model.stopwork.StopWorkInfoModel;
import com.aorise.model.stopwork.StopWorkModel;
import com.aorise.model.system.SysUserModel;
import com.aorise.service.notification.NotificationService;
import com.aorise.service.stopwork.StopWorkService;
import com.aorise.utils.Utils;
import com.aorise.utils.define.ConstDefine;
import com.aorise.utils.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author:wei.peng
 * @Desicription:施工现场阻工
 * @Date:Created in 2018/9/27 13:48
 * @Modified By:
 */
@Service
@Transactional
public class StopWorkServiceImpl implements StopWorkService {
    @Autowired
    private StopWorkMapper stopWorkMapper;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private SysUserMapper sysUserMapper;
    /**
     * 新增施工现场阻工处理信息
     * @param userId 创建人id
     * @param model  施工现场阻工处理信息实体类
     */
    @Override
    public Integer addStopWork(String userId, StopWorkModel model, String url) {
       int rel =  stopWorkMapper.addStopWork(userId,model);
       if(rel > 0){
           //如果处理新增成功，添加通知
           NotificationModel nModel = new NotificationModel();
           nModel.setContent(url+"api/stopWork/id/"+model.getWorkId());
           //查询施工过程中隐患详情
           StopWorkInfoModel cModel = stopWorkMapper.getStopWorkInfo(model.getWorkId());
           if(cModel != null){
               nModel.setTitle(cModel.getName() + "施工现场阻工处理");
           }
           nModel.setType(1);//系统通知
           nModel.setGroupIds(model.getDisposePeople());
           nModel.setCreateTime(Utils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
           nModel.setCreater(Integer.parseInt(userId));
           nModel.setState(ConstDefine.STATE_ABLE);
           notificationService.insertObject(nModel);
           return model.getId();
       }else {
           return rel;
       }
    }
    /**
     * 新增施工现场阻工信息
     * @param userId 创建人id
     * @param model  施工现场阻工信息实体类
     */
    @Override
    public Integer addStopWorkInfo(String userId, StopWorkInfoModel model) {
        int rel = stopWorkMapper.addStopWorkInfo(userId,model);
        if(rel > 0){
            return model.getId();
        }else {
            return rel;
        }
    }
    /**
     * 修改施工现场阻工处理信息
     * @param editer 创建人id
     * @param model  施工现场阻工处理信息实体类
     */
    @Override
    public Integer updateStopWork(String editer, StopWorkModel model) {
        return stopWorkMapper.updateStopWork(editer,model);
    }

    /**
     * 修改施工现场阻工信息
     * @param editer 创建人id
     * @param model  施工现场阻工信息实体类
     */
    @Override
    public Integer updateStopWorkInfo(String editer, StopWorkInfoModel model, String url) {
        int result = stopWorkMapper.updateStopWorkInfo(editer,model);
        if(result > 0){
            if(null != model.getDisposeState() && model.getDisposeState().equals("2")) {
                //如果处理修改成功，添加通知
                NotificationModel nModel = new NotificationModel();
                nModel.setContent(url+"api/stopWork/id/"+model.getId());
                //查询施工过程中隐患详情
                StopWorkInfoModel cModel = stopWorkMapper.getStopWorkInfo(model.getId());
                if (cModel != null) {
                    nModel.setTitle(cModel.getName() + "施工现场阻工行动完成");
                }
                nModel.setType(1);//系统通知
                nModel.setGroupIds(cModel.getCreater().toString());
                nModel.setCreateTime(Utils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
                nModel.setCreater(model.getEditer());
                nModel.setState(ConstDefine.STATE_ABLE);
                notificationService.insertObject(nModel);
            }
            return model.getId();
        }else {
            return result;
        }
    }
        /*@Author:wei.peng
        *@Desicription:分页查询施工现场阻工数据
        * @param map 查询条件
        *@Date:Created in 15:28 2018/9/26
        *@Return:返回数据集合
        * @Modified By:
        */
    @Override
    public List<StopWorkInfoModel> getAllByPage(Map<String, Object> map, Page page) {
        List<StopWorkInfoModel> list=null;
        /**查询总数*/
        Integer count = stopWorkMapper.getAllByPageCount(map);
        if (count > 0) {
            page.setTotalCount(Long.valueOf(count));
            page.setTotalPage(Long.valueOf(count), page.getEveryPage());
            if (page.getCurrentPage() > 0 && page.getEveryPage() > 0) {
                /**查询分页条数*/
                list = stopWorkMapper.getAllByPage(map);
            }
        } else {
            page.setTotalCount(0L);
        }
        /*for (int i = 0; i < list.size(); i++) {
            List<StopWorkModel> sModel = new ArrayList<>();
            //查询施工现场阻工处理
            sModel = stopWorkMapper.getStopWorkById(list.get(i).getId());
            list.get(i).setList(sModel);
        }*/
        return list;
    }
    /**
     * 删除施工现场阻工信息
     * @param state 状态
     * @param id  施工现场阻工id
     */
    @Override
    public int delteStopWorkInfo(Integer[] id,Integer editer, Integer state) {
        return stopWorkMapper.delteStopWorkInfo(id,editer,state);
    }

    /**
     * @Author:Yangzepeng
     * @Desicription:根据id查询施工现场阻工详情
     * @param
     * @Date:2018/10/9 9:22
     * @Return:
     * @Modified By:
     */
    @Override
    public StopWorkInfoModel getStopWorkInfo(Integer id) {
        StopWorkInfoModel model = stopWorkMapper.getStopWorkInfo(id);
        if(model != null){
            List<StopWorkModel> cModel = stopWorkMapper.getStopWorkById(model.getId());
            if(cModel.size() != 0){
                for (int i = 0; i < cModel.size(); i++) {
                    StringBuilder sb = new StringBuilder();
                    //解析用户id
                    String[] result = cModel.get(i).getDisposePeople().split(",");
                    for (int j = 0; j < result.length; j++){
                        //根据id查询用户姓名
                        SysUserModel sys = sysUserMapper.findObject(Integer.parseInt(result[j]));
                        sb.append(sys.getFullName());
                        if(j < result.length -1){
                            sb.append(",");
                        }
                    }
                    cModel.get(i).setDisposePeople(sb.toString());
                }
            }
            model.setList(cModel);
            return model;
        }
        return null;
    }
}

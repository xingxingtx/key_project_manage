package com.aorise.service.constructionhidden.impl;


import com.aorise.mapper.constructionhidden.ConstructionHiddenMapper;
import com.aorise.mapper.system.SysUserMapper;
import com.aorise.model.constructionhidden.ConstructionHiddenInfoModel;
import com.aorise.model.constructionhidden.ConstructionHiddenModel;
import com.aorise.model.notification.NotificationModel;
import com.aorise.model.system.SysUserModel;
import com.aorise.service.constructionhidden.ConstructionHiddenService;
import com.aorise.service.notification.NotificationService;
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
 * @Desicription:施工过程中隐患
 * @Date:Created in 2018/9/27 13:48
 * @Modified By:
 */
@Service
@Transactional
public class ConstructionHiddenImpl implements ConstructionHiddenService {
    @Autowired
    private ConstructionHiddenMapper constructionHiddenMapper;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private SysUserMapper sysUserMapper;
    /**
     * 新增施工过程中隐患处理信息
     * @param userId 创建人id
     * @param model  处理信息实体类
     */
    @Override
    public Integer addConstructionHidden(String userId, ConstructionHiddenModel model,String url) {
        int rel = constructionHiddenMapper.addConstructionHidden(userId,model);
        if(rel > 0){
            //如果处理新增成功，添加通知
            NotificationModel nModel = new NotificationModel();
            nModel.setContent(url+"api/constructionHidden/id/"+model.getHiddenid());
            //查询施工过程中隐患详情
            ConstructionHiddenInfoModel cModel = constructionHiddenMapper.getConstructionHiddenInfo(model.getHiddenid());
            if(cModel != null){
                nModel.setTitle(cModel.getName() + "施工过程中隐患处理");
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
     * 新增施工过程中隐患信息
     * @param userId 创建人id
     * @param model  施工过程中隐患信息实体类
     */
    @Override
    public Integer addConstructionHiddenInfo(String userId, ConstructionHiddenInfoModel model) {
       int rel =  constructionHiddenMapper.addConstructionHiddenInfo(model,userId);
       if(rel > 0){
           return model.getId();
       }else {
           return rel;
       }
    }
    /**
     * 修改施工过程中隐患处理信息
     * @param editer 创建人id
     * @param model  施工过程中隐患信息实体类
     */
    @Override
    public Integer updateConstructionHidden(String editer, ConstructionHiddenModel model) {
        return constructionHiddenMapper.updateConstructionHidden(editer,model);
    }
        /*@Author:wei.peng
        *@Desicription:分页查询施工过程中隐患数据
        * @param map 查询条件
        *@Date:Created in 15:28 2018/9/26
        *@Return:返回数据集合
        * @Modified By:
        */
    @Override
    public List<ConstructionHiddenInfoModel> getAllByPage(Map<String, Object> map, Page page) {
        List<ConstructionHiddenInfoModel> list=null;
        /**查询总数*/
        Integer count = constructionHiddenMapper.getAllByPageCount(map);
        if (count > 0) {
            page.setTotalCount(Long.valueOf(count));
            page.setTotalPage(Long.valueOf(count), page.getEveryPage());
            if (page.getCurrentPage() > 0 && page.getEveryPage() > 0) {
                /**查询分页条数*/
                list = constructionHiddenMapper.getAllByPage(map);
            }
        } else {
            page.setTotalCount(0L);
        }
        /*for (int i = 0; i < list.size(); i++) {
            List<ConstructionHiddenModel> sModel = new ArrayList<>();
            //查询施工过程中隐患处理
            sModel = constructionHiddenMapper.getConstructionHiddenById(list.get(i).getId());
            list.get(i).setList(sModel);
        }*/
        return list;
    }
    /**
     * 删除施工过程中隐患信息
     * @param state 状态
     * @param id  施工过程中隐患id
     */
    @Override
    public int delteConstructionHiddenInfo(Integer[] id,Integer editer,Integer state) {
        return constructionHiddenMapper.delteConstructionHiddenInfo(id,editer,state);
    }

    /**
     * @Author:Yangzepeng
     * @Desicription:根据id查询详情
     * @param
     * @Date:2018/10/8 17:40
     * @Return:
     * @Modified By:
     */
    @Override
    public ConstructionHiddenInfoModel getConstructionHiddenInfo(Integer id) {
        ConstructionHiddenInfoModel model = constructionHiddenMapper.getConstructionHiddenInfo(id);
        if(model != null){
            List<ConstructionHiddenModel> cModel = constructionHiddenMapper.getConstructionHiddenById(model.getId());
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

    /*@Author:wei.peng
     *@Desicription:修改施工过程中隐患数据
     * @param map 查询条件
     *@Date:Created in 15:28 2018/9/26
     *@Return:返回数据集合
     * @Modified By:
     */
    @Override
    public int updateConstructionHiddenInfo(ConstructionHiddenInfoModel model,String url) {
        int result = constructionHiddenMapper.updateConstructionHiddenInfo(model);
        if(result > 0){
            if(null != model.getDisposeState() && model.getDisposeState().equals("2")) {
                //如果处理修改成功，添加通知
                NotificationModel nModel = new NotificationModel();
                nModel.setContent(url+"api/constructionHidden/id/"+model.getId());
                //查询施工过程中隐患详情
                ConstructionHiddenInfoModel cModel = constructionHiddenMapper.getConstructionHiddenInfo(model.getId());
                if (cModel != null) {
                    nModel.setTitle(cModel.getName() + "施工过程中隐患处理完成");
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
}

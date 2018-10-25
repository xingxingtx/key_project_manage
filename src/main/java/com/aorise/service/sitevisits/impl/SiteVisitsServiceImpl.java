package com.aorise.service.sitevisits.impl;

import com.aorise.mapper.sitevisits.SiteVisitsMapper;
import com.aorise.mapper.system.SysUserMapper;
import com.aorise.model.notification.NotificationModel;
import com.aorise.model.sitevisits.SiteVisitsInfoModel;
import com.aorise.model.sitevisits.SiteVisitsModel;
import com.aorise.model.system.SysUserModel;
import com.aorise.service.notification.NotificationService;
import com.aorise.service.sitevisits.SiteVisitsService;
import com.aorise.utils.Utils;
import com.aorise.utils.define.ConstDefine;
import com.aorise.utils.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author:wei.peng
 * @Desicription:开工前摸排
 * @Date:Created in 2018/9/25 13:48
 * @Modified By:
 */
@Service
@Transactional
public class SiteVisitsServiceImpl implements SiteVisitsService {
    @Autowired
    private SiteVisitsMapper siteVisitsMapper;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private SysUserMapper sysUserMapper;
    /**
     * 新增开工前摸排处理信息
     * @param userId 创建人id
     * @param model  开工前摸排处理信息实体类
     */
    @Override
    public Integer addSiteVisits(String userId, SiteVisitsModel model,String url) {
        int rel = siteVisitsMapper.addSiteVisits(userId,model);
        if(rel > 0){

            //如果处理新增成功，添加通知
            NotificationModel nModel = new NotificationModel();
            nModel.setContent(url+"api/siteVisitsInfo/id/"+model.getVisitsId());
            //查询施工过程中隐患详情
             SiteVisitsInfoModel cModel = siteVisitsMapper.getSiteVisitsInfo(model.getVisitsId());
            if(cModel != null){
                nModel.setTitle(cModel.getName() + "开工前摸排处理");
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
     * 新增开工前摸排信息
     * @param userId 创建人id
     * @param model  开工前摸排信息实体类
     */
    @Override
    public Integer addSiteVisitsInfo(String userId, SiteVisitsInfoModel model) {
        int rel = siteVisitsMapper.addSiteVisitsInfo(userId,model);
        if(rel > 0){
            return model.getId();
        }else {
            return rel;
        }
    }
    /**
     * 修改开工前摸排处理信息
     * @param editer 创建人id
     * @param model  开工前摸排信息实体类
     */
    @Override
    public Integer updateSiteVisits(String editer, SiteVisitsModel model) {
        return siteVisitsMapper.updateSiteVisits(editer,model);
    }
        /*@Author:wei.peng
        *@Desicription:分页查询开工前摸排数据
        * @param map 查询条件
        *@Date:Created in 15:28 2018/9/26
        *@Return:返回数据集合
        * @Modified By:
        */
    @Override
    public List<SiteVisitsInfoModel> getAllByPage(Map<String, Object> map, Page page) {
        List<SiteVisitsInfoModel> list=null;
        /**查询总数*/
        Integer count = siteVisitsMapper.getAllByPageCount(map);
        if (count > 0) {
            page.setTotalCount(Long.valueOf(count));
            page.setTotalPage(Long.valueOf(count), page.getEveryPage());
            if (page.getCurrentPage() > 0 && page.getEveryPage() > 0) {
                /**查询分页条数*/
                list = siteVisitsMapper.getAllByPage(map);
            }
        } else {
            page.setTotalCount(0L);
        }
        /*for (int i = 0; i < list.size(); i++) {
            List<SiteVisitsModel> sModel = new ArrayList<>();
            //查询开工前摸排处理
            sModel = siteVisitsMapper.getSiteVisitsById(list.get(i).getId());
            list.get(i).setList(sModel);
        }*/
        return list;
    }
    /**
     * 删除开工前摸排信息
     * @param state 状态
     * @param id  开工前摸排id
     * @param editer  修改人id
     */
    @Override
    public int delteSiteVisitsInfo(Integer[] id,Integer editer,Integer state) {
        return siteVisitsMapper.delteSiteVisitsInfo(id,editer,state);
    }

    /**
     * @Author:Yangzepeng
     * @Desicription:根据id查询详情
     * @param
     * @Date:2018/10/8 16:27
     * @Return:
     * @Modified By:
     */
    @Override
    public SiteVisitsInfoModel getSiteVisitsInfo(Integer id) {
        SiteVisitsInfoModel model = siteVisitsMapper.getSiteVisitsInfo(id);
        if(model != null){
            List<SiteVisitsModel> cModel = siteVisitsMapper.getSiteVisitsById(model.getId());
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
            model.setList(siteVisitsMapper.getSiteVisitsById(model.getId()));
            return model;
        }
        return null;
    }

    /**
     * 修改开工前摸排详情数据
     * @param model
     * @return
     */
    @Override
    public int updateSiteVisitsInfo(SiteVisitsInfoModel model, String url) {
        int result = siteVisitsMapper.updateSiteVisitsInfo(model);
        if(result > 0 ){
            if(null != model.getDisposeState() && model.getDisposeState().equals("2")) {
                //如果处理修改成功，添加通知
                NotificationModel nModel = new NotificationModel();
                nModel.setContent(url+"api/siteVisitsInfo/id/"+model.getId());
                //查询施工过程中隐患详情
                SiteVisitsInfoModel cModel = siteVisitsMapper.getSiteVisitsInfo(model.getId());
                if (cModel != null) {
                    nModel.setTitle(cModel.getName() + "开工前摸排行动完成");
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

package com.aorise.service.petitioningdis.impl;

import com.aorise.mapper.petitioningdis.PetitioningDisMapper;
import com.aorise.mapper.system.SysUserMapper;
import com.aorise.model.notification.NotificationModel;
import com.aorise.model.petitioningdis.PetitioningDisInfoModel;
import com.aorise.model.petitioningdis.PetitioningDisModel;
import com.aorise.model.system.SysUserModel;
import com.aorise.service.notification.NotificationService;
import com.aorise.service.petitioningdis.PetitioningDisService;
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
 * Created by Administrator on 2018/9/27.
 */
@Service
@Transactional
public class PetitioningDisServiceImpl implements PetitioningDisService {
    @Autowired
    private PetitioningDisMapper petitioningDisMapper;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private SysUserMapper sysUserMapper;
    /**
     * 新增群众上访处理信息
     * @param userId 创建人id
     * @param model  群众上访处理信息实体类
     */
    @Override
    public Integer addPetitioningDis(String userId, PetitioningDisModel model, String url) {
        int rel = petitioningDisMapper.addPetitioningDis(userId,model);
        if(rel > 0){
            //如果处理新增成功，添加通知
            NotificationModel nModel = new NotificationModel();
            nModel.setContent(url+"api/petitioning/id/"+model.getPetitioningid());
            //查询施工过程中隐患详情
            PetitioningDisInfoModel cModel = petitioningDisMapper.getPetitioningDisInfo(model.getPetitioningid());
            if(cModel != null){
                nModel.setTitle(cModel.getName() + "群众上访隐患处理");
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
     * 新增群众上访信息
     * @param userId 创建人id
     * @param model  群众上访信息实体类
     */
    @Override
    public Integer addPetitioningDisInfo(String userId, PetitioningDisInfoModel model) {
        int rel = petitioningDisMapper.addPetitioningDisInfo(userId,model);
        if(rel > 0){
            return model.getId();
        }else {
            return rel;
        }
    }
    /**
     * 修改群众上访处理信息
     * @param editer 创建人id
     * @param model  群众上访信息实体类
     */
    @Override
    public Integer updatePetitioningDis(String editer, PetitioningDisModel model) {
        return petitioningDisMapper.updatePetitioningDis(editer,model);
    }
    /*@Author:wei.peng
    *@Desicription:分页查询群众上访数据
    * @param map 查询条件
    *@Date:Created in 15:28 2018/9/26
    *@Return:返回数据集合
    * @Modified By:
    */
    @Override
    public List<PetitioningDisInfoModel> getAllByPage(Map<String, Object> map, Page page) {
        List<PetitioningDisInfoModel> list=null;
        /**查询总数*/
        Integer count = petitioningDisMapper.getAllByPageCount(map);
        if (count > 0) {
            page.setTotalCount(Long.valueOf(count));
            page.setTotalPage(Long.valueOf(count), page.getEveryPage());
            if (page.getCurrentPage() > 0 && page.getEveryPage() > 0) {
                /**查询分页条数*/
                list = petitioningDisMapper.getAllByPage(map);
            }
        } else {
            page.setTotalCount(0L);
        }
       /* for (int i = 0; i < list.size(); i++) {
            List<PetitioningDisModel> sModel = new ArrayList<>();
            //查询群众上访处理
            sModel = petitioningDisMapper.getPetitioningDisById(list.get(i).getId());
            list.get(i).setList(sModel);
        }*/
        return list;
    }
    /**
     * 删除群众上访信息
     * @param state 状态
     * @param id  群众上访id
     */
    @Override
    public int deltePetitioningDisInfo(Integer[] id,Integer editer,Integer state) {
        return petitioningDisMapper.deltePetitioningDisInfo(id,editer,state);
    }

    /**
     * @Author:Yangzepeng
     * @Desicription:根据id查询群众上访详情
     * @param
     * @Date:2018/10/8 18:18
     * @Return:
     * @Modified By:
     */
    @Override
    public PetitioningDisInfoModel getPetitioningDisInfo(Integer id) {
        PetitioningDisInfoModel model = petitioningDisMapper.getPetitioningDisInfo(id);
        if(model != null){
            List<PetitioningDisModel> cModel = petitioningDisMapper.getPetitioningDisById(model.getId());
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

    @Override
    public int updatePetitioningDisInfo(PetitioningDisInfoModel model, String url) {
        int result = petitioningDisMapper.updatePetitioningDisInfo(model);
        if(result > 0){
            if(null != model.getDisposeState() && model.getDisposeState().equals("2")) {
                //如果处理修改成功，添加通知
                NotificationModel nModel = new NotificationModel();
                nModel.setContent(url+"api/petitioning/id/"+model.getId());
                //查询施工过程中隐患详情
                PetitioningDisInfoModel cModel = petitioningDisMapper.getPetitioningDisInfo(model.getId());
                if (cModel != null) {
                    nModel.setTitle(cModel.getName() + "群众上访行动完成");
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

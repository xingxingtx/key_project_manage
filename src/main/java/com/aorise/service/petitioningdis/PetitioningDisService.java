package com.aorise.service.petitioningdis;

import com.aorise.model.petitioningdis.PetitioningDisInfoModel;
import com.aorise.model.petitioningdis.PetitioningDisModel;
import com.aorise.model.sitevisits.SiteVisitsInfoModel;
import com.aorise.model.sitevisits.SiteVisitsModel;
import com.aorise.utils.page.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author:wei.peng
 * @Desicription:
 * @Date:Created in 2018/9/27.
 * @Modified By:
 */
public interface PetitioningDisService {
    /**
     * 新增群众上访信息
     * @param userId 创建人id
     * @param model  群众上访信息实体类
     */
    public Integer addPetitioningDisInfo(String userId, PetitioningDisInfoModel model);
    /**
     * 新增群众上访处理信息
     * @param userId 创建人id
     * @param model  群众上访处理信息实体类
     */
    public Integer addPetitioningDis(String userId, PetitioningDisModel model, String url);

    /**
     * 修改群众上访处理信息
     * @param editer 修改人id
     * @param model  群众上访处理信息实体类
     */
    public Integer updatePetitioningDis(String editer, PetitioningDisModel model);

    /*@Author:wei.peng
     *@Desicription:分页查询群众上访数据
     * @param map 查询条件
     *@Date:Created in 15:28 2018/9/26
     *@Return:返回数据集合
     * @Modified By:
     */
    public List<PetitioningDisInfoModel> getAllByPage(Map<String, Object> map, Page page);
    /**
     * 删除群众上访信息
     *
     *
     * @param state 状态
     * @param id  群众上访id
     */
    int deltePetitioningDisInfo(Integer[] id,Integer editer,Integer state);

    /**
     * @Author:Yangzepeng
     * @Desicription:根据id查询群众上访详情
     * @param
     * @Date:2018/10/8 18:17
     * @Return:
     * @Modified By:
     */
    PetitioningDisInfoModel getPetitioningDisInfo(Integer id);

    /**
     * 修改群众上访详情数据
     * @param model
     * @return
     */
    int updatePetitioningDisInfo(PetitioningDisInfoModel model, String url);
}

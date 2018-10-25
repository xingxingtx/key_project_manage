package com.aorise.service.sitevisits;

import com.aorise.model.sitevisits.SiteVisitsInfoModel;
import com.aorise.model.sitevisits.SiteVisitsModel;
import com.aorise.utils.page.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author:wei.peng
 * @Desicription:
 * @Date:Created in 2018/9/25.
 * @Modified By:
 */
public interface SiteVisitsService {
    /**
     * 新增开工前摸排信息
     * @param userId 创建人id
     * @param model  开工前摸排信息实体类
     */
    public Integer addSiteVisitsInfo(String userId, SiteVisitsInfoModel model);
    /**
     * 新增开工前摸排处理信息
     * @param userId 创建人id
     * @param model  开工前摸排处理信息实体类
     */
    public Integer addSiteVisits(String userId, SiteVisitsModel model, String url);

    /**
     * 修改开工前摸排处理信息
     * @param editer 修改人id
     * @param model  开工前摸排处理信息实体类
     */
    public Integer updateSiteVisits(String editer, SiteVisitsModel model);

    /*@Author:wei.peng
     *@Desicription:分页查询开工前摸排数据
     * @param map 查询条件
     *@Date:Created in 15:28 2018/9/26
     *@Return:返回数据集合
     * @Modified By:
     */
    public List<SiteVisitsInfoModel> getAllByPage(Map<String, Object> map, Page page);
    /**
     * 删除开工前摸排信息
     * @param state 状态
     * @param id  开工前摸排id
     * @param  editer 修改人id
     */
    int delteSiteVisitsInfo(Integer[] id,Integer editer,Integer state);

    /**
     * @Author:Yangzepeng
     * @Desicription:根据id查询详情
     * @param
     * @Date:2018/10/8 16:27
     * @Return:
     * @Modified By:
     */
    SiteVisitsInfoModel getSiteVisitsInfo(Integer id);
    /**
     * 修改开工前摸排详情数据
     * @param model
     * @return
     */
    int updateSiteVisitsInfo(SiteVisitsInfoModel model, String url);
}

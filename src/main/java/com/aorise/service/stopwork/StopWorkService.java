package com.aorise.service.stopwork;


import com.aorise.model.stopwork.StopWorkInfoModel;
import com.aorise.model.stopwork.StopWorkModel;
import com.aorise.utils.page.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author:wei.peng
 * @Desicription:
 * @Date:Created in 2018/9/27.
 * @Modified By:
 */
public interface StopWorkService {
    /**
     * 新增施工现场阻工信息
     * @param userId 创建人id
     * @param model  施工现场阻工信息实体类
     */
    public Integer addStopWorkInfo(String userId, StopWorkInfoModel model);
    /**
     * 新增施工现场阻工处理信息
     * @param userId 创建人id
     * @param model  施工现场阻工处理信息实体类
     */
    public Integer addStopWork(String userId, StopWorkModel model, String url);

    /**
     * 修改施工现场阻工处理信息
     * @param editer 修改人id
     * @param model  施工现场阻工处理信息实体类
     */
    public Integer updateStopWork(String editer, StopWorkModel model);

    /**
     * 修改施工现场阻工信息
     * @param editer 修改人id
     * @param model  施工现场阻工信息实体类
     */
    public Integer updateStopWorkInfo(String editer, StopWorkInfoModel model, String url);
    /*@Author:wei.peng
     *@Desicription:分页查询施工现场阻工数据
     * @param map 查询条件
     *@Date:Created in 15:28 2018/9/26
     *@Return:返回数据集合
     * @Modified By:
     */
    public List<StopWorkInfoModel> getAllByPage(Map<String, Object> map, Page page);
    /**
     * 删除施工现场阻工信息
     * @param state 状态
     * @param id  施工现场阻工id
     */
    int delteStopWorkInfo(Integer[] id,Integer editer,Integer state);

    /**
     * @Author:Yangzepeng
     * @Desicription:根据id查询施工现场阻工详情
     * @param
     * @Date:2018/10/9 9:21
     * @Return:
     * @Modified By:
     */
    StopWorkInfoModel getStopWorkInfo(Integer id);
}

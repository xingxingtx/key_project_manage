package com.aorise.service.constructionhidden;


import com.aorise.model.constructionhidden.ConstructionHiddenInfoModel;
import com.aorise.model.constructionhidden.ConstructionHiddenModel;
import com.aorise.utils.define.ConstDefine;
import com.aorise.utils.page.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author:wei.peng
 * @Desicription:
 * @Date:Created in 2018/9/25.
 * @Modified By:
 */
public interface ConstructionHiddenService {
    /**
     * 新增施工过程中隐患信息
     * @param userId 创建人id
     * @param model  施工过程中隐患信息实体类
     */
    public Integer addConstructionHiddenInfo(String userId, ConstructionHiddenInfoModel model);
    /**
     * 新增施工过程中隐患处理信息
     * @param userId 创建人id
     * @param model  施工过程中隐患处理信息实体类
     */
    public Integer addConstructionHidden(String userId, ConstructionHiddenModel model,String url);

    /**
     * 修改施工过程中隐患处理信息
     * @param editer 修改人id
     * @param model  施工过程中隐患处理信息实体类
     */
    public Integer updateConstructionHidden(String editer, ConstructionHiddenModel model);

    /*@Author:wei.peng
     *@Desicription:分页查询施工过程中隐患数据
     * @param map 查询条件
     *@Date:Created in 15:28 2018/9/26
     *@Return:返回数据集合
     * @Modified By:
     */
    public List<ConstructionHiddenInfoModel> getAllByPage(Map<String, Object> map, Page page);
    /**
     * 删除施工过程中隐患信息
     * @param state 状态
     * @param id  施工过程中隐患id
     * @param editer 修改人id
     */
    int delteConstructionHiddenInfo(Integer[] id,Integer editer, Integer state);

    /**
     * @Author:Yangzepeng
     * @Desicription:根据id查询详情
     * @param
     * @Date:2018/10/8 17:39
     * @Return:
     * @Modified By:
     */
    ConstructionHiddenInfoModel getConstructionHiddenInfo(Integer id);

    /**
     * 修改施工过程中隐患数据
     * @param model
     * @return
     */
    int updateConstructionHiddenInfo(ConstructionHiddenInfoModel model,String url);
}

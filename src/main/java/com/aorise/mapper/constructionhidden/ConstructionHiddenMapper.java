package com.aorise.mapper.constructionhidden;

import com.aorise.model.constructionhidden.ConstructionHiddenInfoModel;
import com.aorise.model.constructionhidden.ConstructionHiddenModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author:wei.peng
 * @Desicription:施工过程中隐患数据库持久层
 * @Date:Created in 2018-09-25
 * @Modified By:
 */
@Mapper
@Component
public interface ConstructionHiddenMapper {
    /**
     * 新增施工过程中隐患信息
     * @param userId 创建人id
     * @param model  施工过程中隐患信息实体类
     */
    public Integer addConstructionHiddenInfo(@Param("model") ConstructionHiddenInfoModel model,@Param("userId") String userId) throws DataAccessException;
    /**
     * 新增施工过程中隐患处理信息
     * @param userId 创建人id
     * @param model  施工过程中隐患处理信息实体类
     */
    public Integer addConstructionHidden(@Param("userId") String userId, @Param("model") ConstructionHiddenModel model) throws DataAccessException;
    /**
     * 修改施工过程中隐患处理信息
     * @param editer 修改人id
     * @param model  施工过程中隐患处理信息实体类
     */
    public Integer updateConstructionHidden(@Param("editer") String editer, @Param("model") ConstructionHiddenModel model);

    /**
     *@Author:weipeng
     *@Desicription:分页查询施工过程中隐患数据
     * @param map 查询条件
     *@Date:Created in 15:28 2018/9/27
     *@Return:返回数据集合
     * @Modified By:
     */
    List<ConstructionHiddenInfoModel> getAllByPage(Map<String, Object> map) throws DataAccessException;
    /**
     *@Author:weipeng
     *@Desicription:分页查询施工过程中隐患数据总数
     * @param map 查询条件
     *@Date:Created in 15:28 2018/9/27
     *@Return:返回数据总数
     * @Modified By:
     */
    int getAllByPageCount(Map<String, Object> map) throws DataAccessException;

    /**
     * 删除施工过程中隐患信息
     * @param state 状态
     * @param id  施工过程中隐患id
     */
    int delteConstructionHiddenInfo(@Param("id") Integer[] id,@Param("editer") Integer editer, @Param("state") Integer state) throws DataAccessException;

    /**
     * 根据施工过程中隐患id查询施工过程中隐患处理信息
     * @param hiddenid  施工过程中隐患id
     */
    List<ConstructionHiddenModel> getConstructionHiddenById(@Param("hiddenid") Integer hiddenid) throws DataAccessException;

    /**
     * @Author:Yangzepeng
     * @Desicription:根据id查询详情
     * @param
     * @Date:2018/10/8 17:43
     * @Return:
     * @Modified By:
     */
    ConstructionHiddenInfoModel getConstructionHiddenInfo(Integer id);

    /**
     * 修改施工过程中隐患数据
     * @param model
     * @return
     */
    int updateConstructionHiddenInfo(@Param("model") ConstructionHiddenInfoModel model);
}

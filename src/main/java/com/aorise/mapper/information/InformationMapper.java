package com.aorise.mapper.information;


import com.aorise.model.information.InformationModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author:Shenzhiwei
 * @Desicription:资讯管理数据持久化层
 * @Date:Created in 2018-09-05 14:59
 * @Modified By:
 */
@Mapper
@Component
public interface InformationMapper {

    /**
     *@Author:Shenzhiwei
     *@Desicription:添加资讯管理数据
      * @param model 资讯管理实体对象
     *@Date:Created in 15:27 2018/9/5
     *@Return:返回受影响的行数
     * @Modified By:
     */
    int insertObject(InformationModel model) throws DataAccessException;
    /**
     *@Author:Shenzhiwei
     *@Desicription:修改资讯管理数据
     * @param model 资讯管理实体对象
     *@Date:Created in 15:27 2018/9/5
     *@Return:返回受影响的行数
     * @Modified By:
     */
    int updateObject(InformationModel model) throws DataAccessException;
    /**
     *@Author:Shenzhiwei
     *@Desicription:根据id查询资讯管理实体
      * @param id 主键id
     *@Date:Created in 15:27 2018/9/5
     *@Return:返回实体对象
     * @Modified By:
     */
    InformationModel getDetail(int id) throws DataAccessException;

    /**
     *@Author:Shenzhiwei
     *@Desicription:分页查询资讯管理数据
      * @param map 查询条件
     *@Date:Created in 15:28 2018/9/5
     *@Return:返回数据集合
     * @Modified By:
     */
    List<InformationModel> getAllByPage(Map<String, Object> map) throws DataAccessException;
    /**
     *@Author:Shenzhiwei
     *@Desicription:分页查询资讯管理数据总数
     * @param map 查询条件
     *@Date:Created in 15:28 2018/9/5
     *@Return:返回数据总数
     * @Modified By:
     */
    int getAllByPageCount(Map<String, Object> map) throws DataAccessException;
    /**
     *@Author:Shenzhiwei
     *@Desicription:修改资讯管理数据
     * @param model 资讯管理实体对象
     *@Date:Created in 15:27 2018/9/5
     *@Return:返回受影响的行数
     * @Modified By:
     */
    int updateStatus(InformationModel model) throws DataAccessException;
    /**
     *@Author:Shenzhiwei
     *@Desicription:删除资讯管理数据
     * @param id 主键id
     *@Date:Created in 15:27 2018/9/5
     *@Return:返回受影响的行数
     * @Modified By:
     */
    int deleteObject(int id) throws DataAccessException;
}

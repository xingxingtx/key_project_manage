package com.aorise.mapper.common;

import com.aorise.model.common.AreaInfoModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author:Shenzhiwei
 * @Desicription:地区数据库映射类
 * @Date:Created in 2018-09-27 09:37
 * @Modified By:
 */
@Mapper
@Component
public interface AreaInfoMapper {

    /**
     *@Author:Shenzhiwei
     *@Desicription:根据父级查询子节点
      * @param code 地区代码
     *@Date:Created in 9:54 2018/9/27
     *@Return:返回数据集合
     * @Modified By:
     */
    List<AreaInfoModel> getListByParent(@Param ( "code" ) String code) throws DataAccessException;
    /**
     *@Author:Shenzhiwei
     *@Desicription:根据id查询父级
     * @param code 地区代码
     *@Date:Created in 9:55 2018/9/27
     *@Return:返回实体对象
     * @Modified By:
     */
    AreaInfoModel getByChild(@Param ( "code" ) String code) throws DataAccessException;
    /**
     *@Author:Shenzhiwei
     *@Desicription:根据id查询当前地区
      * @param code 地区代码
     *@Date:Created in 9:55 2018/9/27
     *@Return:返回实体对象
     * @Modified By:
     */
    AreaInfoModel getById(@Param ( "code" ) String code) throws DataAccessException;
    /**
     *@Author:ZGP
     *@Desicription:查询所有地区
     *@Date:Created in 9:55 2018/9/27
     *@Return:返回实体对象
     * @Modified By:
     */
    List<AreaInfoModel> getAll() throws DataAccessException;
}

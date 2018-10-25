package com.aorise.mapper.system;

import com.aorise.model.system.SysRoleModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author:ZGP
 * @Desicription:
 * @Date:Created in 2018/9/20 9:11
 * @Modified By:
 */
@Mapper
@Component(value = "SysRoleMapper")
public interface SysRoleMapper {

    /**
     *@Author:ZGP
     *@Desicription:添加角色
     * @param model 实体对象
     *@Date:Created in 10:04 2018/9/17
     *@Return:返回影响行数
     * @Modified By:zgp
     */
    int insertObject(SysRoleModel model)throws DataAccessException;

    /**
     *@Author:ZGP
     *@Desicription:删除角色
     * @param model 实体对象
     *@Date:Created in 10:04 2018/9/17
     *@Return:返回受影响的行数
     * @Modified By:zgp
     */
    int deleteObject(SysRoleModel model)throws DataAccessException;
    /**
     *@Author:ZGP
     *@Desicription:修改角色
     * @param model 实体对象
     *@Date:Created in 10:04 2018/9/17
     *@Return:返回受影响的行数
     * @Modified By:zgp
     */
    int editeObject(SysRoleModel model)throws DataAccessException;
    /**
     *@Author:ZGP
     *@Desicription:查询对象
     * @param model 实体对象
     *@Date:Created in 10:04 2018/9/17
     *@Return:对象
     * @Modified By:zgp
     */
    SysRoleModel findObject(SysRoleModel model)throws DataAccessException;

    /**
     *@Author:ZGP
     *@Desicription:查询列表
     *@Date:Created in 10:04 2018/9/17
     *@Return:list
     * @Modified By:zgp
     */
    List<SysRoleModel> findRoleList()throws DataAccessException;

    /**
     *@Author:ZGP
     *@Desicription:统计角色人数
     *@Date:Created in 10:04 2018/9/17
     *@Return:list
     * @Modified By:zgp
     */
    List<SysRoleModel> findRoleCount()throws DataAccessException;
}

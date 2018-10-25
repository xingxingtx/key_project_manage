package com.aorise.mapper.system;

import com.aorise.model.system.SysPermissionModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author:ZGP
 * @Desicription:
 * @Date:Created in 2018/9/20 9:12
 * @Modified By:
 */
@Mapper
@Component(value = "SysPermissionMapper")
public interface SysPermissionMapper {


    /**
     *@Author:ZGP
     *@Desicription:添加权限
     * @param model 实体对象
     *@Date:Created in 10:04 2018/9/17
     *@Return:返回影响行数
     * @Modified By:zgp
     */
    int insertObject(SysPermissionModel model)throws DataAccessException;

    /**
     *@Author:ZGP
     *@Desicription:删除权限
     * @param model 实体对象
     *@Date:Created in 10:04 2018/9/17
     *@Return:返回受影响的行数
     * @Modified By:zgp
     */
    int deleteObject(SysPermissionModel model)throws DataAccessException;
    /**
     *@Author:ZGP
     *@Desicription:修改权限
     * @param model 实体对象
     *@Date:Created in 10:04 2018/9/17
     *@Return:返回受影响的行数
     * @Modified By:zgp
     */
    int editeObject(SysPermissionModel model)throws DataAccessException;
    /**
     *@Author:ZGP
     *@Desicription:查看权限详情
     * @param model 实体对象
     *@Date:Created in 10:04 2018/9/17
     *@Return:返回受影响的行数
     * @Modified By:zgp
     */
    SysPermissionModel findObject(SysPermissionModel model)throws DataAccessException;
    /**
     *@Author:ZGP
     *@Desicription:查看所有权限列表
     *@Date:Created in 10:04 2018/9/17
     *@Return:返回受影响的行数
     * @Modified By:zgp
     */
    List<SysPermissionModel> findPerList()throws DataAccessException;
    /**
     *@Author:ZGP
     *@Desicription:查看所有权限列表
     *@Date:Created in 10:04 2018/9/17
     *@Return:返回受影响的行数
     * @Modified By:zgp
     */
    List<SysPermissionModel> findPerListByRoleId(@Param("roleId")int roleId)throws DataAccessException;
}

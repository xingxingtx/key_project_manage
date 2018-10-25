package com.aorise.mapper.system;

import com.aorise.model.system.SysRolePermissionModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author:Shenzhiwei
 * @Desicription:
 * @Date:Created in 2018-09-17 14:12
 * @Modified By:
 */
@Mapper
@Component(value = "SysRolePermissionMapper")
public interface SysRolePermissionMapper {
    /**
     *@Author:Shenzhiwei
     *@Desicription:添加角色权限关系数据
     * @param model 实体对象
     *@Date:Created in 10:04 2018/9/17
     *@Return:返回受影响的行数
     * @Modified By:zgp
     */
    int insertObject(SysRolePermissionModel model)throws DataAccessException;

    /**
     *@Author:Shenzhiwei
     *@Desicription:根据角色id删除数据
     * @param roleId 删除条件参数
     *@Date:Created in 10:04 2018/9/17
     *@Return:返回受影响的行数
     * @Modified By:zgp
     */
    int deleteByRoleId(@Param("roleId")int roleId)throws DataAccessException;
}

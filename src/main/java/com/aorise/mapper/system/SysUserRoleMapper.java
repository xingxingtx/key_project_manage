package com.aorise.mapper.system;
import com.aorise.model.system.SysUserRoleModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author:Shenzhiwei
 * @Desicription:
 * @Date:Created in 2018-09-17 14:08
 * @Modified By:
 */
@Mapper
@Component(value = "SysUserRoleMapper")
public interface SysUserRoleMapper {
    /**
     *@Author:Shenzhiwei
     *@Desicription:添加用户角色关系数据
     * @param model 实体对象
     *@Date:Created in 10:04 2018/9/17
     *@Return:返回受影响的行数
     * @Modified By:ZGP
     */
    int insertObject(SysUserRoleModel model)throws DataAccessException;

    /**
     *@Author:Shenzhiwei
     *@Desicription:根据用户id删除数据
     * @param userId 删除条件参数
     *@Date:Created in 10:04 2018/9/17
     *@Return:返回受影响的行数
     * @Modified By:
     */
    int deleteByUserId(@Param("userId") int userId)throws DataAccessException;
}

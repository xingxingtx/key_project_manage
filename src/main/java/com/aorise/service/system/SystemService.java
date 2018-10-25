package com.aorise.service.system;


import com.aorise.model.system.SysPermissionModel;
import com.aorise.model.system.SysRoleModel;
import com.aorise.model.system.SysUserModel;
import com.aorise.utils.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author:ZGP
 * @Desicription:系统管理相关
 * @Date:Created in 2018/9/21 13:47
 * @Modified By:
 */
public interface SystemService {

    /**
     * @Author:ZGP
     * @Desicription:  根据用户名查询用户
     * @param username 用户名
     * @Date:Created in 2018/9/20 9:27
     * @Return:userModel
     * @Modified By:
     */
    SysUserModel findByUsername(String username);
    /**
     * @Author:ZGP
     * @Desicription:  根据用户id查询用户
     * @param id 用户id
     * @Date:Created in 2018/9/20 9:27
     * @Return:userModel
     * @Modified By:
     */
    SysUserModel findObject(int id);
    /**
     * @Author:ZGP
     * @Desicription:  根据id集合删除
     * @param ids id
     * @Date:Created in 2018/9/20 9:27
     * @Return:int 影响行数
     * @Modified By:
     */
    int deleteObject(String[] ids);
    /**
     * @Author:ZGP
     * @Desicription:  修改用户
     * @param model 用户对象
     * @Date:Created in 2018/9/20 9:27
     * @Return:userModel
     * @Modified By:
     */
    int editeObject(SysUserModel model);
    /**
     * @Author:ZGP
     * @Desicription:  插入用户
     * @param model 用户对象
     * @Date:Created in 2018/9/20 9:27
     * @Return:userModel
     * @Modified By:
     */
    int insertObject(SysUserModel model);
    /**
     * @Author:ZGP
     * @Desicription:  分页查询用户列表
     * @param model 用户model
     * @Date:Created in 2018/9/20 9:27
     * @Return:userModel
     * @Modified By:
     */
    List<SysUserModel> findUserList(SysUserModel model,Page page);
    /**
     * @Author:ZGP
     * @Desicription:  查询所有的角色
     * @param
     * @Date:Created in 2018/9/20 9:27
     * @Return:SysRoleModels
     * @Modified By:
     */
    List<SysRoleModel> findRoleList();
    /**
     * @Author:ZGP
     * @Desicription:  插入权限
     * @param
     * @Date:Created in 2018/9/25 9:27
     * @Return:SysRoleModels
     * @Modified By:
     */
    public int insertPermission(SysPermissionModel model);
    /**
     * @Author:ZGP
     * @Desicription:  插入角色权限关系
     * @param
     * @Date:Created in 2018/9/25 9:27
     * @Return:SysRoleModels
     * @Modified By:
     */
    public int insertRolePermission(int roleId,String[] permissionIds);

    /**
     * @Author:ZGP
     * @Desicription:  根据角色查询用户
     * @param roleId 用户名
     * @Date:Created in 2018/9/20 9:27
     * @Return:userModel
     * @Modified By:
     */
    List<SysUserModel>  findByRoleId(int roleId,String adder);
    /**
     *@Author:ZGP
     *@Desicription:查看所有权限列表
     *@Date:Created in 10:04 2018/9/17
     *@Return:返回受影响的行数
     * @Modified By:zgp
     */
    List<SysPermissionModel> findPerList();
    /**
     *@Author:ZGP
     *@Desicription:根据角色id查询权限列表
     *@Date:Created in 10:04 2018/9/17
     *@Return:返回受影响的行数
     * @Modified By:zgp
     */
    List<SysPermissionModel> findPerListByRoleId(int roleId);


    /**
     *@Author:ZGP
     *@Desicription:统计角色人数
     *@Date:Created in 10:04 2018/9/17
     *@Return:list
     * @Modified By:zgp
     */
    List<SysRoleModel> findRoleCount();


    /**
     * @Author:ZGP
     * @Desicription:  根据姓名查询用户列表
     * @param fullName 姓名
     * @Date:Created in 2018/9/20 9:27
     * @Return:userModel
     * @Modified By:
     */
    List<SysUserModel> findUserListByFullName(String fullName);


}

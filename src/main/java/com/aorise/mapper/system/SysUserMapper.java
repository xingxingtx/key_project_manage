package com.aorise.mapper.system;

import com.aorise.model.system.SysUserModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author:ZGP
 * @Desicription: 用户相关
 * @Date:Created in 2018/9/20 9:10
 * @Modified By:
 */

@Mapper
@Component(value = "SysUserMapper")
public interface SysUserMapper {

    /**
     * @Author:ZGP
     * @Desicription:  根据用户名查询用户
     * @param username 用户名
     * @Date:Created in 2018/9/20 9:27
     * @Return:userModel
     * @Modified By:
     */
    SysUserModel findByUsername(@Param("username")String username)throws DataAccessException;

    /**
     * @Author:ZGP
     * @Desicription:  根据id查询用户
     * @param id 用户id
     * @Date:Created in 2018/9/20 9:27
     * @Return:userModel
     * @Modified By:
     */
    SysUserModel findObject(@Param("id") int id)throws DataAccessException;

    /**
     * @Author:ZGP
     * @Desicription:  根据id删除
     * @param id id
     * @Date:Created in 2018/9/20 9:27
     * @Return:int 影响行数
     * @Modified By:
     */
    int deleteObject(@Param("id")int id)throws DataAccessException;
    /**
     * @Author:ZGP
     * @Desicription:  修改用户
     * @param model 用户对象
     * @Date:Created in 2018/9/20 9:27
     * @Return:userModel
     * @Modified By:
     */
    int editeObject(SysUserModel model)throws DataAccessException;
    /**
     * @Author:ZGP
     * @Desicription:  插入用户
     * @param model 用户对象
     * @Date:Created in 2018/9/20 9:27
     * @Return:userModel
     * @Modified By:
     */
    int insertObject(SysUserModel model)throws DataAccessException;

    /**
     * @Author:ZGP
     * @Desicription:  根据查询条件查询用户数
     * @param model 用户id
     * @Date:Created in 2018/9/20 9:27
     * @Return:userModel
     * @Modified By:
     */
    long findUserListNumber(SysUserModel model)throws DataAccessException;
    /**
     * @Author:ZGP
     * @Desicription:  分页查询用户列表
     * @param model 用户model
     * @Date:Created in 2018/9/20 9:27
     * @Return:userModel
     * @Modified By:
     */
    List<SysUserModel> findUserList(@Param("model")SysUserModel model,
                                    @Param("beginIndex")long beginIndex,
                                    @Param("everyPage")long everyPage)throws  DataAccessException;

    /**
     * @Author:ZGP
     * @Desicription:  根据角色id查询用户
     * @param roleId 角色id
     * @Date:Created in 2018/9/20 9:27
     * @Return:userModel
     * @Modified By:
     */
    List<SysUserModel>  findByRoleId(@Param("roleId")int roleId,@Param("adder")String adder)throws DataAccessException;

    /**
     *@Author:Shenzhiwei
     *@Desicription:查询所有用户
      * @param state 状态值
     *@Date:Created in 15:57 2018/9/27
     *@Return:返回数据集合
     * @Modified By:
     */
    List<SysUserModel> findAllUser(@Param("state")int state) throws DataAccessException;
    /**
     * @Author:ZGP
     * @Desicription:  根据姓名查询用户列表
     * @param fullName 姓名
     * @Date:Created in 2018/9/20 9:27
     * @Return:userModel
     * @Modified By:
     */
    List<SysUserModel> findUserListByFullName(@Param("fullName")String fullName)throws  DataAccessException;
    /**
     *@Author:ZGP
     *@Desicription:
     * @param addr 项目新增时  通知人列表
     *@Date:Created in 15:57 2018/9/27
     *@Return:返回数据集合
     *@Modified By:
     */
    List<SysUserModel> findUserListByAddrXz(@Param("adder")String  addr) throws DataAccessException;
    /**
     *@Author:ZGP
     *@Desicription:
     * @param addr 评论时  通知人列表
     *@Date:Created in 15:57 2018/9/27
     *@Return:返回数据集合
     *@Modified By:
     */
    List<SysUserModel> findUserListByAddrPl(@Param("adder")String  addr) throws DataAccessException;

}

package com.aorise.service.system.impl;

import com.aorise.exceptions.ServiceException;
import com.aorise.mapper.notification.NotificationUserMapper;
import com.aorise.mapper.system.*;
import com.aorise.model.system.*;
import com.aorise.service.system.SystemService;
import com.aorise.utils.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

/**
 * @Author:ZGP
 * @Desicription:用户管理业务
 * @Date:Created in 2018/9/21 13:48
 * @Modified By:
 */
@Transactional
@Service
public class SystemServiceImpl implements SystemService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Autowired
    private NotificationUserMapper notificationUserMapper;


    /**
     * @param username 用户名
     * @Author:ZGP
     * @Desicription: 根据用户名查询用户
     * @Date:Created in 2018/9/20 9:27
     * @Return:userModel
     * @Modified By:
     */
    public SysUserModel findByUsername(String username) {
        SysUserModel user=sysUserMapper.findByUsername(username);
        if(user!=null) {//未阅读通知条数
            user.setUnreadInform(notificationUserMapper.getByOnRead(user.getId()));
        }else{
            return null;
        }
        List<SysPermissionModel> list=user.getSysPermissions();
        if(list!=null&&list.size()>0) {
            Iterator it = list.iterator();
            if ((!"431200000000".equals(user.getAdder()))&&(!"5".equals(user.getGroups()))) {
                while (it.hasNext()) {
                    SysPermissionModel permissionModel = (SysPermissionModel) it.next();
                    if ("ZXXZ".equals(permissionModel.getCode()) || "ZXSC".equals(permissionModel.getCode())) {
                        it.remove();
                    }

                }
            }
        }
        return user;
    }

    /**
     * @param id 用户id
     * @Author:ZGP
     * @Desicription: 根据用户id查询用户
     * @Date:Created in 2018/9/20 9:27
     * @Return:userModel
     * @Modified By:
     */
    public SysUserModel findObject(int id) {
        return sysUserMapper.findObject(id);
    }

    /**
     * @param ids id
     * @Author:ZGP
     * @Desicription: 根据id集合删除
     * @Date:Created in 2018/9/20 9:27
     * @Return:int 影响行数
     * @Modified By:
     */
    public int deleteObject(String[] ids) {
        if (ids != null && ids.length > 0) {
            int ret = 0;
            for (String id : ids) {
                int intid;
                try {
                    intid = Integer.parseInt(id);
                } catch (Exception e) {
                    throw new ServiceException("id格式错误");
                }
                ret += sysUserMapper.deleteObject(intid);
                sysUserRoleMapper.deleteByUserId(intid);
            }
            return ret;
        }
        return 0;
    }

    /**
     * @param model 用户对象
     * @Author:ZGP
     * @Desicription: 修改用户
     * @Date:Created in 2018/9/20 9:27
     * @Return:userModel
     * @Modified By:
     */
    public int editeObject(SysUserModel model) {
        sysUserRoleMapper.deleteByUserId(model.getId());
        SysUserRoleModel urModel = new SysUserRoleModel();
        urModel.setUserId(model.getId());
        urModel.setRoleId(Integer.parseInt(model.getGroups()));
        sysUserRoleMapper.insertObject(urModel);
        return sysUserMapper.editeObject(model);
    }

    /**
     * @param model 用户对象
     * @Author:ZGP
     * @Desicription: 插入用户
     * @Date:Created in 2018/9/20 9:27
     * @Return:userModel
     * @Modified By:
     */
    public int insertObject(SysUserModel model) {

        int ret = sysUserMapper.insertObject(model);
        SysUserRoleModel urModel = new SysUserRoleModel();
        urModel.setUserId(model.getId());
        urModel.setRoleId(Integer.parseInt(model.getGroups()));
        sysUserRoleMapper.insertObject(urModel);
        return ret;

    }

    /**
     * @param model 用户model
     * @Author:ZGP
     * @Desicription: 分页查询用户列表
     * @Date:Created in 2018/9/20 9:27
     * @Return:userModel
     * @Modified By:
     */
    public List<SysUserModel> findUserList(SysUserModel model, Page page) {
        Long count=sysUserMapper.findUserListNumber(model);
        List<SysUserModel> list=null;
        if (count > 0) {
            page.setTotalCount(count);
            page.setTotalPage(count, page.getEveryPage());
            if (page.getCurrentPage() > 0 && page.getEveryPage() > 0) {
                //查询分页条数
                list = sysUserMapper.findUserList(model, page.getBeginIndex(), page.getEveryPage());
            }
        } else {
            page.setTotalCount(0L);
        }

        return list;
    }

    /**
     * @param
     * @Author:ZGP
     * @Desicription: 查询所有的角色
     * @Date:Created in 2018/9/20 9:27
     * @Return:SysRoleModels
     * @Modified By:
     */
    public List<SysRoleModel> findRoleList() {
        return sysRoleMapper.findRoleList();
    }

    /**
     * @Author:ZGP
     * @Desicription:  插入权限
     * @param
     * @Date:Created in 2018/9/25 9:27
     * @Return:SysRoleModels
     * @Modified By:
     */
    public int insertPermission(SysPermissionModel model) {
        return sysPermissionMapper.insertObject(model);
    }

    /**
     * @Author:ZGP
     * @Desicription:  插入角色权限关系
     * @param
     * @Date:Created in 2018/9/25 9:27
     * @Return:SysRoleModels
     * @Modified By:
     */
    public int insertRolePermission(int roleId, String[] permissionIds) {
        sysRolePermissionMapper.deleteByRoleId(roleId);//先删除角色下的所有权限
        if (permissionIds != null && permissionIds.length > 0) {
            int ret = 0;
            for (String id : permissionIds) {
                int intid;
                try {
                    intid = Integer.parseInt(id);
                } catch (Exception e) {
                    throw new ServiceException("id格式错误");
                }
                SysRolePermissionModel model=new SysRolePermissionModel();
                model.setRoleId(roleId);
                model.setPermissionId(intid);
                ret += sysRolePermissionMapper.insertObject(model);
            }
            return ret;
        }
        return 0;
    }
    /**
     * @Author:ZGP
     * @Desicription:  根据角色查询用户
     * @param roleId 用户名
     * @Date:Created in 2018/9/20 9:27
     * @Return:userModel
     * @Modified By:
     */
    public List<SysUserModel> findByRoleId(int roleId,String adder){
       return sysUserMapper.findByRoleId(roleId,adder);

    }

    /**
     *@Author:ZGP
     *@Desicription:查看所有权限列表
     *@Date:Created in 10:04 2018/9/17
     *@Return:返回受影响的行数
     * @Modified By:zgp
     */
    public List<SysPermissionModel> findPerList() {
        return sysPermissionMapper.findPerList();
    }

    /**
     *@Author:ZGP
     *@Desicription:根据角色id查询权限列表
     *@Date:Created in 10:04 2018/9/17
     *@Return:返回受影响的行数
     * @Modified By:zgp
     */
    public List<SysPermissionModel> findPerListByRoleId(int roleId) {
        return sysPermissionMapper.findPerListByRoleId(roleId);
    }
    /**
     *@Author:ZGP
     *@Desicription:统计角色人数
     *@Date:Created in 10:04 2018/9/17
     *@Return:list
     * @Modified By:zgp
     */
    public List<SysRoleModel> findRoleCount(){
        return sysRoleMapper.findRoleCount();
    }

    /**
     * @Author:ZGP
     * @Desicription:  根据姓名查询用户列表
     * @param fullName 姓名
     * @Date:Created in 2018/9/20 9:27
     * @Return:userModel
     * @Modified By:
     */
    public List<SysUserModel> findUserListByFullName(String fullName) {
        return sysUserMapper.findUserListByFullName(fullName);
    }

}

package com.aorise.service.common;

import com.aorise.model.common.AreaInfoModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author:Shenzhiwei
 * @Desicription:地区接口类
 * @Date:Created in 2018-09-27 09:59
 * @Modified By:
 */
public interface AreaInfoService {
    /**
     *@Author:Shenzhiwei
     *@Desicription:根据父级查询子节点
     * @param code 地区代码
     *@Date:Created in 9:54 2018/9/27
     *@Return:返回数据集合
     * @Modified By:
     */
    List<AreaInfoModel> getListByParent( String code) ;
    /**
     *@Author:Shenzhiwei
     *@Desicription:根据id查询父级
     * @param code 地区代码
     *@Date:Created in 9:55 2018/9/27
     *@Return:返回实体对象
     * @Modified By:
     */
    AreaInfoModel getByChild( String code) ;
    /**
     *@Author:Shenzhiwei
     *@Desicription:根据id查询当前地区
     * @param code 地区代码
     *@Date:Created in 9:55 2018/9/27
     *@Return:返回实体对象
     * @Modified By:
     */
    AreaInfoModel getById( String code) ;

    /**
     *@Author:ZGP
     *@Desicription:查询所有地区
     *@Date:Created in 9:55 2018/9/27
     *@Return:返回实体对象
     * @Modified By:
     */
    List<AreaInfoModel> getAll();
}

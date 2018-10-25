package com.aorise.service.common.impl;

import com.aorise.mapper.common.AreaInfoMapper;
import com.aorise.model.common.AreaInfoModel;
import com.aorise.service.common.AreaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:Shenzhiwei
 * @Desicription:
 * @Date:Created in 2018-09-27 10:00
 * @Modified By:
 */
@Service
public class AreaInfoServiceImpl implements AreaInfoService{
    @Autowired
    private AreaInfoMapper areaInfoMapper;
    /**
     * @param code 地区代码
     * @Author:Shenzhiwei
     * @Desicription:根据父级查询子节点
     * @Date:Created in 9:54 2018/9/27
     * @Return:返回数据集合
     * @Modified By:
     */
    @Override
    public List<AreaInfoModel> getListByParent(String code) {
        return areaInfoMapper.getListByParent(code);
    }

    /**
     * @param code 地区代码
     * @Author:Shenzhiwei
     * @Desicription:根据id查询父级
     * @Date:Created in 9:55 2018/9/27
     * @Return:返回实体对象
     * @Modified By:
     */
    @Override
    public AreaInfoModel getByChild(String code) {
        return areaInfoMapper.getByChild(code);
    }

    /**
     * @param code 地区代码
     * @Author:Shenzhiwei
     * @Desicription:根据id查询当前地区
     * @Date:Created in 9:55 2018/9/27
     * @Return:返回实体对象
     * @Modified By:
     */
    @Override
    public AreaInfoModel getById(String code) {
        return areaInfoMapper.getById(code);
    }

    /**
     *@Author:ZGP
     *@Desicription:查询所有地区
     *@Date:Created in 9:55 2018/9/27
     *@Return:返回实体对象
     * @Modified By:
     */
    public List<AreaInfoModel> getAll(){return areaInfoMapper.getAll();}
}

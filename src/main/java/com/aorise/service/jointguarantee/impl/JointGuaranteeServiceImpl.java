package com.aorise.service.jointguarantee.impl;

import com.aorise.mapper.jointguarantee.JointGuaranteeMapper;
import com.aorise.model.jointguarantee.JointGuaranteeModel;
import com.aorise.service.jointguarantee.JointGuaranteeService;
import com.aorise.utils.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description:联保行动
 * @Author:wei.peng
 * @Date:2018/10/12 11:25
 * @Version V1.0
 */
@Service
public class JointGuaranteeServiceImpl implements JointGuaranteeService{
    @Autowired
    JointGuaranteeMapper jointGuaranteeMapper;
    @Override
    public List<JointGuaranteeModel> getAllByPage(Map<String, Object> map, Page page) {
        List<JointGuaranteeModel> list=null;
        /**查询总数*/
        Integer count = jointGuaranteeMapper.getAllByPageCount(map);
        if (count > 0) {
            page.setTotalCount(Long.valueOf(count));
            page.setTotalPage(Long.valueOf(count), page.getEveryPage());
            if (page.getCurrentPage() > 0 && page.getEveryPage() > 0) {
                /**查询分页条数*/
                list = jointGuaranteeMapper.getAllByPage(map);
            }
        } else {
            page.setTotalCount(0L);
        }
        return list;
    }
}

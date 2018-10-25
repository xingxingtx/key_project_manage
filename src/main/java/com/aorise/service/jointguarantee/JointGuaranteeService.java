package com.aorise.service.jointguarantee;

import com.aorise.model.jointguarantee.JointGuaranteeModel;
import com.aorise.model.sitevisits.SiteVisitsInfoModel;
import com.aorise.utils.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/12.
 */
public interface JointGuaranteeService {
    /*@Author:wei.peng
   *@Desicription:分页查询我的联保代办事项
   * @param map 查询条件
   *@Date:Created in 15:28 2018/9/26
   *@Return:返回数据集合
   * @Modified By:
   */
    public List<JointGuaranteeModel> getAllByPage(Map<String, Object> map, Page page);
}

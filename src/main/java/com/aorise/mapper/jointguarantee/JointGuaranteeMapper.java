package com.aorise.mapper.jointguarantee;

import com.aorise.model.jointguarantee.JointGuaranteeModel;
import com.aorise.utils.page.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description:联保行动
 * @Author:wei.peng
 * @Date:2018/10/12 11:25
 * @Version V1.0
 */
@Mapper
@Component
public interface JointGuaranteeMapper {
    /*@Author:wei.peng
     *@Desicription:分页查询我的联保
     * @param map 查询条件
     *@Date:Created in 15:28 2018/10/12
     *@Return:返回数据集合
     * @Modified By:
     */
    public List<JointGuaranteeModel> getAllByPage(Map<String, Object> map);

    /**
     *@Author:weipeng
     *@Desicription:分页查询我的联保数据总数
     * @param map 查询条件
     *@Date:Created in 15:28 2018/10/12
     *@Return:返回数据总数
     * @Modified By:
     */
    int getAllByPageCount(Map<String, Object> map) throws DataAccessException;

}

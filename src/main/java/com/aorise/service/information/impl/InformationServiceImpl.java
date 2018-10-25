package com.aorise.service.information.impl;


import com.aorise.exceptions.ServiceException;
import com.aorise.mapper.information.InformationMapper;
import com.aorise.model.information.InformationModel;
import com.aorise.service.information.InformationService;
import com.aorise.utils.page.Page;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author:Shenzhiwei
 * @Desicription:资讯管理业务逻辑类
 * @Date:Created in 2018-09-05 16:04
 * @Modified By:
 */
@Service
public class InformationServiceImpl implements InformationService {

    @Autowired
    private InformationMapper informationMapper;

    /**
     * @param model 资讯管理实体对象
     * @Author:Shenzhiwei
     * @Desicription:添加资讯管理数据
     * @Date:Created in 15:27 2018/9/5
     * @Return:返回受影响的行数
     * @Modified By:
     */
    @Override
    public int insertObject(InformationModel model) {
        if(informationMapper.insertObject(model)>0){
            return model.getId();
        }
        return informationMapper.insertObject(model);
    }

    /**
     * @param model 资讯管理实体对象
     * @Author:Shenzhiwei
     * @Desicription:修改资讯管理数据
     * @Date:Created in 15:27 2018/9/5
     * @Return:返回受影响的行数
     * @Modified By:
     */
    @Override
    public int updateObject(InformationModel model) {
        return informationMapper.updateObject(model);
    }

    /**
     * @param id 主键id
     * @Author:Shenzhiwei
     * @Desicription:根据id查询资讯管理实体
     * @Date:Created in 15:27 2018/9/5
     * @Return:返回实体对象
     * @Modified By:
     */
    @Override
    public InformationModel getDetail(int id) {
        return informationMapper.getDetail(id);
    }

    /**
     * @param map  查询条件
     * @param page
     * @Author:Shenzhiwei
     * @Desicription:分页查询资讯管理数据
     * @Date:Created in 15:28 2018/9/5
     * @Return:返回数据集合
     * @Modified By:
     */
    @Override
    public List<InformationModel> getAllByPage(Map<String, Object> map, Page page) {
        List<InformationModel> list=null;
        /**查询总数*/
        Integer count = informationMapper.getAllByPageCount(map);
        if (count > 0) {
            page.setTotalCount(Long.valueOf(count));
            page.setTotalPage(Long.valueOf(count), page.getEveryPage());
            if (page.getCurrentPage() > 0 && page.getEveryPage() > 0) {
                /**查询分页条数*/
                list = informationMapper.getAllByPage(map);
            }
        } else {
            page.setTotalCount(0L);
        }
        return list;
    }

    /**
     * @param model 资讯管理实体对象
     * @Author:Shenzhiwei
     * @Desicription:修改资讯管理数据
     * @Date:Created in 15:27 2018/9/5
     * @Return:返回受影响的行数
     * @Modified By:
     */
    @Override
    public int updateStatus(InformationModel model) {
        return informationMapper.updateStatus(model);
    }

    /**
     * @param ids 主键id拼接字符串
     * @Author:Shenzhiwei
     * @Desicription:删除资讯管理数据
     * @Date:Created in 15:27 2018/9/5
     * @Return:返回受影响的行数
     * @Modified By:
     */
    @Override
    @Transactional
    public int deleteObject(String ids) {
        try {
            int ret=0;
            String[] id = ids.split(",");
            for (String s : id) {
                if (!Strings.isBlank(s)){
                    informationMapper.deleteObject(Integer.parseInt(s));
                    ret++;
                }
            }
            return  ret;
        }catch (Exception e){
            throw new ServiceException("id格式不正确");
        }
    }
}

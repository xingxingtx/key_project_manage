package com.aorise.service.imgfile.impl;

import com.aorise.mapper.imgfile.ImgFileMapper;
import com.aorise.model.imgfile.ImgFileModel;
import com.aorise.service.imgfile.ImgFileService;
import com.aorise.utils.define.ConstDefine;
import com.aorise.utils.fileutil.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author:Yangzepeng
 * @Description:
 * @Date:Created in 2018/10/15
 * @Modified By:
 */
@Service
@Transactional
public class ImgFileServiceImpl implements ImgFileService{

    @Autowired
    private ImgFileMapper imgFileMapper;

    @Autowired
    private FileUtil fileUtil;

    /**
     * @Author:Yangzepeng
     * @Desicription:
     * @param
     * @Date:2018/10/15 11:06
     * @Return:
     * @Modified By:
     */
    @Override
    public List<String> addImgFile(ImgFileModel model,List<MultipartFile> files) {
        List<String> resultList = new ArrayList<>();
        model.setState( ConstDefine.STATE_ABLE);
        for (MultipartFile file : files){
            String fileUrl = fileUtil.upload(file, "uploadFile/img/");
            resultList.add(fileUrl);
            model.setUrl(fileUrl);
            imgFileMapper.addImgFile(model);
        }
        return resultList;

    }

    /**
     * @Author:Yangzepeng
     * @Desicription:
     * @param
     * @Date:2018/10/15 11:35
     * @Return:
     * @Modified By:
     */
    @Override
    public List getImgFile(Map<String, Object> map) {
        return imgFileMapper.getImgFile(map);
    }

    /**
     * @Author:Yangzepeng
     * @Desicription:
     * @param
     * @Date:2018/10/15 13:26
     * @Return:
     * @Modified By:
     */
    @Override
    public int deleteImgFile(Map<String, Object> map) {
        ImgFileModel oldModel = imgFileMapper.getImgFileById(map);
        fileUtil.deleteFile(oldModel.getUrl());
        return imgFileMapper.deleteImgFile(map);
    }

}

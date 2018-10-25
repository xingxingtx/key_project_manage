package com.aorise.service.imgfile;

import com.aorise.model.imgfile.ImgFileModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author:Yangzepeng
 * @Description:
 * @Date:Created in 2018/10/15
 * @Modified By:
 */
public interface ImgFileService {
    /**
     * @Author:Yangzepeng
     * @Desicription:
     * @param
     * @Date:2018/10/15 11:04
     * @Return:
     * @Modified By:
     */
    List<String> addImgFile(ImgFileModel model,List<MultipartFile> file);

    /**
     * @Author:Yangzepeng
     * @Desicription:
     * @param 
     * @Date:2018/10/15 11:34
     * @Return:
     * @Modified By:
     */
    List getImgFile(Map<String, Object> map);

    /**
     * @Author:Yangzepeng
     * @Desicription:
     * @param
     * @Date:2018/10/15 13:25
     * @Return:
     * @Modified By:
     */
    int deleteImgFile(Map<String, Object> map);
}

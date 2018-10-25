package com.aorise.controller.projectdasecase;

import com.alibaba.fastjson.JSON;
import com.aorise.annotation.SystemControllerLog;
import com.aorise.config.Config;
import com.aorise.exceptions.DBErrorException;
import com.aorise.exceptions.DataValidationException;
import com.aorise.exceptions.ProjectException;
import com.aorise.model.projectdasecase.ProjectAddrInfoModel;
import com.aorise.model.projectdasecase.ProjectDaseCaseModel;
import com.aorise.model.system.SysRoleModel;
import com.aorise.model.system.SysUserModel;
import com.aorise.service.projectdasecase.ProjectDaseCaseService;
import com.aorise.service.system.SystemService;
import com.aorise.utils.Utils;
import com.aorise.utils.define.ConstDefine;
import com.aorise.utils.define.StatusDefine;
import com.aorise.utils.define.StatusDefineMessage;
import com.aorise.utils.json.JsonResponseData;
import com.aorise.utils.page.Page;
import com.aorise.utils.validation.DataValidation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.NumberFormat;
import java.util.*;

/**
 * @Description:联保项目基本情况接口
 * @Author:Huangdong
 * @Date:2018/9/18 9:25
 * @Version V1.0
 */
@RestController
@Api(description = "联保项目基本情况接口")
public class ProjectDaseCaseController {

    /**
     * 注入打印日志
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectDaseCaseService projectDaseCaseService;

    @Autowired
    private DataValidation dataValidation;

    @Autowired
    private SystemService systemService;


    /**
     * 新增联保项目基本情况信息
     * HTTP 方式：POST
     * API 路径：/api/projectdasecase
     * 方法名：addProject
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @ApiOperation(value = "新增联保项目基本情况信息", httpMethod = "POST", response = String.class,
            notes = "新增联保项目基本情况信息，供后台新增联保项目基本情况信息使用")
    @SystemControllerLog(descrption = "新增联保项目基本情况信息", actionType = "1", modules = "项目基本情况信息模块")
    @RequiresPermissions("XMXQXZ")
    @RequestMapping(value = "/api/projectdasecase", method = RequestMethod.POST)
    public String addProject(@ApiParam(value = "项目名称", required = true) @RequestParam(value = "name", required = true) String name,
                             @ApiParam(value = "项目类别", required = true) @RequestParam(value = "type", required = true) String type,
                             @ApiParam(value = "立项时间", required = false) @RequestParam(value = "setTime", required = false) String setTime,
                             @ApiParam(value = "项目总投资金额", required = true) @RequestParam(value = "sumMoney", required = true) String sumMoney,
                             @ApiParam(value = "已完成金额", required = true) @RequestParam(value = "finishMoney", required = true) String finishMoney,
                             @ApiParam(value = "项目进度", required = false) @RequestParam(value = "progress", required = false) String progress,
                             @ApiParam(value = "项目状态", required = true) @RequestParam(value = "projectState", required = true) Integer projectState,
                             @ApiParam(value = "开工时间", required = true) @RequestParam(value = "openTime", required = true) String openTime,
                             @ApiParam(value = "计划完成时间", required = true) @RequestParam(value = "completedTime", required = true) String completedTime,
                             @ApiParam(value = "是否跨区域", required = true) @RequestParam(value = "isSkip", required = true) Integer isSkip,
                             @ApiParam(value = "项目所在县(区)", required = true) @RequestParam(value = "county", required = true) String county,
                             @ApiParam(value = "联保联络员(县区)", required = false) @RequestParam(value = "countyPerson", required = false) String countyPerson,
                             @ApiParam(value = "职务(县区)", required = false) @RequestParam(value = "countyPosition", required = false) String countyPosition,
                             @ApiParam(value = "联系方式(县区)", required = false) @RequestParam(value = "countyPhone", required = false) String countyPhone,
                             @ApiParam(value = "项目所在乡镇", required = false) @RequestParam(value = "township", required = false) String township,
                             @ApiParam(value = "联保联络员(乡镇)", required = false) @RequestParam(value = "townshipPerson", required = false) String townshipPerson,
                             @ApiParam(value = "职务(乡镇)", required = false) @RequestParam(value = "townshipPosition", required = false) String townshipPosition,
                             @ApiParam(value = "联系方式(乡镇)", required = false) @RequestParam(value = "townshipPhone", required = false) String townshipPhone,
                             @ApiParam(value = "项目所在村", required = false) @RequestParam(value = "village", required = false) String village,
                             @ApiParam(value = "联保联络员(村)", required = false) @RequestParam(value = "villagePerson", required = false) String villagePerson,
                             @ApiParam(value = "职务(村)", required = false) @RequestParam(value = "positions", required = false) String positions,
                             @ApiParam(value = "联系方式(村)", required = false) @RequestParam(value = "phone", required = false) String phone,
                             @ApiParam(value = "项目所在组", required = false) @RequestParam(value = "progroup", required = false) String progroup,
                             @ApiParam(value = "联保联络员(组)", required = false) @RequestParam(value = "progroupPerson", required = false) String progroupPerson,
                             @ApiParam(value = "职务(组)", required = false) @RequestParam(value = "progroupPosition", required = false) String progroupPosition,
                             @ApiParam(value = "联系方式(组)", required = false) @RequestParam(value = "progroupPhone", required = false) String progroupPhone,
                             @ApiParam(value = "项目详细地址", required = false) @RequestParam(value = "detailedAddr", required = false) String detailedAddr,
                             @ApiParam(value = "所在辖区派出所", required = false) @RequestParam(value = "policeStation", required = false) String policeStation,
                             @ApiParam(value = "派出所负责人", required = false) @RequestParam(value = "leader", required = false) String leader,
                             @ApiParam(value = "派出所负责人职务", required = false) @RequestParam(value = "leaderPosition", required = false) String leaderPosition,
                             @ApiParam(value = "派出所负责人电话", required = false) @RequestParam(value = "stationPhone", required = false) String stationPhone,
                             @ApiParam(value = "项目业主单位", required = false) @RequestParam(value = "ownerCorp", required = false) String ownerCorp,
                             @ApiParam(value = "项目业主联系人", required = false) @RequestParam(value = "ownerContact", required = false) String ownerContact,
                             @ApiParam(value = "项目业主联系人职务", required = false) @RequestParam(value = "ownerPosition", required = false) String ownerPosition,
                             @ApiParam(value = "项目业主联系人电话", required = false) @RequestParam(value = "ownerPhone", required = false) String ownerPhone,
                             @ApiParam(value = "施工单位", required = false) @RequestParam(value = "constructionOrg", required = false) String constructionOrg,
                             @ApiParam(value = "施工单位联系人", required = false) @RequestParam(value = "constructionContact", required = false) String constructionContact,
                             @ApiParam(value = "施工单位联系人职务", required = false) @RequestParam(value = "constructionPosition", required = false) String constructionPosition,
                             @ApiParam(value = "施工单位联系人电话", required = false) @RequestParam(value = "constructionPhone", required = false) String constructionPhone,
                             @ApiParam(value = "经度", required = false) @RequestParam(value = "longitude", required = false) String longitude,
                             @ApiParam(value = "纬度", required = false) @RequestParam(value = "latitude", required = false) String latitude,
                             @ApiParam(value = "项目简介", required = false) @RequestParam(value = "projectContent", required = false) String projectContent,
                             @ApiParam(value = "备注", required = false) @RequestParam(value = "content", required = false) String content,
                             @ApiParam(value = "项目所在县(区)2", required = false) @RequestParam(value = "county2", required = false) String county2,
                             @ApiParam(value = "联保联络员(县区)2", required = false) @RequestParam(value = "countyPerson2", required = false) String countyPerson2,
                             @ApiParam(value = "职务(县区)2", required = false) @RequestParam(value = "countyPosition2", required = false) String countyPosition2,
                             @ApiParam(value = "联系方式(县区)2", required = false) @RequestParam(value = "countyPhone2", required = false) String countyPhone2,
                             @ApiParam(value = "项目所在乡镇2", required = false) @RequestParam(value = "township2", required = false) String township2,
                             @ApiParam(value = "联保联络员(乡镇)2", required = false) @RequestParam(value = "townshipPerson2", required = false) String townshipPerson2,
                             @ApiParam(value = "职务(乡镇)2", required = false) @RequestParam(value = "townshipPosition2", required = false) String townshipPosition2,
                             @ApiParam(value = "联系方式(乡镇)2", required = false) @RequestParam(value = "townshipPhone2", required = false) String townshipPhone2,
                             @ApiParam(value = "项目所在村2", required = false) @RequestParam(value = "village2", required = false) String village2,
                             @ApiParam(value = "联保联络员(村)2", required = false) @RequestParam(value = "villagePerson2", required = false) String villagePerson2,
                             @ApiParam(value = "职务(村)2", required = false) @RequestParam(value = "positions2", required = false) String positions2,
                             @ApiParam(value = "联系方式(村)2", required = false) @RequestParam(value = "phone2", required = false) String phone2,
                             @ApiParam(value = "项目所在组2", required = false) @RequestParam(value = "progroup2", required = false) String progroup2,
                             @ApiParam(value = "联保联络员(组)2", required = false) @RequestParam(value = "progroupPerson2", required = false) String progroupPerson2,
                             @ApiParam(value = "职务(组)2", required = false) @RequestParam(value = "progroupPosition2", required = false) String progroupPosition2,
                             @ApiParam(value = "联系方式(组)2", required = false) @RequestParam(value = "progroupPhone2", required = false) String progroupPhone2,
                             @ApiParam(value = "项目详细地址2", required = false) @RequestParam(value = "detailedAddr2", required = false) String detailedAddr2,
                             @ApiParam(value = "所在辖区派出所2", required = false) @RequestParam(value = "policeStation2", required = false) String policeStation2,
                             @ApiParam(value = "派出所负责人2", required = false) @RequestParam(value = "leader2", required = false) String leader2,
                             @ApiParam(value = "派出所负责人职务2", required = false) @RequestParam(value = "leaderPosition2", required = false) String leaderPosition2,
                             @ApiParam(value = "派出所负责人电话2", required = false) @RequestParam(value = "stationPhone2", required = false) String stationPhone2,
                             @ApiParam(value = "项目业主单位2", required = false) @RequestParam(value = "ownerCorp2", required = false) String ownerCorp2,
                             @ApiParam(value = "项目业主联系人2", required = false) @RequestParam(value = "ownerContact2", required = false) String ownerContact2,
                             @ApiParam(value = "项目业主联系人职务2", required = false) @RequestParam(value = "ownerPosition2", required = false) String ownerPosition2,
                             @ApiParam(value = "项目业主联系人电话2", required = false) @RequestParam(value = "ownerPhone2", required = false) String ownerPhone2,
                             @ApiParam(value = "施工单位2", required = false) @RequestParam(value = "constructionOrg2", required = false) String constructionOrg2,
                             @ApiParam(value = "施工单位联系人2", required = false) @RequestParam(value = "constructionContact2", required = false) String constructionContact2,
                             @ApiParam(value = "施工单位联系人职务2", required = false) @RequestParam(value = "constructionPosition2", required = false) String constructionPosition2,
                             @ApiParam(value = "施工单位联系人电话2", required = false) @RequestParam(value = "constructionPhone2", required = false) String constructionPhone2,
                             @ApiParam(value = "经度2", required = false) @RequestParam(value = "longitude2", required = false) String longitude2,
                             @ApiParam(value = "纬度2", required = false) @RequestParam(value = "latitude2", required = false) String latitude2,
                             @ApiParam(value = "预警员json", required = false) @RequestParam(value = "warnUser", required = false) String warnUser,
                             @ApiParam(value = "登录token", required = false) @RequestParam(value = "token", required = false) String token, HttpServletRequest request) {

        logger.info("项目名称：" + name);
        logger.info("项目类别：" + type);
        logger.info("立项时间：" + setTime);
        logger.info("项目总投资金额：" + sumMoney);
        logger.info("已完成金额：" + finishMoney);
        logger.info("项目进度：" + progress);
        logger.info("项目状态：" + projectState);
        logger.info("开工时间：" + openTime);
        logger.info("计划完成时间：" + completedTime);
        logger.info("是否跨区县：" + isSkip);
        logger.info("项目所在县(区)：" + county);
        logger.info("联保联络员(县区)：" + countyPerson);
        logger.info("职务(县区)：" + countyPosition);
        logger.info("联系方式(县区)：" + countyPhone);
        logger.info("项目所在乡镇：" + township);
        logger.info("联保联络员(乡镇)：" + townshipPerson);
        logger.info("职务(乡镇)：" + townshipPosition);
        logger.info("联系方式(乡镇)：" + townshipPhone);
        logger.info("项目所在村：" + village);
        logger.info("联保联络员(村)：" + villagePerson);
        logger.info("职务(村)：" + positions);
        logger.info("联系方式(村)：" + phone);
        logger.info("项目所在组：" + progroup);
        logger.info("联保联络员(组)：" + progroupPerson);
        logger.info("职务(组)：" + progroupPosition);
        logger.info("联系方式(组)：" + progroupPhone);
        logger.info("项目详细地址：" + detailedAddr);
        logger.info("所在辖区派出所：" + policeStation);
        logger.info("派出所负责人：" + leader);
        logger.info("派出所负责人职务：" + leaderPosition);
        logger.info("派出所负责人电话：" + stationPhone);
        logger.info("项目业主单位：" + ownerCorp);
        logger.info("项目业主联系人：" + ownerContact);
        logger.info("项目业主联系人职务：" + ownerPosition);
        logger.info("项目业主联系人电话：" + ownerPhone);
        logger.info("施工单位：" + constructionOrg);
        logger.info("施工单位联系人：" + constructionContact);
        logger.info("施工单位联系人职务：" + constructionPosition);
        logger.info("施工单位联系人电话：" + constructionPhone);
        logger.info("经度：" + longitude);
        logger.info("纬度：" + latitude);
        logger.info("项目简介：" + projectContent);
        logger.info("备注：" + content);

        try {
            //参数验证
            dataValidation.chkLength(name, 0, 50, "项目名称仅支持大小写字母、数字及汉字输入，长度不超过50个字符");

            dataValidation.chkLength(sumMoney, 0, 20, "项目总投资金额长度不符合规则");
            dataValidation.chkLength(finishMoney, 0, 20, "已完成金额长度不符合规则");

            dataValidation.chkLength(countyPosition, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(townshipPosition, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(positions, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(progroupPosition, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(countyPhone, 0, 20, "手机号长度不符合规则");
            dataValidation.chkLength(townshipPhone, 0, 20, "手机号长度不符合规则");
            dataValidation.chkLength(phone, 0, 20, "手机号长度不符合规则");
            dataValidation.chkLength(progroupPhone, 0, 20, "手机号长度不符合规则");
            dataValidation.chkLength(countyPerson, 0, 20, "联保联络员（联保办）长度不符合规则");
            dataValidation.chkLength(townshipPerson, 0, 20, "联保联络员（乡、镇）长度不符合规则");
            dataValidation.chkLength(villagePerson, 0, 20, "联保联络员（村）长度不符合规则");
            dataValidation.chkLength(progroupPerson, 0, 20, "联保联络员（组）长度不符合规则");
            dataValidation.chkLength(detailedAddr, 0, 50, "项目详细地址长度不符合规则");
            dataValidation.chkLength(policeStation, 0, 50, "所在辖区派出所长度不符合规则");
            dataValidation.chkLength(leader, 0, 20, "负责人长度不符合规则");
            dataValidation.chkLength(leaderPosition, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(stationPhone, 0, 20, "手机号长度不符合规则");
            dataValidation.chkLength(ownerCorp, 0, 50, "项目业主单位长度不符合规则");
            dataValidation.chkLength(ownerContact, 0, 20, "项目业主联系人长度不符合规则");
            dataValidation.chkLength(ownerPosition, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(ownerPhone, 0, 20, "手机号长度不符合规则");
            dataValidation.chkLength(constructionOrg, 0, 50, "施工单位长度不符合规则");
            dataValidation.chkLength(constructionContact, 0, 20, "施工单位联系人长度不符合规则");
            dataValidation.chkLength(constructionPosition, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(constructionPhone, 0, 20, "手机号长度不符合规则");

            dataValidation.chkLength(countyPosition2, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(townshipPosition2, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(positions2, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(progroupPosition2, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(countyPhone2, 0, 20, "手机号长度不符合规则");
            dataValidation.chkLength(townshipPhone2, 0, 20, "手机号长度不符合规则");
            dataValidation.chkLength(phone2, 0, 20, "手机号长度不符合规则");
            dataValidation.chkLength(progroupPhone2, 0, 20, "手机号长度不符合规则");
            dataValidation.chkLength(countyPerson2, 0, 20, "联保联络员（联保办）长度不符合规则");
            dataValidation.chkLength(townshipPerson2, 0, 20, "联保联络员（乡、镇）长度不符合规则");
            dataValidation.chkLength(villagePerson2, 0, 20, "联保联络员（村）长度不符合规则");
            dataValidation.chkLength(progroupPerson2, 0, 20, "联保联络员（组）长度不符合规则");
            dataValidation.chkLength(detailedAddr2, 0, 50, "项目详细地址长度不符合规则");
            dataValidation.chkLength(policeStation2, 0, 50, "所在辖区派出所长度不符合规则");
            dataValidation.chkLength(leader2, 0, 20, "负责人长度不符合规则");
            dataValidation.chkLength(leaderPosition2, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(stationPhone2, 0, 20, "手机号长度不符合规则");
            dataValidation.chkLength(ownerCorp2, 0, 50, "项目业主单位长度不符合规则");
            dataValidation.chkLength(ownerContact2, 0, 20, "项目业主联系人长度不符合规则");
            dataValidation.chkLength(ownerPosition2, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(ownerPhone2, 0, 20, "手机号长度不符合规则");
            dataValidation.chkLength(constructionOrg2, 0, 50, "施工单位长度不符合规则");
            dataValidation.chkLength(constructionContact2, 0, 20, "施工单位联系人长度不符合规则");
            dataValidation.chkLength(constructionPosition2, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(constructionPhone2, 0, 20, "手机号长度不符合规则");
            dataValidation.chkLength(projectContent, 0, 1000, "项目简介长度不符合规则");
            dataValidation.chkLength(content, 0, 200, "备注长度不符合规则");


            //获取当前url
            String url = request.getScheme() + "//" + request.getServerName() + ":" + request.getServerPort() + "/";

            /**组装新增联保项目基本情况model*/
            ProjectDaseCaseModel pdcm = new ProjectDaseCaseModel();
            pdcm.setName(name);
            pdcm.setType(Integer.parseInt(type));
            pdcm.setSetTime(setTime);
            pdcm.setSumMoney(Double.parseDouble(sumMoney));
            pdcm.setFinishMoney(Double.parseDouble(finishMoney));

            NumberFormat nbf= NumberFormat.getInstance();
            nbf.setMinimumFractionDigits(2);
            String c = nbf.format(pdcm.getFinishMoney() /pdcm.getSumMoney());
            pdcm.setProgress(c);

            if (projectState == null) {
                projectState = 1;
            }
            pdcm.setProjectState(projectState);

            pdcm.setOpenTime(openTime);
            pdcm.setCompletedTime(completedTime);
            if (isSkip == null) {
                isSkip = 2;
            }
            pdcm.setIsSkip(isSkip);
            pdcm.setProjectContent(projectContent);
            pdcm.setContent(content);
            pdcm.setState(ConstDefine.STATE_ABLE);
            pdcm.setCreateTime(Config.DATE_FORMAT.format(new Date()));

            //4、获取预警员json并将其转换为Model
            if (!StringUtils.isBlank(warnUser)) {
                List<SysUserModel> lists = JSON.parseArray(warnUser, SysUserModel.class);

                List<SysUserModel> users = new ArrayList<SysUserModel>();
                for (int i = 0; i < lists.size(); i++) {
                    SysUserModel model = lists.get(i);
                    model.setPassWord(Utils.getMd5DigestAsHex(model.getPhone().substring(model.getPhone().length()-6)));//默认电话号码后6位
                    //判断用户是否存在
                    SysUserModel oldModel = systemService.findByUsername(model.getUserName());
                    if (oldModel != null) {
                        return new JsonResponseData(false, "", StatusDefine.U_EXIST_USER,
                                StatusDefineMessage.GetMessage(StatusDefine.U_EXIST_USER), null).toString();
                    }
                    systemService.insertObject(model);
                    users.add(model);
                }
                pdcm.setUserModels(users);
            }
            List<ProjectAddrInfoModel> list = new ArrayList<>();

            /**组装联保项目地址信息model*/

            ProjectAddrInfoModel paim = new ProjectAddrInfoModel();
            paim.setCounty(county);
            paim.setCountyPerson(countyPerson);
            paim.setCountyPosition(countyPosition);
            paim.setCountyPhone(countyPhone);
            paim.setTownship(township);
            paim.setTownshipPerson(townshipPerson);
            paim.setTownshipPosition(townshipPosition);
            paim.setTownshipPhone(townshipPhone);
            paim.setVillage(village);
            paim.setVillagePerson(villagePerson);
            paim.setPositions(positions);
            paim.setPhone(phone);
            paim.setProgroup(progroup);
            paim.setProgroupPerson(progroupPerson);
            paim.setProgroupPosition(progroupPosition);
            paim.setProgroupPhone(progroupPhone);
            paim.setDetailedAddr(detailedAddr);
            paim.setPoliceStation(policeStation);
            paim.setLeader(leader);
            paim.setLeaderPosition(leaderPosition);
            paim.setStationPhone(stationPhone);
            paim.setOwnerCorp(ownerCorp);
            paim.setOwnerContact(ownerContact);
            paim.setOwnerPosition(ownerPosition);
            paim.setOwnerPhone(ownerPhone);
            paim.setConstructionOrg(constructionOrg);
            paim.setConstructionContact(constructionContact);
            paim.setConstructionPosition(constructionPosition);
            paim.setConstructionPhone(constructionPhone);
            paim.setLongitude(longitude);
            paim.setLatitude(latitude);
            paim.setState(ConstDefine.STATE_ABLE);
            paim.setCreateTime(Config.DATE_FORMAT.format(new Date()));
            list.add(paim);

            /**如果跨区县*/
            if (isSkip == 1) {
                ProjectAddrInfoModel paim2 = new ProjectAddrInfoModel();
                paim2.setCounty(county2);
                paim2.setCountyPerson(countyPerson2);
                paim2.setCountyPosition(countyPosition2);
                paim2.setCountyPhone(countyPhone2);
                paim2.setTownship(township2);
                paim2.setTownshipPerson(townshipPerson2);
                paim2.setTownshipPosition(townshipPosition2);
                paim2.setTownshipPhone(townshipPhone2);
                paim2.setVillage(village2);
                paim2.setVillagePerson(villagePerson2);
                paim2.setPositions(positions2);
                paim2.setPhone(phone2);
                paim2.setProgroup(progroup2);
                paim2.setProgroupPerson(progroupPerson2);
                paim2.setProgroupPosition(progroupPosition2);
                paim2.setProgroupPhone(progroupPhone2);
                paim2.setDetailedAddr(detailedAddr2);
                paim2.setPoliceStation(policeStation2);
                paim2.setLeader(leader2);
                paim2.setLeaderPosition(leaderPosition2);
                paim2.setStationPhone(stationPhone2);
                paim2.setOwnerCorp(ownerCorp2);
                paim2.setOwnerContact(ownerContact2);
                paim2.setOwnerPosition(ownerPosition2);
                paim2.setOwnerPhone(ownerPhone2);
                paim2.setConstructionOrg(constructionOrg2);
                paim2.setConstructionContact(constructionContact2);
                paim2.setConstructionPosition(constructionPosition2);
                paim2.setConstructionPhone(constructionPhone2);
                paim2.setLongitude(longitude2);
                paim2.setLatitude(latitude2);
                paim2.setState(ConstDefine.STATE_ABLE);
                paim2.setCreateTime(Config.DATE_FORMAT.format(new Date()));
                list.add(paim2);
            }

            int rel = projectDaseCaseService.addProject(pdcm, list, url);
            if (rel > 0) {
                return new JsonResponseData(true,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS,
                        "插入成功", "id:" + rel).toString();
            } else {
                return new JsonResponseData(false,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), StatusDefine.FAILURE,
                        "插入失败", "").toString();
            }
        } catch (ProjectException e) {
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:addPerson..msg:新增联保项目基本情况信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    StatusDefine.SYS_ERROR, e.getMessage(), null).toString();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:addPerson..msg:新增联保项目基本情况信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "数据类型错误", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        } catch (DBErrorException e) {
            logger.error("controller:ProjectDaseCaseController. ");
            logger.error("function:addPerson..msg:新增联保项目基本情况信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), StatusDefine.DB_ERROR, "",
                    null).toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:addPerson..msg:新增联保项目基本情况信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }

    }

    /**
     * 编辑联保项目基本情况信息
     * HTTP 方式：PUT
     * API 路径：/api/projectdasecase
     * 方法名：updProject
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @ApiOperation(value = "编辑联保项目基本情况信息", httpMethod = "PUT", response = String.class,
            notes = "编辑联保项目基本情况信息，供后台编辑联保项目基本情况信息使用")
    @SystemControllerLog(descrption = "编辑联保项目基本情况信息", actionType = "3", modules = "项目基本情况信息模块")
    @RequiresPermissions("XMXQBJ")
    @RequestMapping(value = "/api/projectdasecase", method = RequestMethod.PUT)
    public String updProject(@ApiParam(value = "项目id", required = true) @RequestParam(value = "id", required = true) String id,
                             @ApiParam(value = "项目地址id", required = true) @RequestParam(value = "addrid", required = true) String addrid,
                             @ApiParam(value = "项目地址id2", required = false) @RequestParam(value = "addrid2", required = false) String addrid2,
                             @ApiParam(value = "项目名称", required = true) @RequestParam(value = "name", required = true) String name,
                             @ApiParam(value = "项目类别", required = true) @RequestParam(value = "type", required = true) String type,
                             @ApiParam(value = "立项时间", required = false) @RequestParam(value = "setTime", required = false) String setTime,
                             @ApiParam(value = "项目总投资金额", required = true) @RequestParam(value = "sumMoney", required = true) String sumMoney,
                             @ApiParam(value = "已完成金额", required = true) @RequestParam(value = "finishMoney", required = true) String finishMoney,
                             @ApiParam(value = "项目进度", required = false) @RequestParam(value = "progress", required = false) String progress,
                             @ApiParam(value = "项目状态", required = true) @RequestParam(value = "projectState", required = true) Integer projectState,
                             @ApiParam(value = "开工时间", required = true) @RequestParam(value = "openTime", required = true) String openTime,
                             @ApiParam(value = "计划完成时间", required = true) @RequestParam(value = "completedTime", required = true) String completedTime,
                             @ApiParam(value = "是否跨区域", required = true) @RequestParam(value = "isSkip", required = true) Integer isSkip,
                             @ApiParam(value = "预警员json", required = false) @RequestParam(value = "warnUser", required = false) String warnUser,
                             @ApiParam(value = "项目所在县(区)", required = true) @RequestParam(value = "county", required = true) String county,
                             @ApiParam(value = "联保联络员(县区)", required = false) @RequestParam(value = "countyPerson", required = false) String countyPerson,
                             @ApiParam(value = "职务(县区)", required = false) @RequestParam(value = "countyPosition", required = false) String countyPosition,
                             @ApiParam(value = "联系方式(县区)", required = false) @RequestParam(value = "countyPhone", required = false) String countyPhone,
                             @ApiParam(value = "项目所在乡镇", required = false) @RequestParam(value = "township", required = false) String township,
                             @ApiParam(value = "联保联络员(乡镇)", required = false) @RequestParam(value = "townshipPerson", required = false) String townshipPerson,
                             @ApiParam(value = "职务(乡镇)", required = false) @RequestParam(value = "townshipPosition", required = false) String townshipPosition,
                             @ApiParam(value = "联系方式(乡镇)", required = false) @RequestParam(value = "townshipPhone", required = false) String townshipPhone,
                             @ApiParam(value = "项目所在村", required = false) @RequestParam(value = "village", required = false) String village,
                             @ApiParam(value = "联保联络员(村)", required = false) @RequestParam(value = "villagePerson", required = false) String villagePerson,
                             @ApiParam(value = "职务(村)", required = false) @RequestParam(value = "positions", required = false) String positions,
                             @ApiParam(value = "联系方式(村)", required = false) @RequestParam(value = "phone", required = false) String phone,
                             @ApiParam(value = "项目所在组", required = false) @RequestParam(value = "progroup", required = false) String progroup,
                             @ApiParam(value = "联保联络员(组)", required = false) @RequestParam(value = "progroupPerson", required = false) String progroupPerson,
                             @ApiParam(value = "职务(组)", required = false) @RequestParam(value = "progroupPosition", required = false) String progroupPosition,
                             @ApiParam(value = "联系方式(组)", required = false) @RequestParam(value = "progroupPhone", required = false) String progroupPhone,
                             @ApiParam(value = "项目详细地址", required = false) @RequestParam(value = "detailedAddr", required = false) String detailedAddr,
                             @ApiParam(value = "所在辖区派出所", required = false) @RequestParam(value = "policeStation", required = false) String policeStation,
                             @ApiParam(value = "派出所负责人", required = false) @RequestParam(value = "leader", required = false) String leader,
                             @ApiParam(value = "派出所负责人职务", required = false) @RequestParam(value = "leaderPosition", required = false) String leaderPosition,
                             @ApiParam(value = "派出所负责人电话", required = false) @RequestParam(value = "stationPhone", required = false) String stationPhone,
                             @ApiParam(value = "项目业主单位", required = false) @RequestParam(value = "ownerCorp", required = false) String ownerCorp,
                             @ApiParam(value = "项目业主联系人", required = false) @RequestParam(value = "ownerContact", required = false) String ownerContact,
                             @ApiParam(value = "项目业主联系人职务", required = false) @RequestParam(value = "ownerPosition", required = false) String ownerPosition,
                             @ApiParam(value = "项目业主联系人电话", required = false) @RequestParam(value = "ownerPhone", required = false) String ownerPhone,
                             @ApiParam(value = "施工单位", required = false) @RequestParam(value = "constructionOrg", required = false) String constructionOrg,
                             @ApiParam(value = "施工单位联系人", required = false) @RequestParam(value = "constructionContact", required = false) String constructionContact,
                             @ApiParam(value = "施工单位联系人职务", required = false) @RequestParam(value = "constructionPosition", required = false) String constructionPosition,
                             @ApiParam(value = "施工单位联系人电话", required = false) @RequestParam(value = "constructionPhone", required = false) String constructionPhone,
                             @ApiParam(value = "经度", required = false) @RequestParam(value = "longitude", required = false) String longitude,
                             @ApiParam(value = "纬度", required = false) @RequestParam(value = "latitude", required = false) String latitude,
                             @ApiParam(value = "项目简介", required = false) @RequestParam(value = "projectContent", required = false) String projectContent,
                             @ApiParam(value = "备注", required = false) @RequestParam(value = "content", required = false) String content,
                             @ApiParam(value = "项目所在县(区)2", required = false) @RequestParam(value = "county2", required = false) String county2,
                             @ApiParam(value = "联保联络员(县区)2", required = false) @RequestParam(value = "countyPerson2", required = false) String countyPerson2,
                             @ApiParam(value = "职务(县区)2", required = false) @RequestParam(value = "countyPosition2", required = false) String countyPosition2,
                             @ApiParam(value = "联系方式(县区)2", required = false) @RequestParam(value = "countyPhone2", required = false) String countyPhone2,
                             @ApiParam(value = "项目所在乡镇2", required = false) @RequestParam(value = "township2", required = false) String township2,
                             @ApiParam(value = "联保联络员(乡镇)2", required = false) @RequestParam(value = "townshipPerson2", required = false) String townshipPerson2,
                             @ApiParam(value = "职务(乡镇)2", required = false) @RequestParam(value = "townshipPosition2", required = false) String townshipPosition2,
                             @ApiParam(value = "联系方式(乡镇)2", required = false) @RequestParam(value = "townshipPhone2", required = false) String townshipPhone2,
                             @ApiParam(value = "项目所在村2", required = false) @RequestParam(value = "village2", required = false) String village2,
                             @ApiParam(value = "联保联络员(村)2", required = false) @RequestParam(value = "villagePerson2", required = false) String villagePerson2,
                             @ApiParam(value = "职务(村)2", required = false) @RequestParam(value = "positions2", required = false) String positions2,
                             @ApiParam(value = "联系方式(村)2", required = false) @RequestParam(value = "phone2", required = false) String phone2,
                             @ApiParam(value = "项目所在组2", required = false) @RequestParam(value = "progroup2", required = false) String progroup2,
                             @ApiParam(value = "联保联络员(组)2", required = false) @RequestParam(value = "progroupPerson2", required = false) String progroupPerson2,
                             @ApiParam(value = "职务(组)2", required = false) @RequestParam(value = "progroupPosition2", required = false) String progroupPosition2,
                             @ApiParam(value = "联系方式(组)2", required = false) @RequestParam(value = "progroupPhone2", required = false) String progroupPhone2,
                             @ApiParam(value = "项目详细地址2", required = false) @RequestParam(value = "detailedAddr2", required = false) String detailedAddr2,
                             @ApiParam(value = "所在辖区派出所2", required = false) @RequestParam(value = "policeStation2", required = false) String policeStation2,
                             @ApiParam(value = "派出所负责人2", required = false) @RequestParam(value = "leader2", required = false) String leader2,
                             @ApiParam(value = "派出所负责人职务2", required = false) @RequestParam(value = "leaderPosition2", required = false) String leaderPosition2,
                             @ApiParam(value = "派出所负责人电话2", required = false) @RequestParam(value = "stationPhone2", required = false) String stationPhone2,
                             @ApiParam(value = "项目业主单位2", required = false) @RequestParam(value = "ownerCorp2", required = false) String ownerCorp2,
                             @ApiParam(value = "项目业主联系人2", required = false) @RequestParam(value = "ownerContact2", required = false) String ownerContact2,
                             @ApiParam(value = "项目业主联系人职务2", required = false) @RequestParam(value = "ownerPosition2", required = false) String ownerPosition2,
                             @ApiParam(value = "项目业主联系人电话2", required = false) @RequestParam(value = "ownerPhone2", required = false) String ownerPhone2,
                             @ApiParam(value = "施工单位2", required = false) @RequestParam(value = "constructionOrg2", required = false) String constructionOrg2,
                             @ApiParam(value = "施工单位联系人2", required = false) @RequestParam(value = "constructionContact2", required = false) String constructionContact2,
                             @ApiParam(value = "施工单位联系人职务2", required = false) @RequestParam(value = "constructionPosition2", required = false) String constructionPosition2,
                             @ApiParam(value = "施工单位联系人电话2", required = false) @RequestParam(value = "constructionPhone2", required = false) String constructionPhone2,
                             @ApiParam(value = "经度2", required = false) @RequestParam(value = "longitude2", required = false) String longitude2,
                             @ApiParam(value = "纬度2", required = false) @RequestParam(value = "latitude2", required = false) String latitude2,
                             @ApiParam(value = "登录token", required = false) @RequestParam(value = "token", required = false) String token, HttpServletRequest request) {
        logger.info("项目id：" + id);
        logger.info("项目名称：" + name);
        logger.info("项目类别：" + type);
        logger.info("立项时间：" + setTime);
        logger.info("项目总投资金额：" + sumMoney);
        logger.info("已完成金额：" + finishMoney);
        logger.info("项目进度：" + progress);
        logger.info("项目状态：" + projectState);
        logger.info("开工时间：" + openTime);
        logger.info("计划完成时间：" + completedTime);
        logger.info("是否跨区县：" + isSkip);
        logger.info("项目所在县(区)：" + county);
        logger.info("联保联络员(县区)：" + countyPerson);
        logger.info("职务(县区)：" + countyPosition);
        logger.info("联系方式(县区)：" + countyPhone);
        logger.info("项目所在乡镇：" + township);
        logger.info("联保联络员(乡镇)：" + townshipPerson);
        logger.info("职务(乡镇)：" + townshipPosition);
        logger.info("联系方式(乡镇)：" + townshipPhone);
        logger.info("项目所在村：" + village);
        logger.info("联保联络员(村)：" + villagePerson);
        logger.info("职务(村)：" + positions);
        logger.info("联系方式(村)：" + phone);
        logger.info("项目所在组：" + progroup);
        logger.info("联保联络员(组)：" + progroupPerson);
        logger.info("职务(组)：" + progroupPosition);
        logger.info("联系方式(组)：" + progroupPhone);
        logger.info("项目详细地址：" + detailedAddr);
        logger.info("所在辖区派出所：" + policeStation);
        logger.info("派出所负责人：" + leader);
        logger.info("派出所负责人职务：" + leaderPosition);
        logger.info("派出所负责人电话：" + stationPhone);
        logger.info("项目业主单位：" + ownerCorp);
        logger.info("项目业主联系人：" + ownerContact);
        logger.info("项目业主联系人职务：" + ownerPosition);
        logger.info("项目业主联系人电话：" + ownerPhone);
        logger.info("施工单位：" + constructionOrg);
        logger.info("施工单位联系人：" + constructionContact);
        logger.info("施工单位联系人职务：" + constructionPosition);
        logger.info("施工单位联系人电话：" + constructionPhone);
        logger.info("经度：" + longitude);
        logger.info("纬度：" + latitude);
        logger.info("项目简介：" + projectContent);
        logger.info("备注：" + content);

        try {
            //参数验证
            dataValidation.chkLength(name, 0, 50, "项目名称仅支持大小写字母、数字及汉字输入，长度不超过50个字符");

            dataValidation.chkLength(sumMoney, 0, 20, "项目总投资金额长度不符合规则");
            dataValidation.chkLength(finishMoney, 0, 20, "已完成金额长度不符合规则");
            dataValidation.chkLength(countyPosition, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(townshipPosition, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(positions, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(progroupPosition, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(countyPhone, 0, 20, "手机号长度不符合规则");
            dataValidation.chkLength(townshipPhone, 0, 20, "手机号长度不符合规则");
            dataValidation.chkLength(phone, 0, 20, "手机号长度不符合规则");
            dataValidation.chkLength(progroupPhone, 0, 20, "手机号长度不符合规则");
            dataValidation.chkLength(countyPerson, 0, 20, "联保联络员（联保办）长度不符合规则");
            dataValidation.chkLength(townshipPerson, 0, 20, "联保联络员（乡、镇）长度不符合规则");
            dataValidation.chkLength(villagePerson, 0, 20, "联保联络员（村）长度不符合规则");
            dataValidation.chkLength(progroupPerson, 0, 20, "联保联络员（组）长度不符合规则");
            dataValidation.chkLength(detailedAddr, 0, 50, "项目详细地址长度不符合规则");
            dataValidation.chkLength(policeStation, 0, 50, "所在辖区派出所长度不符合规则");
            dataValidation.chkLength(leader, 0, 20, "负责人长度不符合规则");
            dataValidation.chkLength(leaderPosition, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(stationPhone, 0, 20, "手机号长度不符合规则");
            dataValidation.chkLength(ownerCorp, 0, 50, "项目业主单位长度不符合规则");
            dataValidation.chkLength(ownerContact, 0, 20, "项目业主联系人长度不符合规则");
            dataValidation.chkLength(ownerPosition, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(ownerPhone, 0, 20, "手机号长度不符合规则");
            dataValidation.chkLength(constructionOrg, 0, 50, "施工单位长度不符合规则");
            dataValidation.chkLength(constructionContact, 0, 20, "施工单位联系人长度不符合规则");
            dataValidation.chkLength(constructionPosition, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(constructionPhone, 0, 20, "手机号长度不符合规则");

            dataValidation.chkLength(countyPosition2, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(townshipPosition2, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(positions2, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(progroupPosition2, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(countyPhone2, 0, 20, "手机号长度不符合规则");
            dataValidation.chkLength(townshipPhone2, 0, 20, "手机号长度不符合规则");
            dataValidation.chkLength(phone2, 0, 20, "手机号长度不符合规则");
            dataValidation.chkLength(progroupPhone2, 0, 20, "手机号长度不符合规则");
            dataValidation.chkLength(countyPerson2, 0, 20, "联保联络员（联保办）长度不符合规则");
            dataValidation.chkLength(townshipPerson2, 0, 20, "联保联络员（乡、镇）长度不符合规则");
            dataValidation.chkLength(villagePerson2, 0, 20, "联保联络员（村）长度不符合规则");
            dataValidation.chkLength(progroupPerson2, 0, 20, "联保联络员（组）长度不符合规则");
            dataValidation.chkLength(detailedAddr2, 0, 50, "项目详细地址长度不符合规则");
            dataValidation.chkLength(policeStation2, 0, 50, "所在辖区派出所长度不符合规则");
            dataValidation.chkLength(leader2, 0, 20, "负责人长度不符合规则");
            dataValidation.chkLength(leaderPosition2, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(stationPhone2, 0, 20, "手机号长度不符合规则");
            dataValidation.chkLength(ownerCorp2, 0, 50, "项目业主单位长度不符合规则");
            dataValidation.chkLength(ownerContact2, 0, 20, "项目业主联系人长度不符合规则");
            dataValidation.chkLength(ownerPosition2, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(ownerPhone2, 0, 20, "手机号长度不符合规则");
            dataValidation.chkLength(constructionOrg2, 0, 50, "施工单位长度不符合规则");
            dataValidation.chkLength(constructionContact2, 0, 20, "施工单位联系人长度不符合规则");
            dataValidation.chkLength(constructionPosition2, 0, 20, "职务长度不符合规则");
            dataValidation.chkLength(constructionPhone2, 0, 20, "手机号长度不符合规则");
            dataValidation.chkLength(projectContent, 0, 1000, "项目简介长度不符合规则");
            dataValidation.chkLength(content, 0, 200, "备注长度不符合规则");

            //获取当前url
            String url = request.getScheme() + "//" + request.getServerName() + ":" + request.getServerPort() + "/";

            /**组装联保项目基本情况model*/
            ProjectDaseCaseModel pdcm = new ProjectDaseCaseModel();
            pdcm.setId(Integer.parseInt(id));
            pdcm.setName(name);
            pdcm.setType(Integer.parseInt(type));
            pdcm.setSetTime(setTime);
            pdcm.setSumMoney(Double.parseDouble(sumMoney));
            pdcm.setFinishMoney(Double.parseDouble(finishMoney));

            NumberFormat nbf= NumberFormat.getInstance();
            nbf.setMinimumFractionDigits(2);
            String c = nbf.format(pdcm.getFinishMoney() /pdcm.getSumMoney());
            pdcm.setProgress(c);

            pdcm.setProjectState(projectState);
            pdcm.setOpenTime(openTime);
            pdcm.setCompletedTime(completedTime);
            pdcm.setIsSkip(isSkip);
            pdcm.setProjectContent(projectContent);
            pdcm.setContent(content);
            pdcm.setState(ConstDefine.STATE_ABLE);
            pdcm.setEditTime(Config.DATE_FORMAT.format(new Date()));

            //4、获取预警员json并将其转换为Model
            if (!StringUtils.isBlank(warnUser)) {
                List<SysUserModel> lists = JSON.parseArray(warnUser, SysUserModel.class);

                List<SysUserModel> users = new ArrayList<SysUserModel>();
                for (int i = 0; i < lists.size(); i++) {
                    SysUserModel model = lists.get(i);
                    model.setPassWord(Utils.getMd5DigestAsHex(model.getPhone().substring(model.getPhone().length()-6)));//默认电话号码后6位
                    //判断用户是否存在
                    SysUserModel oldModel = systemService.findByUsername(model.getUserName());
                    if (oldModel != null) {
                        return new JsonResponseData(false, "", StatusDefine.U_EXIST_USER,
                                StatusDefineMessage.GetMessage(StatusDefine.U_EXIST_USER), null).toString();
                    }

                    systemService.insertObject(model);
                    users.add(model);
                }
                pdcm.setUserModels(users);
            }
            /**子表List组装*/
            List<ProjectAddrInfoModel> list = new ArrayList<>();

            /**组装联保项目地址信息model*/
            ProjectAddrInfoModel paim = new ProjectAddrInfoModel();
            paim.setId(Integer.parseInt(addrid));
            paim.setCounty(county);
            paim.setCountyPerson(countyPerson);
            paim.setCountyPosition(countyPosition);
            paim.setCountyPhone(countyPhone);
            paim.setTownship(township);
            paim.setTownshipPerson(townshipPerson);
            paim.setTownshipPosition(townshipPosition);
            paim.setTownshipPhone(townshipPhone);
            paim.setVillage(village);
            paim.setVillagePerson(villagePerson);
            paim.setPositions(positions);
            paim.setPhone(phone);
            paim.setProgroup(progroup);
            paim.setProgroupPerson(progroupPerson);
            paim.setProgroupPosition(progroupPosition);
            paim.setProgroupPhone(progroupPhone);
            paim.setDetailedAddr(detailedAddr);
            paim.setPoliceStation(policeStation);
            paim.setLeader(leader);
            paim.setLeaderPosition(leaderPosition);
            paim.setStationPhone(stationPhone);
            paim.setOwnerCorp(ownerCorp);
            paim.setOwnerContact(ownerContact);
            paim.setOwnerPosition(ownerPosition);
            paim.setOwnerPhone(ownerPhone);
            paim.setConstructionOrg(constructionOrg);
            paim.setConstructionContact(constructionContact);
            paim.setConstructionPosition(constructionPosition);
            paim.setConstructionPhone(constructionPhone);
            paim.setLongitude(longitude);
            paim.setLatitude(latitude);
            paim.setState(ConstDefine.STATE_ABLE);
            paim.setEditTime(Config.DATE_FORMAT.format(new Date()));
            list.add(paim);

            /**如果跨区县*/
            if (isSkip == 1) {
                ProjectAddrInfoModel paim2 = new ProjectAddrInfoModel();
                if (!StringUtils.isBlank(addrid2)) {
                    paim2.setId(Integer.parseInt(addrid2));
                }
                paim2.setCounty(county2);
                paim2.setCountyPerson(countyPerson2);
                paim2.setCountyPosition(countyPosition2);
                paim2.setCountyPhone(countyPhone2);
                paim2.setTownship(township2);
                paim2.setTownshipPerson(townshipPerson2);
                paim2.setTownshipPosition(townshipPosition2);
                paim2.setTownshipPhone(townshipPhone2);
                paim2.setVillage(village2);
                paim2.setVillagePerson(villagePerson2);
                paim2.setPositions(positions2);
                paim2.setPhone(phone2);
                paim2.setProgroup(progroup2);
                paim2.setProgroupPerson(progroupPerson2);
                paim2.setProgroupPosition(progroupPosition2);
                paim2.setProgroupPhone(progroupPhone2);
                paim2.setDetailedAddr(detailedAddr2);
                paim2.setPoliceStation(policeStation2);
                paim2.setLeader(leader2);
                paim2.setLeaderPosition(leaderPosition2);
                paim2.setStationPhone(stationPhone2);
                paim2.setOwnerCorp(ownerCorp2);
                paim2.setOwnerContact(ownerContact2);
                paim2.setOwnerPosition(ownerPosition2);
                paim2.setOwnerPhone(ownerPhone2);
                paim2.setConstructionOrg(constructionOrg2);
                paim2.setConstructionContact(constructionContact2);
                paim2.setConstructionPosition(constructionPosition2);
                paim2.setConstructionPhone(constructionPhone2);
                paim2.setLongitude(longitude2);
                paim2.setLatitude(latitude2);
                paim2.setState(ConstDefine.STATE_ABLE);
                paim2.setEditTime(Config.DATE_FORMAT.format(new Date()));
                list.add(paim2);
            }

            int rel = projectDaseCaseService.updProject(pdcm, list, url);
            if (rel > 0) {
                return new JsonResponseData(true,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS,
                        "编辑成功", "id:" + rel).toString();
            } else {
                return new JsonResponseData(false,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), StatusDefine.FAILURE,
                        "编辑失败", "").toString();
            }
        } catch (ProjectException e) {
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:updProject..msg:编辑联保项目基本情况信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    StatusDefine.SYS_ERROR, e.getMessage(), null).toString();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:updProject..msg:编辑联保项目基本情况信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "数据类型错误", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        } catch (DBErrorException e) {
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:updProject..msg:编辑联保项目基本情况信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), StatusDefine.DB_ERROR, "",
                    null).toString();
        }catch (DataValidationException e){
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:updProject..msg:编辑联保项目基本情况信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), StatusDefine.DB_ERROR, "",
                    null).toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:updProject..msg:编辑联保项目基本情况信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }

    }

    /**
     * 删除联保项目基本情况信息
     * HTTP 方式：DELETE
     * API 路径：/api/projectdasecase
     * 方法名：delProject
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @ApiOperation(value = "删除联保项目基本情况信息", httpMethod = "DELETE", response = String.class,
            notes = "删除联保项目基本情况信息，供后台删除联保项目基本情况信息使用")
    @SystemControllerLog(descrption = "删除联保项目基本情况信息", actionType = "2", modules = "项目基本情况信息模块")
    @RequiresPermissions("XMXQSC")
    @RequestMapping(value = "/api/projectdasecase", method = RequestMethod.DELETE)
    @Transactional
    public String delProject(@ApiParam(value = "项目id集合用“,”隔开", required = true) @RequestParam(value = "ids", required = true) String ids,
                             @ApiParam(value = "登录token", required = false) @RequestParam(value = "token", required = false) String token) {
        logger.info("项目id：" + ids);
        try {
            int rel = 0;
            for (String id : ids.split(",")) {
                ProjectDaseCaseModel pdcm = new ProjectDaseCaseModel();
                pdcm.setId(Integer.parseInt(id));
                pdcm.setState(StatusDefine.FAILURE);

                ProjectAddrInfoModel paim2 = new ProjectAddrInfoModel();
                paim2.setProjectid(Integer.parseInt(id));
                paim2.setState(StatusDefine.FAILURE);

                rel += projectDaseCaseService.delProject(pdcm, paim2);
            }

            //判断返回值
            if (rel > 0) {
                return new JsonResponseData(true,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS,
                        "删除成功", "id:" + rel).toString();
            } else {
                return new JsonResponseData(false,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), StatusDefine.FAILURE,
                        "删除失败", "").toString();
            }
        } catch (ProjectException e) {
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:delProject..msg:删除联保项目基本情况信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    StatusDefine.SYS_ERROR, e.getMessage(), null).toString();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:delProject..msg:删除联保项目基本情况信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "数据类型错误", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        } catch (DBErrorException e) {
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:delProject..msg:删除联保项目基本情况信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), StatusDefine.DB_ERROR, "",
                    null).toString();
        } catch (Exception e) {
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:delProject..msg:删除联保项目基本情况信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }

    }

    /**
     * 联保项目基本详情查询
     * HTTP 方式：GET
     * API 路径：/api/projectdasecase/{id}
     * 方法名：getProject
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @ApiOperation(value = "联保项目基本详情查询", httpMethod = "GET", response = String.class,
            notes = "联保项目基本详情查询，供后台联保项目基本情况详情查看信息使用")
    @SystemControllerLog(descrption = "联保项目基本详情查询", actionType = "4", modules = "项目基本情况信息模块")
    @RequiresPermissions("XMXQCK")
    @RequestMapping(value = "/api/projectdasecase/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getProject(@ApiParam(value = "项目id", required = true) @RequestParam(value = "id", required = true) String id) {
        logger.info("项目id：" + id);
        try {
            ProjectDaseCaseModel model = projectDaseCaseService.getProject(Integer.parseInt(id));
            return new JsonResponseData(true, "成功", StatusDefine.SUCCESS,
                    StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), model).toString();
        } catch (ProjectException e) {
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:getProject..msg:联保项目基本详情查询出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    StatusDefine.SYS_ERROR, e.getMessage(), null).toString();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:getProject..msg:联保项目基本详情查询出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "数据类型错误", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        } catch (DBErrorException e) {
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:getProject..msg:联保项目基本详情查询出现异常. error:" + e.getMessage());
            return new JsonResponseData(false,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), StatusDefine.DB_ERROR, "",
                    null).toString();
        } catch (Exception e) {
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:getProject..msg:联保项目基本详情查询出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }

    /**
     * 分页查询联保基础信息
     * HTTP 方式：GET
     * API 路径：/api/projectdasecase/pageIndex/{pageIndex}/pageNum/{pageNum}
     * 方法名：queryProjectdasecaseByPage
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @ApiOperation(value = "分页查询联保基础信息", httpMethod = "GET", response = String.class, notes = "项目所在地区，项目类别、项目状态、开工时间时间等信息分页查询联保信息数据")
    @SystemControllerLog(descrption = "分页查询联保基础信息", actionType = "4", modules = "项目基本情况信息模块")
    @RequiresPermissions("XMJBQK")
    @RequestMapping(value = "/api/projectdasecase/pageIndex/{pageIndex}/pageNum/{pageNum}", method = RequestMethod.GET, produces = "application/json")
    public String queryProjectdasecaseByPage(@ApiParam(value = "项目所在区县", required = false) @RequestParam(value = "county", required = false) String county,
                                             @ApiParam(value = "项目所在乡镇", required = false) @RequestParam(value = "township", required = false) String township,
                                             @ApiParam(value = "项目所在村", required = false) @RequestParam(value = "village", required = false) String village,
                                             @ApiParam(value = "项目类别", required = false) @RequestParam(value = "type", required = false) String type,
                                             @ApiParam(value = "项目状态(可以选多个,用“,”隔开)", required = false)
                                             @RequestParam(value = "projectState", required = false) String projectState,
                                             @ApiParam(value = "是否跨县", required = false) @RequestParam(value = "isSkip", required = false) String isSkip,
                                             @ApiParam(value = "是否超时", required = false) @RequestParam(value = "isOverTime", required = false) String isOverTime,
                                             @ApiParam(value = "开工时间", required = false) @RequestParam(value = "caseTimeStart", required = false) String caseTimeStart,
                                             @ApiParam(value = "截止时间", required = false) @RequestParam(value = "caseTimeEnd", required = false) String caseTimeEnd,
                                             @ApiParam(value = "页索引", required = true) @PathVariable(value = "pageIndex", required = true) String pageIndex,
                                             @ApiParam(value = "页大小", required = true) @PathVariable(value = "pageNum", required = true) String pageNum,
                                             @ApiParam(value = "token", required = false) @RequestParam(value = "token", required = false) String token) {
        try {
            logger.info("项目所在区县：" + county);
            logger.debug("项目所在乡镇" + township);
            logger.debug("项目所在村" + village);
            logger.debug("项目类别" + type);
            logger.debug("项目状态" + projectState);
            logger.debug("是否跨县" + isSkip);
            logger.debug("是否超时" + isOverTime);
            logger.debug("开工时间" + caseTimeStart);
            logger.debug("截止时间" + caseTimeEnd);
            logger.debug("页索引" + pageIndex);
            logger.debug("页大小" + pageNum);


            Page page = new Page();
            if (!StringUtils.isBlank(pageIndex)) {
                page.setCurrentPage(Long.parseLong(pageIndex.toString()));
            }
            if (!StringUtils.isBlank(pageNum)) {
                page.setEveryPage(Long.parseLong(pageNum.toString()));
            }

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("state", ConstDefine.STATE_ABLE);
            paramMap.put("beginIndex", page.getBeginIndex());
            paramMap.put("endIndex", page.getEndinIndex());
            paramMap.put("county", county);
            paramMap.put("township", township);
            paramMap.put("village", village);
            if (!StringUtils.isBlank(type)) {
                paramMap.put("type", Integer.parseInt(type));
            }
            paramMap.put("projectState", projectState);
            paramMap.put("isSkip", isSkip);
            paramMap.put("isOverTime", isOverTime);
            paramMap.put("caseTimeStart", caseTimeStart);
            paramMap.put("caseTimeEnd", caseTimeEnd);

            //获取当前用户角色
            SysUserModel sysUserModel = (SysUserModel) SecurityUtils.getSubject().getPrincipal();
            List<SysRoleModel> role = sysUserModel.getSysRoles();
            for (int i = 0; i < role.size(); i++) {
                //判断是否是预警员
                if (role.get(i).getName().equals("预警员")) {
                    paramMap.put("userId", ((SysUserModel) SecurityUtils.getSubject().getPrincipal()).getId().toString());
                } else {
                    paramMap.put("userId", null);
                }
            }
            //分页操作
            List<ProjectDaseCaseModel> retList = projectDaseCaseService.queryProjectdasecaseByPage(paramMap, page);
            page.setList(retList);
            return new JsonResponseData(true, StatusDefineMessage.GetMessage(StatusDefine.SUCCESS),
                    StatusDefine.SUCCESS, "操作成功", page).toString();
        } catch (ProjectException e) {
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:queryProjectdasecaseByPage..msg:联保项目分页查询出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    StatusDefine.SYS_ERROR, e.getMessage(), null).toString();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:queryProjectdasecaseByPage..msg:联保项目分页查询出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "数据类型错误", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        } catch (DBErrorException e) {
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:queryProjectdasecaseByPage..msg:联保项目分页查询出现异常. error:" + e.getMessage());
            return new JsonResponseData(false,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), StatusDefine.DB_ERROR, "",
                    null).toString();
        } catch (Exception e) {
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:queryProjectdasecaseByPage..msg:联保项目分页查询出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }

    }


    /**
     * APP客户端分页查询联保基础信息
     * HTTP 方式：GET
     * API 路径：/api/projectdasecaseapp/pageIndex/{pageIndex}/pageNum/{pageNum}
     * 方法名：queryProjectdasecaseAppByPage
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @ApiOperation(value = "APP客户端分页查询联保基础信息", httpMethod = "GET", response = String.class, notes = "项目所在地区，项目类别、项目状态、开工时间时间等信息分页查询联保信息数据")
    @SystemControllerLog(descrption = "APP客户端分页查询联保基础信息", actionType = "4", modules = "APP客户端分页查询联保基础信息")
    @RequestMapping(value = "/api/projectdasecaseapp/pageIndex/{pageIndex}/pageNum/{pageNum}", method = RequestMethod.GET, produces = "application/json")
    public String queryProjectdasecaseAppByPage(@ApiParam(value = "项目所在区县", required = false) @RequestParam(value = "county", required = false) String county,
                                                @ApiParam(value = "项目所在乡镇", required = false) @RequestParam(value = "township", required = false) String township,
                                                @ApiParam(value = "项目所在村", required = false) @RequestParam(value = "village", required = false) String village,
                                                @ApiParam(value = "项目类别(可以选多个,用“,”隔开)", required = false)
                                                @RequestParam(value = "type", required = false) String type,
                                                @ApiParam(value = "项目状态(可以选多个,用“,”隔开)", required = false)
                                                @RequestParam(value = "projectState", required = false) String projectState,
                                                @ApiParam(value = "项目名称", required = false) @RequestParam(value = "name", required = false) String name,
                                                @ApiParam(value = "页索引", required = true) @PathVariable(value = "pageIndex", required = true) String pageIndex,
                                                @ApiParam(value = "页大小", required = true) @PathVariable(value = "pageNum", required = true) String pageNum,
                                                @ApiParam(value = "token", required = false) @RequestParam(value = "token", required = false) String token) {
        try {
            logger.info("项目所在区县：" + county);
            logger.debug("项目所在乡镇" + township);
            logger.debug("项目所在村" + village);
            logger.debug("项目类别" + type);
            logger.debug("项目状态" + projectState);
            logger.debug("项目名称" + name);
            logger.debug("页索引" + pageIndex);
            logger.debug("页大小" + pageNum);


            Page page = new Page();
            if (!StringUtils.isBlank(pageIndex)) {
                page.setCurrentPage(Long.parseLong(pageIndex.toString()));
            }
            if (!StringUtils.isBlank(pageNum)) {
                page.setEveryPage(Long.parseLong(pageNum.toString()));
            }

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("state", ConstDefine.STATE_ABLE);
            paramMap.put("beginIndex", page.getBeginIndex());
            paramMap.put("endIndex", page.getEndinIndex());
            paramMap.put("county", county);
            paramMap.put("township", township);
            paramMap.put("village", village);
            paramMap.put("type", type);
            paramMap.put("projectState", projectState);
            paramMap.put("name", name);


            //获取当前用户角色
            SysUserModel sysUserModel = (SysUserModel) SecurityUtils.getSubject().getPrincipal();
            List<SysRoleModel> role = sysUserModel.getSysRoles();
            for (int i = 0; i < role.size(); i++) {
                //判断是否是预警员
                if (role.get(i).getName().equals("预警员")) {
                    paramMap.put("userId", ((SysUserModel) SecurityUtils.getSubject().getPrincipal()).getId().toString());
                } else {
                    paramMap.put("userId", null);
                }
            }
            //分页操作
            List<ProjectDaseCaseModel> retList = projectDaseCaseService.queryProjectdasecaseAppByPage(paramMap, page);
            page.setList(retList);
            return new JsonResponseData(true, StatusDefineMessage.GetMessage(StatusDefine.SUCCESS),
                    StatusDefine.SUCCESS, "操作成功", page).toString();
        } catch (ProjectException e) {
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:queryProjectdasecaseByPage..msg:联保项目分页查询出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    StatusDefine.SYS_ERROR, e.getMessage(), null).toString();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:queryProjectdasecaseByPage..msg:联保项目分页查询出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "数据类型错误", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        } catch (DBErrorException e) {
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:queryProjectdasecaseByPage..msg:联保项目分页查询出现异常. error:" + e.getMessage());
            return new JsonResponseData(false,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), StatusDefine.DB_ERROR, "",
                    null).toString();
        } catch (Exception e) {
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:queryProjectdasecaseByPage..msg:联保项目分页查询出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }

    }


    /**
     * 分页查询联保基础信息导出
     * HTTP 方式：GET
     * API 路径：/api/projectdasecaseInfo
     * 方法名：queryProjectdasecaseExcel
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @ApiOperation(value = "分页查询联保基础信息导出", httpMethod = "GET", response = String.class, notes = "项目所在地区，项目类别、项目状态、导出Excel")
    @SystemControllerLog(descrption = "分页查询联保基础信息导出", actionType = "4", modules = "分页查询联保基础信息导出")
    @RequestMapping(value = "/api/projectdasecaseInfo", method = RequestMethod.GET)
    public String queryProjectdasecaseExcel(@ApiParam(value = "项目所在区县", required = false) @RequestParam(value = "county", required = false) String county,
                                            @ApiParam(value = "项目所在乡镇", required = false) @RequestParam(value = "township", required = false) String township,
                                            @ApiParam(value = "项目所在村", required = false) @RequestParam(value = "village", required = false) String village,
                                            @ApiParam(value = "项目类别", required = false) @RequestParam(value = "type", required = false) String type,
                                            @ApiParam(value = "项目状态(可以选多个,用“,”隔开)", required = false)
                                            @RequestParam(value = "projectState", required = false) String projectState,
                                            @ApiParam(value = "是否跨县", required = false) @RequestParam(value = "isSkip", required = false) String isSkip,
                                            @ApiParam(value = "是否超时", required = false) @RequestParam(value = "isOverTime", required = false) String isOverTime,
                                            @ApiParam(value = "开工时间", required = false) @RequestParam(value = "caseTimeStart", required = false) String caseTimeStart,
                                            @ApiParam(value = "截止时间", required = false) @RequestParam(value = "caseTimeEnd", required = false) String caseTimeEnd,
                                            HttpServletResponse response, HttpServletRequest request,
                                            @ApiParam(value = "token", required = false) @RequestParam(value = "token", required = false) String token) {
        try {
            logger.info("项目所在区县：" + county);
            logger.debug("项目所在乡镇" + township);
            logger.debug("项目所在村" + village);
            logger.debug("项目类别" + type);
            logger.debug("项目状态" + projectState);
            logger.debug("是否跨县" + isSkip);
            logger.debug("是否超时" + isOverTime);
            logger.debug("开工时间" + caseTimeStart);
            logger.debug("截止时间" + caseTimeEnd);

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("state", ConstDefine.STATE_ABLE);
            paramMap.put("county", county);
            paramMap.put("township", township);
            paramMap.put("village", village);
            if (!StringUtils.isBlank(type)) {
                paramMap.put("type", Integer.parseInt(type));
            }
            paramMap.put("projectState", projectState);
            paramMap.put("isSkip", isSkip);
            paramMap.put("isOverTime", isOverTime);
            paramMap.put("caseTimeStart", caseTimeStart);
            paramMap.put("caseTimeEnd", caseTimeEnd);

            //分页操作

            String retList = projectDaseCaseService.queryProjectdasecaseExcel(paramMap, response, request);
            return new JsonResponseData(true, StatusDefineMessage.GetMessage(StatusDefine.SUCCESS),
                    StatusDefine.SUCCESS, "操作成功", retList).toString();
        } catch (ProjectException e) {
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:queryProjectdasecaseExcel..msg:联保项目分页查询出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    StatusDefine.SYS_ERROR, e.getMessage(), null).toString();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:queryProjectdasecaseExcel..msg:联保项目分页查询出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "数据类型错误", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        } catch (DBErrorException e) {
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:queryProjectdasecaseExcel..msg:联保项目分页查询出现异常. error:" + e.getMessage());
            return new JsonResponseData(false,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), StatusDefine.DB_ERROR, "",
                    null).toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:queryProjectdasecaseExcel..msg:联保项目分页查询出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }

    }

    /**
     * 联保项目列表查询
     * HTTP 方式：GET
     * API 路径：/api/projectdasecaseList
     * 方法名：getProjectList
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @ApiOperation(value = "联保项目列表查询", httpMethod = "GET", response = String.class,
            notes = "联保项目项目列表，供后台联保项目列表查看信息使用")
    @SystemControllerLog(descrption = "联保项目列表查询", actionType = "4", modules = "项目基本情况信息模块")
    @RequestMapping(value = "/api/projectdasecaseList", method = RequestMethod.GET)
    public String getProjectList(@ApiParam(value = "项目所在区县code", required = false) @RequestParam(value = "county", required = false) String county,
                                 @ApiParam(value = "项目所在乡镇code", required = false) @RequestParam(value = "township", required = false) String township,
                                 @ApiParam(value = "项目所在村code", required = false) @RequestParam(value = "village", required = false) String village,
                                 @ApiParam(value = "token", required = false) @RequestParam(value = "token", required = false) String token
    ) {
        logger.info("项目所在区县" + county);
        logger.info("项目所在乡镇", township);
        logger.info("项目所在村", village);
        try {
            //获取当前用户角色
            Map<String, Object> map = new HashMap<>();
            map.put("county", county);
            map.put("township", township);
            map.put("village", village);
            SysUserModel sysUserModel = (SysUserModel) SecurityUtils.getSubject().getPrincipal();
            List<SysRoleModel> role = sysUserModel.getSysRoles();
            for (int i = 0; i < role.size(); i++) {
                //判断是否是预警员
                if (role.get(i).getName().equals("预警员")) {
                    map.put("userId", ((SysUserModel) SecurityUtils.getSubject().getPrincipal()).getId().toString());
                } else {
                    map.put("userId", null);
                }
            }
            List<ProjectDaseCaseModel> model = projectDaseCaseService.getProjectList(map);
            return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                    StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), model).toString();
        } catch (ProjectException e) {
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:getProjectList..msg:联保项目列表查询出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    StatusDefine.SYS_ERROR, e.getMessage(), null).toString();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:getProjectList..msg:联保项目列表查询出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "数据类型错误", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        } catch (DBErrorException e) {
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:getProjectList..msg:联保项目列表出现异常. error:" + e.getMessage());
            return new JsonResponseData(false,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), StatusDefine.DB_ERROR, "",
                    null).toString();
        } catch (Exception e) {
            logger.error("controller:ProjectDaseCaseController.");
            logger.error("function:getProjectList..msg:联保项目列表出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }


}

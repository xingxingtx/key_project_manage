package com.aorise.utils;


import com.aorise.exceptions.SendMsgExeption;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:Shenzhiwei
 * @Desicription:消息推送
 * @Date:Created in 2018-05-28 11:29
 * @Modified By:
 */
@Component
public class PushMessage {
    /**
     * 个推
     */
    @Value ("${getui.appId}")
    private   String appId;
    @Value ("${getui.appKey}")
    private   String appKey ;
    @Value ("${getui.masterSecret}")
    private  String masterSecret;
    @Value ("${getui.url}")
    private  String url;

    public IPushResult PushMessage(String clientIds, String content) throws SendMsgExeption {
        try {
            // 配置返回每个用户返回用户状态，可选
            System.setProperty("gexin_pushList_needDetails", "true");
            // 配置返回每个别名及其对应cid的用户状态，可选
            // System.setProperty("gexin_pushList_needAliasDetails", "true");
            IGtPush push = new IGtPush(url, appKey, masterSecret);
            // 通知透传模板
            TransmissionTemplate template = getTemplate(content);
            ListMessage message = new ListMessage();
            message.setData(template);
            // 设置消息离线，并设置离线时间
            message.setOffline(true);
            // 离线有效时间，单位为毫秒，可选
            message.setOfflineExpireTime(24 * 1000 * 3600);
            // 配置推送目标
            List targets = new ArrayList();
            String[] clientId = clientIds.split(",");
            for (int i = 0; i < clientId.length; i++) {
                Target target = new Target();
                target.setAppId(appId);
                target.setAlias(clientId[i]);
                targets.add(target);
            }
            // taskId用于在推送时去查找对应的message
            String taskId = push.getContentId(message);
            IPushResult ret = push.pushMessageToList(taskId, targets);
            return ret;
        }catch (Exception e)
        {
            throw new SendMsgExeption("短信发送失败");
        }
    }


    /**
     * 透传消息模板
     *
     * @param content
     * @return
     */
    public  TransmissionTemplate getTemplate(String content) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTransmissionContent(content);
        template.setTransmissionType(2);
        APNPayload payload = new APNPayload();
        //在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
        payload.setAutoBadge("+1");
        //payload.setContentAvailable(1);
        payload.setSound("default");
        payload.setCategory("$由客户端定义");

        //简单模式APNPayload.SimpleMsg
        payload.setAlertMsg(new APNPayload.SimpleAlertMsg(content));

        template.setAPNInfo(payload);
        return template;
    }
}

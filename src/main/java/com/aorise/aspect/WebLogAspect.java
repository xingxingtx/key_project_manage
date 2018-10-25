package com.aorise.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aorise.annotation.SystemControllerLog;
import com.aorise.exceptions.LoginOvertimeException;
import com.aorise.model.system.SysUserModel;
import com.aorise.model.systemlog.SystemLogModel;
import com.aorise.service.systemlog.SystemLogService;
import com.aorise.utils.json.JsonResponseData;
import com.google.gson.GsonBuilder;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

@Aspect
@Component
public class WebLogAspect {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SystemLogService systemLogService;

    /**
     * 定义切入点，表示拦截com.aorise.springboot.controller包下的 public 任意返回类型 方法
     */
    @Pointcut("@annotation(com.aorise.annotation.SystemControllerLog)")
    public void weblog(){}

    /***
     * 方法执行前拦截
     * @param joinPoint
     * @throws Throwable
     */
    @Around("weblog()")
    public String doBefore(ProceedingJoinPoint joinPoint)throws Throwable {
        Object o = joinPoint.getTarget();
        Object proceed = null;
        proceed = joinPoint.proceed();
        //提取controller中ExecutionResult的属性
        String result = (String) proceed;
        JsonResponseData jsonResponseData = JSONObject.toJavaObject(JSON.parseObject(result), JsonResponseData.class);

        //得到方法
        Method[] methods = o.getClass().getDeclaredMethods();

                //系统日志实体
                SystemLogModel systemLog = new SystemLogModel();
                try {
                    String fullname = ((SysUserModel) SecurityUtils.getSubject().getPrincipal()).getFullName();
                    systemLog.setUserid(fullname);
                }catch (Exception e){
                    throw new LoginOvertimeException("登录超时");
                }
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = attributes.getRequest();
                logger.info("URL:" + request.getRequestURL().toString());
                systemLog.setRequestapi(request.getRequestURL().toString());
                logger.info("Method:" + request.getMethod());
                systemLog.setHttptype(request.getMethod());
                logger.info("remoteAddr:" + request.getRemoteAddr());
                systemLog.setIp(request.getRemoteAddr());
                //获取执行的方法名
                logger.info("actionmethod:" + joinPoint.getSignature().getName());
                systemLog.setActionmethod(joinPoint.getSignature().getName());
                //获取方法执行前时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                systemLog.setActiondate(df.format(new Date()));
                Enumeration<String> enu = request.getParameterNames();
                StringBuilder re = new StringBuilder();
                while (enu.hasMoreElements()) {
                    String key = (String) enu.nextElement();
                    re.append(key+":");
                    String value = request.getParameter(key);
                    re.append(value);
                    re.append("  ");
                    logger.info("key:{},value:{}", key, value);
                }

                if (jsonResponseData.getRet() == true) {
                    //设置操作信息
                    systemLog.setType("成功");
                    //获取执行方法的注解内容
                    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
                    Method methodw = signature.getMethod();
                    Annotation annotation = methodw.getAnnotation(SystemControllerLog.class);
                    logger.info(((SystemControllerLog) annotation).descrption());
                    //systemLog.setDescription(((SystemControllerLog) annotation).descrption());
                    systemLog.setDescription(((SystemControllerLog) annotation).descrption() + ":" + jsonResponseData.getMsg());
                    systemLog.setModules(((SystemControllerLog) annotation).modules());
                } else {
                    systemLog.setType("失败");
                    //获取执行方法的注解内容
                    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
                    Method methodw = signature.getMethod();
                    Annotation annotation = methodw.getAnnotation(SystemControllerLog.class);
                    systemLog.setDescription(((SystemControllerLog) annotation).descrption() + ":" + jsonResponseData.getMsg());
                    systemLog.setModules(((SystemControllerLog) annotation).modules());
                    systemLog.setExceptioncode(jsonResponseData.getMsg());
                }
                systemLog.setParams(re.toString());
                systemLog.setReturndate(df.format(new Date()));
                systemLog.setReturndata(new GsonBuilder().create().toJson(jsonResponseData.getData()));
                systemLogService.saveUser(systemLog);

        return result;
    }
    /****
     * 处理完请求执行
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret" ,pointcut = "weblog()")
    public void doAfterReturn(Object ret) throws Throwable{

        logger.info("response:"+ret);
    }

    /***
     * 获取controller的操作信息
     * @param point
     * @return
     */
    public String getControllerMethodDescription(ProceedingJoinPoint point) throws  Exception{
        //获取连接点目标类名
        String targetName = point.getTarget().getClass().getName() ;
        //获取连接点签名的方法名
        String methodName = point.getSignature().getName() ;
        //获取连接点参数
        Object[] args = point.getArgs() ;
        //根据连接点类的名字获取指定类
        Class targetClass = Class.forName(targetName);
        //获取类里面的方法
        Method[] methods = targetClass.getMethods() ;
        String description="" ;
        for (Method method : methods) {
            if (method.getName().equals(methodName)){
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == args.length){
                    description = method.getAnnotation(SystemControllerLog.class).descrption();
                    break;
                }
            }
        }
        return description ;
    }

}
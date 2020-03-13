package server.user.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import server.commom.utils.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author tieMinPan
 * @Desc: Description
 * @date 2019-07-08 18:22
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {
    /**
     * 请求开始时间
     */
    private Long startTime = 0L;

    //切入点描述 这个是controller包的切入点
    @Pointcut("execution(public * server.user.controller..*.*(..))")
    /**
     * 签名，可以理解成这个切入点的一个名称
     */
    public void webLogAspect() {
    }


    /**
     * 在切入点的方法run之前执行
     *
     * @param joinPoint
     */
    @Before("webLogAspect()")
    public void logBeforeController(JoinPoint joinPoint) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        startTime = System.currentTimeMillis();
        // 标识信息
        MDC.put("reqUrl", request.getRequestURI());
        MDC.put("reqId", UUID.randomUUID().toString());
        log.info("请求开始时间:{}", startTime);
        log.info("请求URL:{}", request.getRequestURL().toString());
        log.info("请求CLASS_METHOD:{}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("请求方法:{}", request.getMethod());
        log.info("请求接受参数:{}", Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 返回时执行
     *
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = "webLogAspect()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容
        log.info("请求返回内容:{}", JsonUtil.objToString(ret));
        log.info("请求结束时间:{}", System.currentTimeMillis());
        log.info("请求总耗时:{}毫秒", System.currentTimeMillis() - startTime);

    }
}

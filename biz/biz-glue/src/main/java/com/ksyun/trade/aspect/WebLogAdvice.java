package com.ksyun.trade.aspect;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Maps;
import com.ksyun.common.util.TraceUtils;
import com.ksyun.common.util.mapper.JacksonMapper;
import com.ksyun.trade.constant.Constant;
import com.ksyun.trade.constant.LoggerName;
import com.ksyun.trade.rest.RestResult;
import com.ksyun.trade.util.UtilAll;
import com.ksyun.trade.util.WebIpUtils;
import com.ksyun.trade.util.date.BetweenFormater;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.UUID;

/**
 * Controller层日志打印.
 *
 * @author ksc
 */
@Component
@Aspect
public class WebLogAdvice {
    public static final String REQUEST_ID = "X-KSC-REQUEST-ID";

    private static final Logger webLogger = LoggerFactory.getLogger(LoggerName.WEB_LOGGER_NAME);

    private static final JacksonMapper jacksonMapper = new JacksonMapper(JsonInclude.Include.NON_NULL)
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS).setDateFormat(Constant.yyyyMMdd);

    @Autowired(required = false)
    private HttpServletRequest request;

    @Around(value = "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object addMonitorLog(final ProceedingJoinPoint pjp) throws Throwable {
        final Stopwatch stopwatch = Stopwatch.createUnstarted().start();
        webLogger.debug("request:{}", request);
        Signature signature = pjp.getSignature();
        String methodName = signature.getName();
        String className = UtilAll.parseShortClassName(signature
                .getDeclaringTypeName());
        String fullName = className + "_" + methodName;

        Map<String, Object> httpParams = getHttpRequestArgParams(pjp);
        Map<String, String> headers = getHeader(request);
        String ip = WebIpUtils.getIpAddr(request);
        webLogger.info("方法:{},请求参数:{},headers:{}, 请求Ip:{}", fullName, httpParams, headers, ip);

        try {
            long start = System.currentTimeMillis();
            Object obj = pjp.proceed();
            if (obj instanceof RestResult) {
                RestResult dto = ((RestResult) obj);
//                dto.setRequestId(TraceUtils.getTraceId());
                if (dto.getRequestId() == null)
                    dto.setRequestId(headers.getOrDefault("X-KSY-REQUEST-ID", UUID.randomUUID().toString()));
                dto.setDescr(StringUtils.defaultString(new BetweenFormater(System.currentTimeMillis() - start).simpleFormat(), dto.getDescr()));
            }
            return obj;
        } catch (final Exception e) {
            webLogger.error("方法:{} 出错! ", fullName, e);
            throw e;
        } finally {
            stopwatch.stop();
            webLogger.info("方法:{} 执行耗时:{} !", fullName, stopwatch);
        }
    }

    private String getArg(Object arg) {
        if (null == arg) {
            return "空参数";
        }

        if (arg instanceof HttpServletRequest || arg instanceof HttpServletResponse || arg instanceof MultipartFile) {
            Class<?> clazz = getClass();
            if (clazz == WebLogAdvice.class) {
                return "";
            }
            return clazz.getName();
        }

        try {
            return jacksonMapper.toJson(arg);
        } catch (Exception e) {
            //ignore ex
            return "";
        }
    }

    private Map<String, String> getHeader(HttpServletRequest request) {
        Map<String, String> headers = Maps.newHashMap();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String header = enumeration.nextElement();
            headers.put(header.toUpperCase(), request.getHeader(header));
        }
        return headers;
    }

    private Map<String, Object> getHttpRequestArgParams(ProceedingJoinPoint pjp) {
        // 拦截的方法参数
        Object[] args = pjp.getArgs();
        Map<String, Object> params = Maps.newLinkedHashMapWithExpectedSize(args.length);

        if (request != null) {
            params.put("url", request.getRequestURL());
            params.put("method", request.getMethod());
//            params.put("content-type", request.getContentType());
        }

        // 循环获得所有参数对象
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            //如果是BindingAwareModelMap这种类型的 直接跳过
            if (arg instanceof BindingAwareModelMap) {
                continue;
            }
            String key = "args[" + i + "]";
            String value = getArg(arg);

            if (StringUtils.isNotBlank(value)) {
                params.put(key, value);
            }
        }

        return params;
    }

}

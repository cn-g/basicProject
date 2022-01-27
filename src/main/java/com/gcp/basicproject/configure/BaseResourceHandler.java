package com.gcp.basicproject.configure;

import com.gcp.basicproject.response.CommonException;
import com.gcp.basicproject.response.ResponseModels;
import com.gcp.basicproject.util.ParamUtil;
import com.gcp.basicproject.util.ToolsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 异常拦截
 * @author Admin
 */
@ControllerAdvice
public class BaseResourceHandler {

    private static final Logger log = LoggerFactory.getLogger(BaseResourceHandler.class);

    private <T> ResponseEntity<T> buildResponse(T t, HttpServletRequest request) {
        return ParamUtil.notEmpty(request.getHeader("FEIGN_ACCESS")) ? new ResponseEntity(t, HttpStatus.INTERNAL_SERVER_ERROR) : ResponseEntity.ok(t);
    }

    @ExceptionHandler({CommonException.class})
    public Object handleBusinessException(HttpServletRequest request, HttpServletResponse response, CommonException e) throws IOException {
        return this.buildResponse(ResponseModels.commonException(e), request);
    }

    @ExceptionHandler
    public Object handleException(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        if (e instanceof MethodArgumentTypeMismatchException) {
            log.info("接口调用失败,url={},ip={},message={}", new Object[]{request.getRequestURL(), ToolsUtil.getServerIp(), e.getMessage()});
            return this.buildResponse(ResponseModels.commonnException().message("请求参数异常").message_en(e.getLocalizedMessage()), request);
        } else if (e instanceof HttpMessageConversionException) {
            log.info("接口调用失败,url={},ip={},message={}", new Object[]{request.getRequestURL(), ToolsUtil.getServerIp(), e.getMessage()});
            return this.buildResponse(ResponseModels.commonnException().message("请求参数异常").message_en(e.getLocalizedMessage()), request);
        } else {
            if (e instanceof IOException && ParamUtil.notEmpty(e.getMessage()) && e.getMessage().contains("Connection reset by peer")) {
                log.debug("接口断开,url={},ip={},message={}", new Object[]{request.getRequestURL(), ToolsUtil.getServerIp(), e.getMessage()});
            } else {
                StackTraceElement[] stackTraceElementArr = e.getStackTrace();
                if (stackTraceElementArr.length > 0) {
                    StackTraceElement stackTraceElement = stackTraceElementArr[0];
                        log.info("接口调用失败,url={},ip={},errorFileName={},errorLine={},errorMethod={}", new Object[]{request.getRequestURL(), ToolsUtil.getServerIp(), stackTraceElement.getFileName(), stackTraceElement.getLineNumber(), stackTraceElement.getMethodName(), e});
                }
            }
            return this.buildResponse(ResponseModels.unkownException(), request);
        }
    }

}

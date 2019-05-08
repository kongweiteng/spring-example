package com.kong.example.boot.config;

import com.kong.example.boot.util.RespEntityUtil;
import com.kong.example.boot.util.StatusCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RestControllerAdvice
public class AllExceptionHandler extends ResponseEntityExceptionHandler {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 自定义异常处理
     *
     * @param e
     * @return
     */
//    @ExceptionHandler(value = EnergyException.class)
//    @ResponseBody
//    public EnergyResp<String> etspExceptionHandler(EnergyException e) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        e.printStackTrace(new PrintStream(baos));
//        String exception = baos.toString();
//        //定义异常信息
//        Integer code = null;
//        String msg = null;
//        String error = null;
//        //code
//        if (e.getCode() != null) {
//            code = e.getCode();
//        } else {
//            code = StatusCode.ERROR.getCode();
//        }
//        //msg
//        if (e.getMsg() != null) {
//            msg = e.getMsg();
//        } else {
//            msg = StatusCode.ERROR.getMsg();
//        }
//        //error
//        if (e.getError() != null) {
//            error = e.getError() + ": " + exception;
//        } else {
//            error = StatusCode.ERROR.getMsg();
//        }
//        logger.error(error);
//        logger.error(exception);
//        EnergyResp<String> etspResp = new EnergyResp<>();
//        if (e.getStackTrace().length > 0) {
//            StackTraceElement stackTraceElement = e.getStackTrace()[0];// 得到异常棧的首个元素
//            error += "Class：" + stackTraceElement.getFileName() + ", Method：" + stackTraceElement.getMethodName() + ", Line：" + stackTraceElement.getLineNumber() + ", error：" + e.getMessage() + ", time：" + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
//        }
//        etspResp.faile(code, msg, error);
//        return etspResp;
//    }


    /**
     * 统一异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public RespEntityUtil<String> runtimeExceptionHandler(Exception e) {
        RespEntityUtil<String> etspResp = new RespEntityUtil<>();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(baos));
        String exception = baos.toString();
        logger.error(exception);
        String error = null;
        if (e.getStackTrace().length > 0) {
            StackTraceElement stackTraceElement = e.getStackTrace()[0];// 得到异常棧的首个元素
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
            String nowStr = LocalDateTime.now().format(format);
            error = "Class：" + stackTraceElement.getFileName() + ", Method：" + stackTraceElement.getMethodName() + ", Line：" + stackTraceElement.getLineNumber() + ", error：" + e.getMessage() + ", time：" + nowStr;
        }
        return etspResp.fail(StatusCodeUtil.ERROR.getCode(), StatusCodeUtil.ERROR.getMsg(), error);
    }


    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public RespEntityUtil<String> runtimeExceptionHandler(ConstraintViolationException e) {
        RespEntityUtil<String> resp = new RespEntityUtil<>();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(baos));
        String exception = baos.toString();
        logger.error(exception);
        for (ConstraintViolation<?> s : e.getConstraintViolations()) {
            resp.fail(StatusCodeUtil.C.getCode(), s.getMessage(), s.getInvalidValue() + ": " + s.getMessage());
            return resp;
        }
        return resp.fail(StatusCodeUtil.C.getCode(), StatusCodeUtil.C.getMsg(), "参数不合法！！！");
    }


}

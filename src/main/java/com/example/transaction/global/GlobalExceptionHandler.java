package com.example.transaction.global;

import com.example.transaction.exception.TransactionException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理未被指定的异常
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R defaultErrorHandler(HttpServletRequest request, Exception e) {
        logger.error("全局异常信息 ex={}", e.getMessage(), e);
        return R.fail(e.getLocalizedMessage());
    }

    /**
     * 交易异常
     */
    @ExceptionHandler(value = {TransactionException.class})
    public R jsonErrorHandler(HttpServletRequest request, TransactionException e) {
        logger.error("交易异常信息 ex={}", e.getMessage());
        return R.fail(e.getErrorCode(), e.getMessage());
    }

    /**
     * 缺少必要参数
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R requiredParamMissedHandler(HttpServletRequest request, MissingServletRequestParameterException e) {
        return R.fail(String.format(GlobalConstant.MISSED_REQUIRED_PARAM_ERROR, e.getParameterName()));
    }

    /**
     *  参数校验异常处理
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public R validExceptionHandler(HttpServletRequest request, BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        return R.fail(String.format("%s %s", fieldErrors.get(0).getField(), fieldErrors.get(0).getDefaultMessage()));
    }
}

package com.wsc.basic.core.exception;

import com.wsc.basic.core.constant.I18nMsgConstants;
import com.wsc.basic.core.model.Result;
import com.wsc.basic.core.utils.I18nMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author 吴淑超
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Resource
    private ApplicationContext applicationContext;

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = GlobalException.class)
    public Result<String> businessException(GlobalException exception) {
        log.error("GlobalException异常：{}", exception.getMessage());
        return Result.failed(String.valueOf(exception.getCode()), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<String> parameterException(MethodArgumentNotValidException exception) {
        log.error("MethodArgumentNotValidException异常：{}", exception.getBindingResult());
        List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();
        String message = I18nMessages.getMessage(allErrors.get(0).getDefaultMessage());
        return Result.failed(String.valueOf(HttpStatus.BAD_REQUEST.value()), message);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = BindException.class)
    public Result<String> bindException(BindException exception) {
        log.error("BindException异常：{}", exception.getBindingResult());
        String message = I18nMessages.getMessage(Objects.requireNonNull(exception.getFieldError()).getDefaultMessage());
        return Result.failed(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), message);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public Result<String> exception(Exception exception) {
        log.error("Exception异常：", exception);
        List<String> profiles = Arrays.asList(applicationContext.getEnvironment().getActiveProfiles());
        String debugMessage = profiles.contains("prod") ? null : exception.toString();
        return Result.failed("500", I18nMessages.getMessage(I18nMsgConstants.SERVER_INTERNAL_ERROR), debugMessage);
    }

}

package com.wsc.basic.core.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 吴淑超
 * @date 2020-02-14 17:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "统一返回包装")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String PRD_CODE = "EX";
    private static final String EXCEPTION_TYPE = "BUSINESS";
    private static final String SPLIT_CHAR = "_";
    public static final String CODE_PRE = PRD_CODE + SPLIT_CHAR + EXCEPTION_TYPE + SPLIT_CHAR;

    @ApiModelProperty(value = "是否成功", required = true)
    private Boolean success;

    @ApiModelProperty("异常编码")
    private String code;

    @ApiModelProperty("响应消息")
    private String message;

    @ApiModelProperty("请求id")
    private String requestId;

    @ApiModelProperty(value = "结果集")
    private T data;

    @ApiModelProperty(value = "错误信息")
    private String debugMessage;

    public static <T> Result<T> success() {
        return success(null, null);
    }

    public static <T> Result<T> success(T data) {
        return success(null, data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(true, CODE_PRE + "200", message, null, data, null);
    }

    public static <T> Result<T> failed(String message) {
        return failed("500", message);
    }

    public static <T> Result<T> failed(String code, String message) {
        return failed(code, message, null);
    }

    public static <T> Result<T> failed(String code, String message, String debugMessage) {
        return new Result<>(false, CODE_PRE + code, message, null, null, debugMessage);
    }

}

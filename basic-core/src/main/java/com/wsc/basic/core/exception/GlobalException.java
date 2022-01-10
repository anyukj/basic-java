package com.wsc.basic.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.http.HttpStatus;


/**
 * @author 吴淑超
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GlobalException extends RuntimeException {

    private HttpStatus httpStatus;

    public GlobalException(String msg) {
        super(msg);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public GlobalException(HttpStatus httpStatus) {
        super(httpStatus.getReasonPhrase());
        this.httpStatus = httpStatus;
    }

    public GlobalException(String msg, HttpStatus httpStatus) {
        super(msg == null ? httpStatus.getReasonPhrase() : msg);
        this.httpStatus = httpStatus;
    }

    public Integer getCode() {
        return this.httpStatus.value();
    }

}

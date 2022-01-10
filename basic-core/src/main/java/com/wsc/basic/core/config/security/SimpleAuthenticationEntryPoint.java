package com.wsc.basic.core.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wsc.basic.core.utils.I18nMessages;
import com.wsc.basic.core.constant.I18nMsgConstants;
import com.wsc.basic.core.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 未登录统一异常处理（401）
 *
 * @author 吴淑超
 * @since 2020-10-06 23:03
 */
@Slf4j
public class SimpleAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        // 返回内容
        log.error("SimpleAuthenticationEntryPoint error:");
        Result<String> result = Result.failed(
                String.valueOf(HttpStatus.UNAUTHORIZED.value()),
                I18nMessages.getMessage(I18nMsgConstants.UNAUTHORIZED));
        // 输出结果
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(new ObjectMapper().writeValueAsString(result));
        printWriter.flush();
        printWriter.close();
    }

}

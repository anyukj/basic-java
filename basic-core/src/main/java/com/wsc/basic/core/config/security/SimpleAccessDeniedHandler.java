package com.wsc.basic.core.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wsc.basic.core.constant.I18nMsgConstants;
import com.wsc.basic.core.model.Result;
import com.wsc.basic.core.utils.I18nMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 无权限统一异常处理（403）
 *
 * @author 吴淑超
 * @since 2020-10-06 23:01
 */
@Slf4j
public class SimpleAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        // 返回内容
        log.error("SimpleAccessDeniedHandler error");
        Result<String> result = Result.failed(
                String.valueOf(HttpStatus.FORBIDDEN.value()),
                I18nMessages.getMessage(I18nMsgConstants.NO_ACCESS));
        // 输出结果
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(new ObjectMapper().writeValueAsString(result));
        printWriter.flush();
        printWriter.close();
    }

}

package com.wsc.basic.core.model;

import com.wsc.basic.core.constant.MDCConstants;
import com.wsc.basic.core.utils.IPUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 链路追踪信息
 *
 * @author 吴淑超
 * @since 2020-10-08 11:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TraceInfo {
    private String requestId;
    private String requestIp;
    private String timestamp;
    private String method;
    private String uri;

    public TraceInfo(HttpServletRequest request) {
        this.requestId = UUID.randomUUID().toString().replace("-", "");
        this.requestIp = IPUtils.getIpAddress(request);
        this.timestamp = String.valueOf(System.currentTimeMillis());
        this.method = request.getMethod();
        this.uri = request.getRequestURI();

        request.setAttribute(MDCConstants.REQUEST_ID, this.requestId);
    }
}

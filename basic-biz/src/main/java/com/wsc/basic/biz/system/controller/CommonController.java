package com.wsc.basic.biz.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.wsc.basic.core.constant.I18nMsgConstants;
import com.wsc.basic.core.utils.I18nMessages;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 吴淑超
 * @since 2020-10-08 11:49
 */
@RestController
@RequestMapping
@Api(tags = "公共接口")
@ApiSupport(order = 0, author = "吴淑超")
public class CommonController {

    @ApiOperation("健康检查")
    @GetMapping("/service/health/check")
    public String healthCheck() {
        return I18nMessages.getMessage(I18nMsgConstants.HEALTH);
    }

}

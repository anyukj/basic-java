package com.wsc.basic.biz.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.wsc.basic.biz.system.service.SysResourceService;
import com.wsc.basic.core.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 吴淑超
 * @since 2020-06-02 10:05:11
 */
@RestController
@RequestMapping("sysResource")
@Api(tags = "资源管理")
@ApiSupport(order = 4, author = "吴淑超")
public class SysResourceController {

    @Resource
    private SysResourceService sysResourceService;

    @Deprecated
    @ApiOperation("按swagger文档初始化资源")
    @GetMapping("/initBySwagger")
    public Result<String> initBySwagger() {
        return sysResourceService.initBySwagger();
    }

}
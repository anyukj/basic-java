package com.wsc.basic.biz.system.controller;

import com.wsc.basic.biz.system.model.dto.member.MemberCheckDTO;
import com.wsc.basic.biz.system.model.vo.member.MemberCheckVO;
import com.wsc.basic.biz.system.model.vo.member.MemberMonitorVO;
import com.wsc.basic.biz.system.service.SysMemberService;
import com.wsc.basic.core.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author 吴淑超
 * @since 2020-06-01 17:40:07
 */
@RestController
@RequestMapping("moli")
@Api(tags = "魔力会员")
public class SysMemberController {

    @Resource
    private SysMemberService sysMemberService;

    @ApiOperation("校验用户信息")
    @PostMapping("check")
    public Result<MemberCheckDTO> check(@Valid @RequestBody MemberCheckVO entity) {
        return Result.success(sysMemberService.check(entity));
    }

    @ApiOperation("监控破解用户")
    @PostMapping("monitor")
    public Result<Boolean> monitor(@RequestBody MemberMonitorVO entity) {
        return Result.success(sysMemberService.monitor(entity));
    }

}
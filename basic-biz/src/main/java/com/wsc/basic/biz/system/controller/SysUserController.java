package com.wsc.basic.biz.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.wsc.basic.biz.system.model.vo.user.*;
import com.wsc.basic.biz.system.service.SysUserService;
import com.wsc.basic.biz.system.model.dto.user.UserPageDTO;
import com.wsc.basic.biz.system.model.dto.user.UserItemDTO;
import com.wsc.basic.biz.system.model.vo.user.*;
import com.wsc.basic.core.config.security.UserContext;
import com.wsc.basic.core.constant.I18nMsgConstants;
import com.wsc.basic.core.model.BasePageDTO;
import com.wsc.basic.core.model.Result;
import com.wsc.basic.core.utils.I18nMessages;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * @author 吴淑超
 * @since 2020-06-01 17:40:07
 */
@RestController
@RequestMapping("sysUser")
@Api(tags = "用户管理")
@ApiSupport(order = 1, author = "吴淑超")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @ApiOperation("用户登录")
    @PostMapping("login")
    public Result<String> login(@Valid @RequestBody UserLoginVO entity) {
        return Result.success(sysUserService.login(entity));
    }

    @ApiOperation("获取当前用户信息")
    @GetMapping("currentUser")
    public Result<UserItemDTO> currentUser() {
        return Result.success(sysUserService.currentUser());
    }

    @ApiOperation("刷新token")
    @GetMapping("refreshToken")
    public Result<String> refreshToken() {
        String token = sysUserService.generateToken(UserContext.getUser().getUserId());
        return Result.success(token);
    }

    @ApiOperation("修改个人密码")
    @PutMapping("password")
    public Result<?> updatePassword(@Valid @RequestBody UpdatePasswordVO entity) {
        sysUserService.updatePassword(entity);
        return Result.success(I18nMessages.getMessage(I18nMsgConstants.OPERATION_SUCCESS));
    }

    @ApiOperation("重置密码")
    @PutMapping("resetPassword")
    public Result<?> resetPassword(@Valid @RequestBody ResetPasswordVO entity) {
        sysUserService.resetPassword(entity);
        return Result.success(I18nMessages.getMessage(I18nMsgConstants.OPERATION_SUCCESS));
    }

    @ApiOperation("分页查询")
    @GetMapping
    public Result<BasePageDTO<UserPageDTO>> queryPage(QueryUserPageVO entity) {
        return Result.success(sysUserService.queryPage(entity));
    }

    @ApiOperation("通过主键查询单条数据")
    @GetMapping("{id}")
    public Result<UserItemDTO> queryItem(@PathVariable Serializable id) {
        return Result.success(sysUserService.queryItem(id));
    }

    @ApiOperation("新增数据")
    @PostMapping
    public Result<?> add(@Valid @RequestBody CreateUserVO entity) {
        sysUserService.add(entity);
        return Result.success(I18nMessages.getMessage(I18nMsgConstants.OPERATION_SUCCESS));
    }

    @ApiOperation("修改数据")
    @PutMapping
    public Result<?> update(@Valid @RequestBody UpdateUserVO entity) {
        sysUserService.update(entity);
        return Result.success(I18nMessages.getMessage(I18nMsgConstants.OPERATION_SUCCESS));
    }

    @ApiOperation("删除数据")
    @DeleteMapping
    public Result<?> delete(@RequestParam("ids") List<Integer> ids) {
        sysUserService.delete(ids);
        return Result.success(I18nMessages.getMessage(I18nMsgConstants.OPERATION_SUCCESS));
    }

}
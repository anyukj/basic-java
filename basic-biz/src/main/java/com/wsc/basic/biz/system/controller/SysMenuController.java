package com.wsc.basic.biz.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.wsc.basic.biz.system.service.SysMenuService;
import com.wsc.basic.biz.system.model.dto.menu.TreeMenuDTO;
import com.wsc.basic.biz.system.model.entity.SysMenu;
import com.wsc.basic.biz.system.model.vo.menu.CreateMenuVO;
import com.wsc.basic.biz.system.model.vo.menu.UpdateMenuVO;
import com.wsc.basic.core.config.security.UserContext;
import com.wsc.basic.core.constant.I18nMsgConstants;
import com.wsc.basic.core.model.Result;
import com.wsc.basic.core.model.TokenEntity;
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
 * @since 2020-06-03 09:59:35
 */
@RestController
@RequestMapping("sysMenu")
@Api(tags = "菜单管理")
@ApiSupport(order = 3, author = "吴淑超")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;

    @ApiOperation("获取当前用户菜单(树形菜单)")
    @GetMapping("currentUserMenu")
    public Result<List<TreeMenuDTO>> currentUserMenu() {
        TokenEntity tokenEntity = UserContext.getUser();
        return Result.success(sysMenuService.currentMenu(tokenEntity));
    }

    @ApiOperation("获取全部菜单(树形菜单)")
    @GetMapping("allMenu")
    public Result<List<TreeMenuDTO>> allMenu() {
        return Result.success(sysMenuService.allMenu());
    }

    @ApiOperation("通过主键查询单条数据")
    @GetMapping("{id}")
    public Result<SysMenu> queryOne(@PathVariable Serializable id) {
        return Result.success(sysMenuService.getById(id));
    }

    @ApiOperation("新增数据")
    @PostMapping
    public Result<?> add(@Valid @RequestBody CreateMenuVO entity) {
        sysMenuService.add(entity);
        return Result.success(I18nMessages.getMessage(I18nMsgConstants.OPERATION_SUCCESS));
    }

    @ApiOperation("修改数据")
    @PutMapping
    public Result<?> update(@Valid @RequestBody UpdateMenuVO entity) {
        sysMenuService.update(entity);
        return Result.success(I18nMessages.getMessage(I18nMsgConstants.OPERATION_SUCCESS));
    }

    @ApiOperation("删除数据")
    @DeleteMapping
    public Result<?> delete(@RequestParam("ids") List<Long> ids) {
        sysMenuService.delete(ids);
        return Result.success(I18nMessages.getMessage(I18nMsgConstants.OPERATION_SUCCESS));
    }

}
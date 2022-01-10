package com.wsc.basic.biz.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.wsc.basic.biz.system.service.SysRoleService;
import com.wsc.basic.biz.system.model.dto.role.RoleItemDTO;
import com.wsc.basic.biz.system.model.entity.SysRole;
import com.wsc.basic.biz.system.model.vo.role.CreateRoleVO;
import com.wsc.basic.biz.system.model.vo.role.QueryRolePageVO;
import com.wsc.basic.biz.system.model.vo.role.UpdateRoleVO;
import com.wsc.basic.core.constant.I18nMsgConstants;
import com.wsc.basic.core.model.BasePageDTO;
import com.wsc.basic.core.model.OneToManyVO;
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
 * @since 2020-06-16 09:42:49
 */
@RestController
@RequestMapping("sysRole")
@Api(tags = "角色管理")
@ApiSupport(order = 2, author = "吴淑超")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @ApiOperation("分页查询所有数据")
    @GetMapping
    public Result<BasePageDTO<SysRole>> queryPage(QueryRolePageVO entity) {
        return Result.success(sysRoleService.queryPage(entity));
    }

    @ApiOperation("通过主键查询单条数据")
    @GetMapping("{id}")
    public Result<RoleItemDTO> queryItem(@PathVariable Serializable id) {
        return Result.success(sysRoleService.queryItem(id));
    }

    @ApiOperation("新增数据")
    @PostMapping
    public Result<?> add(@Valid @RequestBody CreateRoleVO entity) {
        sysRoleService.add(entity);
        return Result.success(I18nMessages.getMessage(I18nMsgConstants.OPERATION_SUCCESS));
    }

    @ApiOperation("修改数据")
    @PutMapping
    public Result<?> update(@Valid @RequestBody UpdateRoleVO entity) {
        sysRoleService.update(entity);
        return Result.success(I18nMessages.getMessage(I18nMsgConstants.OPERATION_SUCCESS));
    }

    @ApiOperation("删除数据")
    @DeleteMapping
    public Result<?> delete(@RequestParam("ids") List<Integer> ids) {
        sysRoleService.delete(ids);
        return Result.success(I18nMessages.getMessage(I18nMsgConstants.OPERATION_SUCCESS));
    }

    @ApiOperation("分配用户")
    @PostMapping("assignUsers")
    public Result<?> assignUsers(@Valid @RequestBody OneToManyVO entity) {
        sysRoleService.assignUsers(entity);
        return Result.success(I18nMessages.getMessage(I18nMsgConstants.OPERATION_SUCCESS));
    }

    @ApiOperation("获取角色关联的用户")
    @GetMapping("getRelationUsers")
    public Result<List<Long>> getRelationUsers(int roleId) {
        return Result.success(sysRoleService.getRelationUsers(roleId));
    }

    @ApiOperation("分配菜单")
    @PostMapping("assignMenus")
    public Result<?> assignMenus(@Valid @RequestBody OneToManyVO entity) {
        sysRoleService.assignMenus(entity);
        return Result.success(I18nMessages.getMessage(I18nMsgConstants.OPERATION_SUCCESS));
    }

    @ApiOperation("获取角色关联的菜单")
    @GetMapping("getRelationMenus")
    public Result<List<Long>> getRelationMenus(long roleId) {
        return Result.success(sysRoleService.getRelationMenus(roleId));
    }

}
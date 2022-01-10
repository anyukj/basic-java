package com.wsc.basic.biz.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wsc.basic.biz.system.model.entity.SysRoleMenu;

import java.util.List;

/**
 * 角色菜单关联表(SysRoleMenu)表数据库访问层
 *
 * @author 吴淑超
 * @since 2020-06-23 18:02:15
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 批量保存数据
     *
     * @param list 数据列表
     * @return 成功条数
     */
    int batchSave(List<SysRoleMenu> list);

}
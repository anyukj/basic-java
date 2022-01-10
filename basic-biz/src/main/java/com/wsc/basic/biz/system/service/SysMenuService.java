package com.wsc.basic.biz.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsc.basic.biz.system.model.dto.menu.TreeMenuDTO;
import com.wsc.basic.biz.system.model.entity.SysMenu;
import com.wsc.basic.biz.system.model.vo.menu.CreateMenuVO;
import com.wsc.basic.biz.system.model.vo.menu.UpdateMenuVO;
import com.wsc.basic.core.model.TokenEntity;

import java.util.List;

/**
 * 功能菜单表(SysMenu)表服务接口
 *
 * @author 吴淑超
 * @since 2020-06-03 09:59:35
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取当前用户拥有的菜单列表
     *
     * @param tokenEntity 用户信息
     * @return 树形菜单
     */
    List<TreeMenuDTO> currentMenu(TokenEntity tokenEntity);

    /**
     * 获取全部菜单
     *
     * @return 树形菜单
     */
    List<TreeMenuDTO> allMenu();

    /**
     * 新建菜单
     *
     * @param entity 创建菜单实体
     */
    void add(CreateMenuVO entity);

    /**
     * 修改菜单
     *
     * @param entity 修改菜单实体
     */
    void update(UpdateMenuVO entity);

    /**
     * 删除菜单
     *
     * @param ids 需要删除的id列表
     */
    void delete(List<Long> ids);

}
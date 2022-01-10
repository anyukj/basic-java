package com.wsc.basic.biz.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsc.basic.biz.system.dao.SysRoleMapper;
import com.wsc.basic.biz.system.dao.SysRoleMenuMapper;
import com.wsc.basic.biz.system.dao.SysRoleUserMapper;
import com.wsc.basic.biz.system.service.SysRoleService;
import com.wsc.basic.biz.system.model.dto.role.RoleItemDTO;
import com.wsc.basic.biz.system.model.entity.SysRole;
import com.wsc.basic.biz.system.model.entity.SysRoleMenu;
import com.wsc.basic.biz.system.model.entity.SysRoleUser;
import com.wsc.basic.biz.system.model.vo.role.CreateRoleVO;
import com.wsc.basic.biz.system.model.vo.role.QueryRolePageVO;
import com.wsc.basic.biz.system.model.vo.role.UpdateRoleVO;
import com.wsc.basic.core.annotation.LogPoint;
import com.wsc.basic.core.model.BasePageDTO;
import com.wsc.basic.core.model.OneToManyVO;
import com.wsc.basic.core.utils.SuperBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色表(SysRole)表服务实现类
 *
 * @author 吴淑超
 * @since 2020-06-03 11:04:38
 */
@Slf4j
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;


    @Override
    public BasePageDTO<SysRole> queryPage(QueryRolePageVO entity) {
        Page<?> page = entity.genPage().addOrder(OrderItem.desc("id"));
        Page<SysRole> result = baseMapper.queryPage(page, entity);
        return new BasePageDTO<>(result);
    }

    @Override
    public RoleItemDTO queryItem(Serializable id) {
        SysRole sysRole = baseMapper.selectById(id);
        return SuperBeanUtils.copyProperties(sysRole, RoleItemDTO::new);
    }

    @Override
    public List<RoleItemDTO> queryItemByUserId(Serializable userId) {
        return baseMapper.queryItemByUserId(userId);
    }

    @Override
    public void add(CreateRoleVO entity) {
        SysRole sysRole = SuperBeanUtils.copyProperties(entity, SysRole::new);
        super.save(sysRole);
    }

    @Override
    public void update(UpdateRoleVO entity) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(entity, sysRole);
        super.updateById(sysRole);
    }

    @CacheEvict(cacheNames = {"user", "role", "menu"})
    @LogPoint(message = "'删除角色信息：'+#userInfo+',删除列表：'+#ids")
    @Override
    public void delete(List<Integer> ids) {
        // 删除角色
        super.removeByIds(ids);
        // 删除角色相关的用户
        sysRoleUserMapper.delete(Wrappers.lambdaQuery(SysRoleUser.class)
                .in(SysRoleUser::getRoleId, ids));
        // 删除角色相关的菜单
        sysRoleMenuMapper.delete(Wrappers.lambdaQuery(SysRoleMenu.class)
                .in(SysRoleMenu::getRoleId, ids));
    }

    @CacheEvict(cacheNames = "user", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignUsers(OneToManyVO entity) {
        List<SysRoleUser> sysRoleUsers = sysRoleUserMapper.selectList(Wrappers.lambdaQuery(SysRoleUser.class)
                .eq(SysRoleUser::getRoleId, entity.getOne()));
        // 前端没有传过来的id需要删除
        Set<Long> deleteIds = sysRoleUsers.stream()
                .filter(item -> !entity.getMany().contains(item.getUserId()))
                .map(SysRoleUser::getId)
                .collect(Collectors.toSet());
        if (deleteIds.size() > 0) {
            sysRoleUserMapper.deleteBatchIds(deleteIds);
        }
        // 保存后端没有的id数据
        Set<Long> allUserIds = sysRoleUsers.stream()
                .map(SysRoleUser::getUserId)
                .collect(Collectors.toSet());
        List<SysRoleUser> addRoleUsers = entity.getMany().stream()
                .filter(userId -> !allUserIds.contains(userId))
                .map(userId -> {
                    SysRoleUser sysRoleUser = new SysRoleUser();
                    sysRoleUser.setRoleId(entity.getOne());
                    sysRoleUser.setUserId(userId);
                    return sysRoleUser;
                }).collect(Collectors.toList());
        if (addRoleUsers.size() > 0) {
            sysRoleUserMapper.batchSave(addRoleUsers);
        }
    }


    @Override
    public List<Long> getRelationUsers(int roleId) {
        List<SysRoleUser> sysRoleUsers = sysRoleUserMapper.selectList(Wrappers.lambdaQuery(SysRoleUser.class)
                .eq(SysRoleUser::getRoleId, roleId));
        return sysRoleUsers.stream()
                .map(SysRoleUser::getUserId)
                .collect(Collectors.toList());
    }

    @CacheEvict(cacheNames = "menu", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignMenus(OneToManyVO entity) {
        List<SysRoleMenu> sysRoleMenus = sysRoleMenuMapper.selectList(Wrappers.lambdaQuery(SysRoleMenu.class)
                .eq(SysRoleMenu::getRoleId, entity.getOne()));
        // 前端没有传过来的id需要删除
        Set<Long> deleteIds = sysRoleMenus.stream()
                .filter(item -> !entity.getMany().contains(item.getMenuId()))
                .map(SysRoleMenu::getId)
                .collect(Collectors.toSet());
        if (deleteIds.size() > 0) {
            sysRoleMenuMapper.deleteBatchIds(deleteIds);
        }
        // 保存后端没有的id数据
        Set<Long> allMenuIds = sysRoleMenus.stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toSet());
        List<SysRoleMenu> addRoleMenus = entity.getMany().stream()
                .filter(menuId -> !allMenuIds.contains(menuId))
                .map(menuId -> {
                    SysRoleMenu sysRoleMenu = new SysRoleMenu();
                    sysRoleMenu.setRoleId(entity.getOne());
                    sysRoleMenu.setMenuId(menuId);
                    return sysRoleMenu;
                }).collect(Collectors.toList());
        if (addRoleMenus.size() > 0) {
            sysRoleMenuMapper.batchSave(addRoleMenus);
        }
    }

    @Override
    public List<Long> getRelationMenus(long roleId) {
        List<SysRoleMenu> sysRoleUsers = sysRoleMenuMapper.selectList(Wrappers.lambdaQuery(SysRoleMenu.class)
                .eq(SysRoleMenu::getRoleId, roleId));
        return sysRoleUsers.stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toList());
    }

}
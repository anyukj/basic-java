package com.wsc.basic.biz.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsc.basic.biz.system.model.dto.role.RoleItemDTO;
import com.wsc.basic.biz.system.model.entity.SysRole;
import com.wsc.basic.biz.system.model.vo.role.CreateRoleVO;
import com.wsc.basic.biz.system.model.vo.role.QueryRolePageVO;
import com.wsc.basic.biz.system.model.vo.role.UpdateRoleVO;
import com.wsc.basic.core.model.BasePageDTO;
import com.wsc.basic.core.model.OneToManyVO;

import java.io.Serializable;
import java.util.List;

/**
 * 角色表(SysRole)表服务接口
 *
 * @author 吴淑超
 * @since 2020-06-03 11:04:38
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 分页查询数据
     *
     * @param entity 查询条件
     * @return 分页数据
     */
    BasePageDTO<SysRole> queryPage(QueryRolePageVO entity);

    /**
     * 查询单条记录详情
     *
     * @param id 角色id
     * @return 明细数据
     */
    RoleItemDTO queryItem(Serializable id);

    /**
     * 通过用户id查询角色列表
     *
     * @param userId 用户id
     * @return 角色列表
     */
    List<RoleItemDTO> queryItemByUserId(Serializable userId);

    /**
     * 新增
     *
     * @param entity 创建实体
     */
    void add(CreateRoleVO entity);

    /**
     * 修改
     *
     * @param entity 修改实体
     */
    void update(UpdateRoleVO entity);

    /**
     * 批量删除
     *
     * @param ids 需要删除的id列表
     */
    void delete(List<Integer> ids);

    /**
     * 分配用户
     *
     * @param entity 一对多关联实体
     */
    void assignUsers(OneToManyVO entity);

    /**
     * 获取角色关联的用户
     *
     * @param roleId 角色id
     * @return 用户id列表
     */
    List<Long> getRelationUsers(int roleId);

    /**
     * 分配菜单
     *
     * @param entity 一对多关联实体
     */
    void assignMenus(OneToManyVO entity);

    /**
     * 获取角色关联的菜单
     *
     * @param roleId 角色id
     * @return 用户id列表
     */
    List<Long> getRelationMenus(long roleId);

}
package com.wsc.basic.biz.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wsc.basic.biz.system.model.dto.role.RoleItemDTO;
import com.wsc.basic.biz.system.model.entity.SysRole;
import com.wsc.basic.biz.system.model.vo.role.QueryRolePageVO;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * 角色表(SysRole)表数据库访问层
 *
 * @author 吴淑超
 * @since 2020-06-03 11:04:38
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 分页查询数据
     *
     * @param page  分页信息
     * @param query 查询条件
     * @return 分页数据
     */
    Page<SysRole> queryPage(Page<?> page, QueryRolePageVO query);

    /**
     * 获取角色列表
     *
     * @param userId 用户id
     * @return 角色列表
     */
    List<RoleItemDTO> queryItemByUserId(@Param("userId") Serializable userId);

}
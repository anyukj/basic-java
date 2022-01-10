package com.wsc.basic.biz.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wsc.basic.biz.system.model.dto.user.UserPageDTO;
import com.wsc.basic.biz.system.model.entity.SysUser;
import com.wsc.basic.biz.system.model.vo.user.QueryUserPageVO;

/**
 * 用户表(SysUser)表数据库访问层
 *
 * @author 吴淑超
 * @since 2020-06-01 17:40:07
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 分页查询数据
     *
     * @param page  分页信息
     * @param query 查询条件
     * @return 分页数据
     */
    Page<UserPageDTO> queryPage(Page<?> page, QueryUserPageVO query);
}
package com.wsc.basic.biz.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wsc.basic.biz.system.model.dto.user.UserPageDTO;
import com.wsc.basic.biz.system.model.entity.SysMember;
import com.wsc.basic.biz.system.model.entity.SysUser;
import com.wsc.basic.biz.system.model.vo.user.QueryUserPageVO;

/**
 * 会员表(SysUser)表数据库访问层
 *
 * @author 吴淑超
 * @since 2020-06-01 17:40:07
 */
public interface SysMemberMapper extends BaseMapper<SysMember> {

}
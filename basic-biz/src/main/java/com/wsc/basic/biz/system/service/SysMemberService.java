package com.wsc.basic.biz.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsc.basic.biz.system.model.dto.member.MemberCheckDTO;
import com.wsc.basic.biz.system.model.entity.SysMember;
import com.wsc.basic.biz.system.model.vo.member.MemberCheckVO;
import com.wsc.basic.biz.system.model.vo.member.MemberMonitorVO;

/**
 * 会员表(SysUser)表服务接口
 *
 * @author 吴淑超
 * @since 2020-06-01 17:40:05
 */
public interface SysMemberService extends IService<SysMember> {

    /**
     * 获取会员信息
     *
     * @param entity 请求参数
     * @return 结束
     */
    MemberCheckDTO check(MemberCheckVO entity);

    /**
     * 监控破解用户信息
     *
     * @param entity 实体
     * @return 标识
     */
    Boolean monitor(MemberMonitorVO entity);

}
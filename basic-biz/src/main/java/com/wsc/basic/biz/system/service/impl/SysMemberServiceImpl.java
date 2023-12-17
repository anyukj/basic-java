package com.wsc.basic.biz.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsc.basic.biz.system.dao.SysMemberMapper;
import com.wsc.basic.biz.system.model.dto.member.MemberCheckDTO;
import com.wsc.basic.biz.system.model.entity.SysMember;
import com.wsc.basic.biz.system.model.vo.member.MemberCheckVO;
import com.wsc.basic.biz.system.service.SysMemberService;
import com.wsc.basic.core.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 会员表(SysUser)表服务实现类
 *
 * @author 吴淑超
 * @since 2020-06-01 17:40:06
 */
@Slf4j
@Service
public class SysMemberServiceImpl extends ServiceImpl<SysMemberMapper, SysMember> implements SysMemberService {

    @Override
    public MemberCheckDTO check(MemberCheckVO entity) {
        SysMember sysMember = baseMapper.selectOne(Wrappers.lambdaQuery(SysMember.class)
                .eq(SysMember::getCard, entity.getCard()));
        if (sysMember == null) {
            throw new GlobalException("卡号不存在");
        } else if (sysMember.getStatus() != 0) {
            throw new GlobalException("卡号已被禁用");
        } else if (sysMember.getExpirationTime().compareTo(LocalDateTime.now()) < 0) {
            throw new GlobalException("卡号已过期");
        } else if(sysMember.getId() != 1 && StrUtil.isNotBlank(sysMember.getMac()) && !StrUtil.equals(sysMember.getMac(), entity.getMac())) {
            throw new GlobalException("卡号已绑定别的设备");
        }
        // 如果mac为空那么绑定
        if (StrUtil.isBlank(sysMember.getMac()) && sysMember.getId() > 1) {
            sysMember.setMac(entity.getMac());
            baseMapper.updateById(sysMember);
        }
        // 返回实体信息
        return new MemberCheckDTO(sysMember.getExpirationTime(), sysMember.getLevel());
    }

}
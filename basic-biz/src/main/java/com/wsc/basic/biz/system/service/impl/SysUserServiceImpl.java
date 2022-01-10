package com.wsc.basic.biz.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsc.basic.biz.system.dao.SysUserMapper;
import com.wsc.basic.biz.system.enums.RelationTypeEnum;
import com.wsc.basic.biz.system.model.vo.user.*;
import com.wsc.basic.biz.system.service.SysFileService;
import com.wsc.basic.biz.system.service.SysRoleService;
import com.wsc.basic.biz.system.service.SysUserService;
import com.wsc.basic.biz.system.model.dto.file.FileItemDTO;
import com.wsc.basic.biz.system.model.dto.role.RoleItemDTO;
import com.wsc.basic.biz.system.model.dto.user.UserPageDTO;
import com.wsc.basic.biz.system.model.dto.user.UserItemDTO;
import com.wsc.basic.biz.system.model.entity.SysUser;
import com.wsc.basic.biz.system.model.vo.file.QueryRelationFileVO;
import com.wsc.basic.biz.system.model.vo.file.RelationFileVO;
import com.wsc.basic.biz.system.model.vo.user.*;
import com.wsc.basic.core.annotation.LogPoint;
import com.wsc.basic.core.config.security.UserContext;
import com.wsc.basic.core.constant.BizConstant;
import com.wsc.basic.core.exception.GlobalException;
import com.wsc.basic.core.model.BasePageDTO;
import com.wsc.basic.core.model.TokenEntity;
import com.wsc.basic.core.utils.JwtTokenUtils;
import com.wsc.basic.core.utils.Sm3Utils;
import com.wsc.basic.core.utils.SuperBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(SysUser)表服务实现类
 *
 * @author 吴淑超
 * @since 2020-06-01 17:40:06
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysFileService sysFileService;

    @LogPoint(message = "'用户登录：'+#entity.getUserName()")
    @Override
    public String login(UserLoginVO entity) {
        // 查询用户信息
        SysUser sysUser = super.getOne(Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getUserName, entity.getUserName()));
        if (sysUser == null) {
            throw new GlobalException("用户不存在");
        } else if (!Sm3Utils.verification(entity.getPassword(), sysUser.getPassword())) {
            throw new GlobalException("密码错误");
        } else if (sysUser.getStatus().equals(1)) {
            throw new GlobalException("用户已被停用");
        }
        // 生成并返回token
        return generateToken(sysUser.getId());
    }

    @Override
    public String generateToken(Long userId) {
        SysUser sysUser = super.getById(userId);
        // 查询角色信息
        List<RoleItemDTO> roleItemList = sysRoleService.queryItemByUserId(sysUser.getId());
        String roleIds = roleItemList.stream()
                .map(item -> String.valueOf(item.getId()))
                .collect(Collectors.joining(","));
        // 返回token
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setUserId(sysUser.getId());
        tokenEntity.setUserName(sysUser.getUserName());
        tokenEntity.setFullName(sysUser.getFullName());
        tokenEntity.setRoleIds(roleIds);
        String token = JwtTokenUtils.generate(tokenEntity);
        if (token == null) {
            throw new GlobalException("创建token失败");
        }
        return token;
    }

    @Override
    public UserItemDTO currentUser() {
        return queryItem(UserContext.getUser().getUserId());
    }

    @LogPoint(message = "'修改密码：'+#userInfo")
    @Override
    public void updatePassword(UpdatePasswordVO entity) {
        TokenEntity tokenEntity = UserContext.getUser();
        SysUser sysUser = super.getById(tokenEntity.getUserId());
        if (sysUser == null) {
            throw new GlobalException("用户不存在");
        } else if (!Sm3Utils.verification(entity.getOldPassword(), sysUser.getPassword())) {
            throw new GlobalException("旧密码错误");
        } else if (sysUser.getStatus().equals(1)) {
            throw new GlobalException("用户已被停用");
        }
        sysUser.setPassword(Sm3Utils.encryption(entity.getNewPassword()));
        super.updateById(sysUser);
    }

    @LogPoint(message = "'重置密码：'+#userInfo+',修改列表：'+#entity.getIds()")
    @Override
    public void resetPassword(ResetPasswordVO entity) {
        super.update(Wrappers.lambdaUpdate(SysUser.class)
                .in(SysUser::getId, entity.getIds())
                .ne(SysUser::getUserName, BizConstant.ADMIN)
                .set(SysUser::getPassword, Sm3Utils.encryption(entity.getPassword())));
    }

    @Override
    public BasePageDTO<UserPageDTO> queryPage(QueryUserPageVO entity) {
        Page<?> page = entity.genPage().addOrder(OrderItem.desc("id"));
        Page<UserPageDTO> result = baseMapper.queryPage(page, entity);
        return new BasePageDTO<>(result);
    }

    @Override
    public UserItemDTO queryItem(Serializable id) {
        SysUser sysUser = baseMapper.selectById(id);
        UserItemDTO result = SuperBeanUtils.copyProperties(sysUser, UserItemDTO::new);
        // 查询用户角色信息
        List<RoleItemDTO> roles = sysRoleService.queryItemByUserId(id);
        result.setRoles(roles);
        // 查询附件信息
        List<FileItemDTO> relationFiles = sysFileService.getRelationFiles(new QueryRelationFileVO(sysUser.getId(), RelationTypeEnum.USER.getCode(), null));
        result.setFiles(relationFiles);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CreateUserVO entity) {
        // 新增用户
        long count = super.count(Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getUserName, entity.getUserName()));
        if (count > 0) {
            throw new GlobalException("用户名已存在");
        }
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(entity, sysUser);
        sysUser.setPassword(Sm3Utils.encryption(sysUser.getPassword()));
        super.save(sysUser);
        //  关联附件信息
        sysFileService.coverRelation(new RelationFileVO(
                sysUser.getId(), entity.getFiles(),
                RelationTypeEnum.USER.getCode(), null));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(UpdateUserVO entity) {
        SysUser sysUser = super.getById(entity.getId());
        if (sysUser == null) {
            throw new GlobalException("用户不存在");
        }
        BeanUtils.copyProperties(entity, sysUser);
        super.updateById(sysUser);
        //  关联附件信息
        sysFileService.coverRelation(new RelationFileVO(
                sysUser.getId(), entity.getFiles(),
                RelationTypeEnum.USER.getCode(), null));
    }

    @LogPoint(message = "'删除用户信息：'+#userInfo+',删除列表：'+#ids")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<Integer> ids) {
        ids.forEach(id -> {
            SysUser sysUser = super.getById(id);
            if (sysUser == null) {
                log.warn("用户id{}不存在", id);
                throw new GlobalException("用户不存在");
            } else if (BizConstant.ADMIN.equals(sysUser.getUserName())) {
                throw new GlobalException("admin账号不允许删除");
            }
            // 逻辑删除
            boolean removeFlag = super.removeById(id);
            if (!removeFlag) {
                throw new GlobalException("删除用户失败");
            }
        });
    }
}
package com.wsc.basic.biz.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsc.basic.biz.system.dao.SysResourceMapper;
import com.wsc.basic.biz.system.service.SysResourceService;
import com.wsc.basic.biz.system.model.entity.SysResource;
import com.wsc.basic.core.config.security.CheckResource;
import com.wsc.basic.core.config.security.UserContext;
import com.wsc.basic.core.constant.BizConstant;
import com.wsc.basic.core.constant.I18nMsgConstants;
import com.wsc.basic.core.model.Result;
import com.wsc.basic.core.model.TokenEntity;
import com.wsc.basic.core.utils.I18nMessages;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * API资源表(SysResource)表服务实现类
 *
 * @author 吴淑超
 * @since 2020-06-02 10:05:11
 */
@Slf4j
@Service
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements SysResourceService, CheckResource {

    @Resource
    private DocumentationCache documentationCache;
    @Resource
    private ServiceModelToSwagger2Mapper modelToSwagger2Mapper;

    private Pattern pattern = Pattern.compile("Using([^_]+)");

    @Override
    public boolean hasPermit(HttpServletRequest request, Authentication authentication) {
        TokenEntity tokenEntity;
        try {
            tokenEntity = UserContext.getUser();
        } catch (Exception e) {
            return false;
        }
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String roleIds = tokenEntity.getRoleIds();
        log.info("开始校验权限：{} {}  {}", method, uri, roleIds);
        // 超级管理员不需要校验权限
        boolean isAdmin = BizConstant.ADMIN.equals(tokenEntity.getUserName());
        if (isAdmin) {
            return true;
        }
        // TODO 通过角色列表查询到所有的权限列表
        // TODO 通过PathMatcher检查请求的method和uri是否匹配到权限列表
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<String> initBySwagger() {
        Documentation documentation = documentationCache.documentationByGroup(Docket.DEFAULT_GROUP_NAME);
        Swagger swagger = modelToSwagger2Mapper.mapDocumentation(documentation);
        Map<String, Path> paths = swagger.getPaths();
        List<SysResource> sysResources = paths.entrySet().stream().flatMap(pathEntry -> {
            String path = pathEntry.getKey().replaceAll("\\{.+}", "*");
            return pathEntry.getValue().getOperations().stream().map(operation -> {
                SysResource sysResource = new SysResource();
                Matcher matcher = pattern.matcher(operation.getOperationId());
                if (matcher.find()) {
                    sysResource.setMethod(matcher.group(1));
                }
                sysResource.setCategory(operation.getTags().get(0));
                sysResource.setUrl(path);
                sysResource.setName(operation.getSummary());
                return sysResource;
            });
        }).collect(Collectors.toList());
        if (sysResources.size() > 0) {
            log.info("初始化资源数据");
            for (SysResource sysResource : sysResources) {
                long count = super.count(Wrappers.lambdaQuery(SysResource.class)
                        .eq(SysResource::getUrl, sysResource.getUrl())
                        .eq(SysResource::getMethod, sysResource.getMethod()));
                if (count == 0) {
                    super.save(sysResource);
                }
            }
        }
        return Result.success(I18nMessages.getMessage(I18nMsgConstants.OPERATION_SUCCESS));
    }

}
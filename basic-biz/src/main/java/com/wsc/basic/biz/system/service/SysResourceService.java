package com.wsc.basic.biz.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsc.basic.biz.system.model.entity.SysResource;
import com.wsc.basic.core.model.Result;

/**
 * API资源表(SysResource)表服务接口
 *
 * @author 吴淑超
 * @since 2020-06-02 10:05:11
 */
public interface SysResourceService extends IService<SysResource> {

    /**
     * 按swagger文档初始化资源
     *
     * @return 结果信息
     */
    Result<String> initBySwagger();

}
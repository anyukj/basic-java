package com.wsc.basic.biz.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsc.basic.biz.system.model.dto.dictionary.DictionaryItemDTO;
import com.wsc.basic.biz.system.model.dto.dictionary.TreeDictionaryDTO;
import com.wsc.basic.biz.system.model.entity.SysDictionary;
import com.wsc.basic.biz.system.model.vo.dictionary.CreateDictionaryVO;
import com.wsc.basic.biz.system.model.vo.dictionary.QueryDictionaryVO;
import com.wsc.basic.biz.system.model.vo.dictionary.UpdateDictionaryVO;

import java.io.Serializable;
import java.util.List;

/**
 * 通用字典(SysDictionary)表服务接口
 *
 * @author 吴淑超
 * @since 2020-07-09 10:58:36
 */
public interface SysDictionaryService extends IService<SysDictionary> {

    /**
     * 查询字典列表-返回树形数据
     *
     * @param entity 查询条件
     * @return 树形数据
     */
    List<TreeDictionaryDTO> treeDictionary(QueryDictionaryVO entity);

    /**
     * 查询子字典列表
     *
     * @param code 字典code
     * @return 数据列表
     */
    List<DictionaryItemDTO> getChildByCode(String code);

    /**
     * 查询单条记录详情
     *
     * @param id 记录id
     * @return 明细数据
     */
    DictionaryItemDTO queryItem(Serializable id);

    /**
     * 新增
     *
     * @param entity 创建实体
     */
    void add(CreateDictionaryVO entity);

    /**
     * 修改
     *
     * @param entity 修改实体
     */
    void update(UpdateDictionaryVO entity);

    /**
     * 批量删除
     *
     * @param ids 需要删除的id列表
     */
    void delete(List<Long> ids);

}
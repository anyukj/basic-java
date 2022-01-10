package com.wsc.basic.biz.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wsc.basic.biz.system.model.dto.dictionary.DictionaryItemDTO;
import com.wsc.basic.biz.system.model.vo.dictionary.QueryDictionaryVO;
import com.wsc.basic.biz.system.model.entity.SysDictionary;

import java.util.List;

/**
 * 通用字典(SysDictionary)表数据库访问层
 *
 * @author 吴淑超
 * @since 2020-07-09 10:58:36
 */
public interface SysDictionaryMapper extends BaseMapper<SysDictionary> {

    /**
     * 根据条件查询全部数据
     *
     * @param entity 查询条件
     * @return 结果数据
     */
    List<SysDictionary> queryList(QueryDictionaryVO entity);

    /**
     * 查询子字典列表
     *
     * @param code 字典code
     * @return 数据列表
     */
    List<DictionaryItemDTO> getChildByCode(String code);

}
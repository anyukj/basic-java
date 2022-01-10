package com.wsc.basic.biz.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsc.basic.biz.system.dao.SysDictionaryMapper;
import com.wsc.basic.biz.system.model.dto.dictionary.DictionaryItemDTO;
import com.wsc.basic.biz.system.model.dto.dictionary.TreeDictionaryDTO;
import com.wsc.basic.biz.system.model.entity.SysDictionary;
import com.wsc.basic.biz.system.model.vo.dictionary.CreateDictionaryVO;
import com.wsc.basic.biz.system.model.vo.dictionary.QueryDictionaryVO;
import com.wsc.basic.biz.system.model.vo.dictionary.UpdateDictionaryVO;
import com.wsc.basic.biz.system.service.SysDictionaryService;
import com.wsc.basic.core.annotation.LogPoint;
import com.wsc.basic.core.constant.I18nMsgConstants;
import com.wsc.basic.core.exception.GlobalException;
import com.wsc.basic.core.utils.SuperBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 通用字典(SysDictionary)表服务实现类
 *
 * @author 吴淑超
 * @since 2020-07-09 10:58:36
 */
@Slf4j
@Service
public class SysDictionaryServiceImpl extends ServiceImpl<SysDictionaryMapper, SysDictionary> implements SysDictionaryService {

    @Cacheable(cacheNames = "dictionary")
    @Override
    public List<TreeDictionaryDTO> treeDictionary(QueryDictionaryVO entity) {
        List<SysDictionary> sysDictionaries = baseMapper.queryList(entity);
        return BooleanUtils.isTrue(entity.getReturnParent()) ?
                convertChildren(sysDictionaries) :
                convertTree(sysDictionaries);
    }

    @Cacheable(cacheNames = "dictionary")
    @Override
    public List<DictionaryItemDTO> getChildByCode(String code) {
        return baseMapper.getChildByCode(code);
    }

    @Override
    public DictionaryItemDTO queryItem(Serializable id) {
        SysDictionary sysDictionary = baseMapper.selectById(id);
        return SuperBeanUtils.copyProperties(sysDictionary, DictionaryItemDTO::new);
    }

    @CacheEvict(cacheNames = "dictionary", allEntries = true)
    @Override
    public void add(CreateDictionaryVO entity) {
        // 校验数据（code不能重复）
        long codeCount = super.count(Wrappers.lambdaQuery(SysDictionary.class)
                .eq(SysDictionary::getCode, entity.getCode()));
        if (codeCount > 0) {
            throw new GlobalException("字典代码已存在");
        }
        // 获取层级，父节点层级 + 1
        int level = 0;
        if (entity.getParentId() == null) {
            entity.setParentId(0L);
        }
        if (entity.getParentId() > 0) {
            SysDictionary parentDictionary = super.getById(entity.getParentId());
            if (parentDictionary != null) {
                level = parentDictionary.getLevel() + 1;
            }
        }
        SysDictionary sysDictionary = new SysDictionary();
        BeanUtils.copyProperties(entity, sysDictionary);
        sysDictionary.setLevel(level);
        sysDictionary.setStatus(0);
        super.save(sysDictionary);
    }

    @CacheEvict(cacheNames = "dictionary", allEntries = true)
    @Override
    public void update(UpdateDictionaryVO entity) {
        // 校验数据（code不能重复）
        long codeCount = super.count(Wrappers.lambdaQuery(SysDictionary.class)
                .eq(SysDictionary::getCode, entity.getCode())
                .ne(SysDictionary::getId, entity.getId()));
        if (codeCount > 0) {
            throw new GlobalException("字典代码已存在");
        }
        // 修改数据
        SysDictionary sysDictionary = super.getById(entity.getId());
        if (sysDictionary == null) {
            throw new GlobalException("字典不存在", HttpStatus.NOT_FOUND);
        }
        BeanUtils.copyProperties(entity, sysDictionary);
        super.updateById(sysDictionary);
    }

    @CacheEvict(cacheNames = "dictionary", allEntries = true)
    @LogPoint(message = "'删除字典信息：'+#userInfo+',删除列表：'+#ids")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<Long> ids) {
        ids.forEach(id -> {
            SysDictionary sysDictionary = super.getById(id);
            if (sysDictionary == null) {
                log.warn("字典id{}不存在", id);
                throw new GlobalException("字典不存在");
            }
            // 查询出子列表
            List<SysDictionary> childList = super.list(Wrappers.lambdaQuery(SysDictionary.class)
                    .eq(SysDictionary::getParentId, id));
            // 子列表在删除id中没有的数据条数（子列表也删除完的话就放过）
            long childCount = childList.stream()
                    .filter(item -> !ids.contains(item.getId()))
                    .count();
            if (childCount > 0) {
                log.warn("字典id{}存在子字典，删除失败", id);
                throw new GlobalException("字典存在子字典，删除失败");
            }
            // 逻辑删除
            boolean removeFlag = super.removeById(id);
            if (!removeFlag) {
                throw new GlobalException(I18nMsgConstants.OPERATION_FAILED);
            }
        });
    }

    /**
     * 字典列表转换为树形（根据parentId递归）
     *
     * @param sysDictionaries 字典列表
     * @return 树形字典
     */
    private List<TreeDictionaryDTO> convertTree(List<SysDictionary> sysDictionaries) {
        // 转成树形数据
        List<TreeDictionaryDTO> treeDictionaryList = sysDictionaries.stream()
                .map(item -> SuperBeanUtils.copyProperties(item, TreeDictionaryDTO::new))
                .collect(Collectors.toList());
        // 遍历每个节点，并设置子节点
        treeDictionaryList.forEach(item -> {
            List<TreeDictionaryDTO> childMenus = treeDictionaryList.stream()
                    .filter(child -> child.getParentId().equals(item.getId()))
                    .collect(Collectors.toList());
            if (!childMenus.isEmpty()) {
                item.setChildren(childMenus);
            }
        });
        // 返回第一级节点的数据（没有上一级的）
        Set<Long> allIds = sysDictionaries.stream()
                .map(SysDictionary::getId)
                .collect(Collectors.toSet());
        return treeDictionaryList.stream()
                .filter(item -> !allIds.contains(item.getParentId()))
                .collect(Collectors.toList());
    }

    /**
     * 字典列表转换为包含子节点的
     *
     * @param sysDictionaries 字典列表
     * @return 包含子节点的字典
     */
    private List<TreeDictionaryDTO> convertChildren(List<SysDictionary> sysDictionaries) {
        // 获取父节点菜单id
        Set<Long> allIds = sysDictionaries.stream()
                .map(SysDictionary::getParentId)
                .collect(Collectors.toSet());
        // 转成树形数据
        return sysDictionaries.stream()
                .filter(item -> allIds.contains(item.getId()))
                .map(parent -> {
                    TreeDictionaryDTO parentDTO = SuperBeanUtils.copyProperties(parent, TreeDictionaryDTO::new);
                    // 查询下级节点
                    List<TreeDictionaryDTO> childrenDTO = sysDictionaries.stream()
                            .filter(children -> parent.getId().equals(children.getParentId()))
                            .map(children -> SuperBeanUtils.copyProperties(children, TreeDictionaryDTO::new))
                            .collect(Collectors.toList());
                    parentDTO.setChildren(childrenDTO);
                    return parentDTO;
                }).collect(Collectors.toList());
    }

}
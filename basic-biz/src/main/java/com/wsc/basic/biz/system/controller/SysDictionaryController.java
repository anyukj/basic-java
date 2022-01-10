package com.wsc.basic.biz.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.wsc.basic.biz.system.service.SysDictionaryService;
import com.wsc.basic.biz.system.model.dto.dictionary.DictionaryItemDTO;
import com.wsc.basic.biz.system.model.dto.dictionary.TreeDictionaryDTO;
import com.wsc.basic.biz.system.model.vo.dictionary.CreateDictionaryVO;
import com.wsc.basic.biz.system.model.vo.dictionary.QueryDictionaryVO;
import com.wsc.basic.biz.system.model.vo.dictionary.UpdateDictionaryVO;
import com.wsc.basic.core.constant.I18nMsgConstants;
import com.wsc.basic.core.model.Result;
import com.wsc.basic.core.utils.I18nMessages;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * @author 吴淑超
 * @since 2020-07-09 10:58:36
 */
@RestController
@RequestMapping("sysDictionary")
@Api(tags = "字典管理")
@ApiSupport(order = 5, author = "吴淑超")
public class SysDictionaryController {

    @Resource
    private SysDictionaryService sysDictionaryService;

    @ApiOperation("查询字典列表")
    @GetMapping
    public Result<List<TreeDictionaryDTO>> treeDictionary(QueryDictionaryVO entity) {
        return Result.success(sysDictionaryService.treeDictionary(entity));
    }

    @ApiOperation("查询子字典列表")
    @GetMapping("/getChildByCode")
    public Result<List<DictionaryItemDTO>> getChildByCode(String code) {
        return Result.success(sysDictionaryService.getChildByCode(code));
    }

    @ApiOperation("通过主键查询单条数据")
    @GetMapping("{id}")
    public Result<DictionaryItemDTO> queryItem(@PathVariable Serializable id) {
        return Result.success(sysDictionaryService.queryItem(id));
    }

    @ApiOperation("新增数据")
    @PostMapping
    public Result<?> add(@Valid @RequestBody CreateDictionaryVO entity) {
        sysDictionaryService.add(entity);
        return Result.success(I18nMessages.getMessage(I18nMsgConstants.OPERATION_SUCCESS));
    }

    @ApiOperation("修改数据")
    @PutMapping
    public Result<?> update(@Valid @RequestBody UpdateDictionaryVO entity) {
        sysDictionaryService.update(entity);
        return Result.success(I18nMessages.getMessage(I18nMsgConstants.OPERATION_SUCCESS));
    }

    @ApiOperation("删除数据")
    @DeleteMapping
    public Result<?> delete(@RequestParam("ids") List<Long> ids) {
        sysDictionaryService.delete(ids);
        return Result.success(I18nMessages.getMessage(I18nMsgConstants.OPERATION_SUCCESS));
    }

}
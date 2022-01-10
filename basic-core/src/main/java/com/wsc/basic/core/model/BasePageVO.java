package com.wsc.basic.core.model;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 基础分页组件
 *
 * @author 吴淑超
 */
@Data
public class BasePageVO {

    @ApiModelProperty(value = "排序：用下划线分隔字段和类型【字段_（'ascend'|'descend'）】")
    private String sorter;

    @ApiModelProperty(value = "当前页", notes = "默认显示第1页")
    private Integer currentPage;

    @ApiModelProperty(value = "页大小", notes = "默认一页显示10行")
    private Integer pageSize;


    /**
     * 生成分页条件
     */
    public Page genPage() {
        Page page = new Page<>();
        page.setCurrent(this.currentPage == null ? 1 : this.currentPage);
        page.setSize(this.pageSize == null ? 10 : this.pageSize);
        // 加入排序
        OrderItem orderItem = splitSorter(this.sorter);
        if (orderItem != null) {
            page.addOrder(orderItem);
        }
        return page;
    }

    /**
     * 将排序字符串转成OrderItem对象
     *
     * @param value 排序字符串
     * @return 排序对象
     */
    private OrderItem splitSorter(String value) {
        if (StringUtils.isNotBlank(value)) {
            int ascend = value.lastIndexOf("_ascend");
            if (ascend > 0) {
                String sort = value.substring(0, ascend);
                return OrderItem.asc(StringUtils.camelToUnderline(sort));
            }
            int descend = value.lastIndexOf("_descend");
            if (descend > 0) {
                String sort = value.substring(0, descend);
                return OrderItem.desc(StringUtils.camelToUnderline(sort));
            }
        }
        return null;
    }
}

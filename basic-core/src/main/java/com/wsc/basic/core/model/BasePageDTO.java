package com.wsc.basic.core.model;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 吴淑超
 * @since 2020-10-09 11:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasePageDTO<T> {

    @ApiModelProperty(value = "查询数据列表")
    private List<T> records;

    @ApiModelProperty(value = "总数")
    private Long total;

    @ApiModelProperty(value = "每页显示条数")
    private Long size;

    @ApiModelProperty(value = "当前页")
    private Long current;

    public BasePageDTO(IPage<T> page) {
        this.setRecords(page.getRecords());
        this.setTotal(page.getTotal());
        this.setSize(page.getSize());
        this.setCurrent(page.getCurrent());
    }
}

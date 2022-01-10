package com.wsc.basic.biz.system.model.vo.file;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 文件信息表创建实体
 *
 * @author 吴淑超
 * @since 2020-07-13 09:10:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationFileVO {

    @NotNull(message = "业务id不能为空")
    @ApiModelProperty(value = "关联id", required = true)
    private Long relationId;

    @ApiModelProperty(value = "文件名称列表,为空时会删除所有关联文件")
    private List<String> fileNames;

    @NotNull(message = "业务分组不能为空")
    @ApiModelProperty(value = "关联的业务类型（使用枚举）", required = true)
    private Integer relationType;

    @ApiModelProperty(value = "关联业务子类型（一个类型下存在多种业务的时候使用）")
    private String relationChildType;

}

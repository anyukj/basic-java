package com.wsc.basic.biz.system.model.dto.file;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 文件详情
 *
 * @author 吴淑超
 * @date 2021-11-11 14:37
 */
@Data
public class FileItemDTO {

    @ApiModelProperty(value = "文件id")
    private Long id;

    @ApiModelProperty(value = "文件原名称")
    private String originalName;

    @ApiModelProperty(value = "文件新名称（uuid随机）")
    private String newName;

    @ApiModelProperty(value = "文件类型（识别是否是图片或文档...）")
    private String fileType;

    @ApiModelProperty(value = "缩略图BASE64")
    private String thumb;

}

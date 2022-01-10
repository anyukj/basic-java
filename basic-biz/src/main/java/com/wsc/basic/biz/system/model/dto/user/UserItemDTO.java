package com.wsc.basic.biz.system.model.dto.user;

import com.wsc.basic.biz.system.model.dto.file.FileItemDTO;
import com.wsc.basic.biz.system.model.dto.role.RoleItemDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户详情
 *
 * @author 吴淑超
 * @date 2021-11-11 14:37
 */
@Data
public class UserItemDTO {

    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "登录名")
    private String userName;

    @ApiModelProperty(value = "姓名")
    private String fullName;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "文件列表")
    private List<FileItemDTO> files;

    @ApiModelProperty(value = "角色列表")
    private List<RoleItemDTO> roles;

    @ApiModelProperty(value = "状态：0正常、1停用")
    private Integer status;

}

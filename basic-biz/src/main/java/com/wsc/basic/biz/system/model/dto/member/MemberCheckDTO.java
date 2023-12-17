package com.wsc.basic.biz.system.model.dto.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 会员校验
 *
 * @author 吴淑超
 * @date 2023-12-17 15:19
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberCheckDTO {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "过期时间")
    private LocalDateTime expirationTime;

    @ApiModelProperty(value = "用户等级：1试用、2普通、3高级")
    private Integer level;

}

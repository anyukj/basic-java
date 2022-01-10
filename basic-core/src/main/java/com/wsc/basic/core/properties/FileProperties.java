package com.wsc.basic.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件相关
 *
 * @author 吴淑超
 * @since 2020-09-30 9:43
 */
@Data
@Component
@ConfigurationProperties(prefix = "app.file")
public class FileProperties {

    /** 上传文件目录 */
    private String uploadDir = "/";
    /** 缩略图宽度 */
    private int thumbWidth = 200;
    /** 缩略图高度 */
    private int thumbHeight = 200;

}

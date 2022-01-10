package com.wsc.basic.core.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.wsc.basic.core.config.security.UserContext;
import com.wsc.basic.core.exception.GlobalException;
import com.wsc.basic.core.model.TokenEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MyBatis新增或修改数据自动填充
 *
 * @author 吴淑超
 * @since 2020-10-09 16:09
 */
@Slf4j
@Component
public class MybatisMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            TokenEntity user = UserContext.getUser();
            this.strictInsertFill(metaObject, "createUser", user::getUserId, Long.class);
        } catch (GlobalException ignored) {
        }
        this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "version", () -> 1L, Long.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            TokenEntity user = UserContext.getUser();
            this.setUpdateFieldValue(metaObject, "updateUser", user.getUserId());
        } catch (GlobalException ignored) {
        }
        this.setUpdateFieldValue(metaObject, "updateTime", LocalDateTime.now());
    }

    /**
     * 使用自定义赋值（strictUpdateFill严格模式下，如果值已经存在那么不会覆盖）
     * 校验字段是否存在和是否包含 @TableField(fill = FieldFill.UPDATE)
     *
     * @param metaObject metaObject meta object parameter
     * @param fieldName  填充字段
     * @param fieldVal   填充值
     */
    private void setUpdateFieldValue(MetaObject metaObject, String fieldName, Object fieldVal) {
        this.findTableInfo(metaObject).getFieldList()
                .stream()
                .filter(i -> i.getProperty().equals(fieldName) && i.isWithUpdateFill())
                .findFirst()
                .ifPresent(i -> metaObject.setValue(fieldName, fieldVal));
    }

}

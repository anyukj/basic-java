package com.wsc.basic.core.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * JavaBeans操作工具
 *
 * @author 吴淑超
 * @since 2020-09-02 9:37
 */
public class SuperBeanUtils extends BeanUtils {

    /**
     * 实体属性复制
     *
     * @param source 数据源
     * @param target 目标
     * @param <T>    目标实体类型
     * @return 目标对象
     */
    public static <T> T copyProperties(Object source, Supplier<T> target) {
        T t = target.get();
        if (source != null) {
            copyProperties(source, t);
        }
        return t;
    }

    /**
     * 跳过null值进行复制
     *
     * @param source 数据源
     * @param target 目标
     */
    public static void copyPropertiesIgnoreNull(Object source, Object target) {
        copyProperties(source, target, getNullPropertyName(source));
    }

    /**
     * 列表属性复制
     *
     * @param sources 数据源列表
     * @param target  目标
     * @param <T>     目标实体类型
     * @return 目标对象
     */
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target) {
        List<T> list = new ArrayList<>(sources.size());
        for (Object source : sources) {
            list.add(copyProperties(source, target));
        }
        return list;
    }

    /**
     * 获取null值字段
     *
     * @param source 实体
     * @return 字段数组
     */
    private static String[] getNullPropertyName(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}

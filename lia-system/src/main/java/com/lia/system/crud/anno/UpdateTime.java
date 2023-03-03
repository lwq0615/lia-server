package com.lia.system.crud.anno;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在实体类属性上配置@UpdateTime注解，意味着该字段为更新时间字段
 * 在编辑时该字段将不参与编辑
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Between
public @interface UpdateTime {
}

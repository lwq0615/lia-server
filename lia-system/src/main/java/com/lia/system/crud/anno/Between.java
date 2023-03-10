package com.lia.system.crud.anno;

import java.lang.annotation.*;


/**
 * 在实体类属性上配置@Between注解，意味着做查询时会自动转换为区间查询
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Between {
}

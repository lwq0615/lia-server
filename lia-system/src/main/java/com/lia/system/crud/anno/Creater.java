package com.lia.system.crud.anno;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在实体类属性上配置@Creater注解，意味着该字段为创建人字段
 * 在新增时会自动将当前用户id作为值写入该字段，注意：原来的值将被覆盖
 * 在编辑时该字段将不参与编辑
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Creater {
}

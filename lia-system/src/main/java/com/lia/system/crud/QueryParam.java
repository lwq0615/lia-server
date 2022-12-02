package com.lia.system.crud;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lia.system.crud.anno.CreateBy;
import com.lia.system.crud.anno.DateType;
import com.lia.system.crud.anno.Like;
import com.lia.system.crud.anno.UpdateTime;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QueryParam{

    private Object entity;

    public QueryParam(Object entity) {
        this.entity = entity;
    }

    public List<Column> getSelectColumn(){
        List<Column> columns = new ArrayList<>();
        Class eClass = entity.getClass();
        for (Field field : eClass.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if(field.get(entity) == null){
                    continue;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            // 获取与数据库映射的字段名
            String columnName;
            TableField tableField = field.getAnnotation(TableField.class);
            if(tableField != null){
                columnName = tableField.value();
            }else{
                columnName = field.getName();
            }
            try {
                Object value = field.get(entity);
                // 如果该字段是日期类型并且添加了DateType，则做范围查询
                if(AnnotationUtils.findAnnotation(field, DateType.class) != null){
                    List<String> btw = new ArrayList<>();
                    Collections.addAll(btw, ((String) value).split(","));
                    value = btw;
                }
                // 值为null则不参与查询
                columns.add(new Column(columnName, value, field.getAnnotation(Like.class) != null));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return columns;
    }

    public List<Column> getUpdateColumn(){
        List<Column> columns = new ArrayList<>();
        Class eClass = entity.getClass();
        for (Field field : eClass.getDeclaredFields()) {
            field.setAccessible(true);
            // 创建人与创建时间和主键字段不参与更新
            if(field.getAnnotation(CreateBy.class) != null || field.getAnnotation(UpdateTime.class) != null
            || field.getAnnotation(TableId.class) != null){
                continue;
            }
            // 获取与数据库映射的字段名
            String columnName;
            TableField tableField = field.getAnnotation(TableField.class);
            if(tableField != null){
                columnName = tableField.value();
            }else{
                columnName = field.getName();
            }
            try {
                columns.add(new Column(columnName, field.get(entity), false));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return columns;
    }


    public Column getIdColumn(){
        for (Field field : entity.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            // 创建人与创建时间和主键字段不参与更新
            if(field.getAnnotation(TableId.class) != null){
                // 获取与数据库映射的字段名
                String columnName;
                TableField tableField = field.getAnnotation(TableField.class);
                if(tableField != null){
                    columnName = tableField.value();
                }else{
                    columnName = field.getName();
                }
                try {
                    return new Column(columnName, field.get(entity), false);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    public static class Column{

        /**
         * 字段名
         */
        private String name;

        /**
         * 字段值
         */
        private Object value;

        /**
         * 是否模糊查询
         */
        private boolean isLike;

        public Column(String name, Object value,boolean isLike) {
            this.name = name;
            this.value = value;
            this.isLike = isLike;
        }

        public String getName() {
            return name;
        }

        public Object getValue() {
            return value;
        }

        public boolean isLike() {
            return isLike;
        }
    }

}

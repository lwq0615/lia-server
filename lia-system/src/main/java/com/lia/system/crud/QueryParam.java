package com.lia.system.crud;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lia.system.crud.anno.*;
import com.lia.system.utils.StrUtils;
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
                // 值为null或者配置了@Pass注解则不参与查询
                if(field.get(entity) == null || field.getAnnotation(Pass.class) != null){
                    continue;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            // 获取与数据库映射的字段名
            String columnName;
            TableId tableId = field.getAnnotation(TableId.class);
            TableField tableField = field.getAnnotation(TableField.class);
            if(tableId != null){
                columnName = tableId.value();
            }
            else if(tableField != null){
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
            // 创建人与创建时间和更新时间和主键字段不参与更新
            if(field.getAnnotation(CreateBy.class) != null || field.getAnnotation(UpdateTime.class) != null
            || field.getAnnotation(TableId.class) != null || field.getAnnotation(CreateTime.class) != null
            || field.getAnnotation(Pass.class) != null){
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
            TableId tableId = field.getAnnotation(TableId.class);
            if(tableId != null){
                // 获取与数据库映射的字段名
                String columnName;
                if(!StrUtils.isEmpty(tableId.value())){
                    columnName = tableId.value();
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

        @Override
        public String toString() {
            return "Column{" +
                    "name='" + name + '\'' +
                    ", value=" + value +
                    ", isLike=" + isLike +
                    '}';
        }
    }

}

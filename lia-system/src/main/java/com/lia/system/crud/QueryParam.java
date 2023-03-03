package com.lia.system.crud;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lia.system.crud.anno.*;
import com.lia.system.crud.exception.GetMethodNotFoundException;
import com.lia.system.utils.StrUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class QueryParam {

    private Object entity;

    public QueryParam(Object entity) {
        this.entity = entity;
    }

    public String[] getReturnColumn() {
        List<String> columns = new ArrayList<>();
        for (Field field : entity.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getAnnotation(Pass.class) != null) {
                continue;
            }
            // 获取与数据库映射的字段名
            String columnName;
            TableId tableId = field.getAnnotation(TableId.class);
            TableField tableField = field.getAnnotation(TableField.class);
            if (tableId != null && !StrUtils.isEmpty(tableId.value())) {
                columnName = tableId.value();
            } else if (tableField != null && !StrUtils.isEmpty(tableField.value())) {
                columnName = tableField.value();
            } else {
                columnName = field.getName();
            }
            columns.add(columnName);
        }
        String[] res = new String[columns.size()];
        for (int i = 0; i < columns.size(); i++) {
            res[i] = columns.get(i);
        }
        return res;
    }

    public List<Column> getSelectColumn() {
        List<Column> columns = new ArrayList<>();
        Class eClass = entity.getClass();
        for (Field field : eClass.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                // 值为null或者配置了@Pass注解则不参与查询
                if (field.get(entity) == null || field.getAnnotation(Pass.class) != null) {
                    continue;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            // 获取与数据库映射的字段名
            String columnName;
            TableId tableId = field.getAnnotation(TableId.class);
            TableField tableField = field.getAnnotation(TableField.class);
            if (tableId != null && !StrUtils.isEmpty(tableId.value())) {
                columnName = tableId.value();
            } else if (tableField != null && !StrUtils.isEmpty(tableField.value())) {
                columnName = tableField.value();
            } else {
                columnName = field.getName();
            }
            Object value = getColumnValue(field);
            // 如果该字段添加了Between，则做范围查询
            if (AnnotationUtils.findAnnotation(field, Between.class) != null) {
                List<String> btw = new ArrayList<>();
                Collections.addAll(btw, ((String) value).split(","));
                value = btw;
            }
            columns.add(new Column(columnName, value, field.getAnnotation(Like.class) != null));
        }
        return columns;
    }

    public List<Column> getUpdateColumn() {
        List<Column> columns = new ArrayList<>();
        Class eClass = entity.getClass();
        for (Field field : eClass.getDeclaredFields()) {
            field.setAccessible(true);
            // 创建人与创建时间和更新时间和主键字段不参与更新
            if (field.getAnnotation(Creater.class) != null || field.getAnnotation(UpdateTime.class) != null
                    || field.getAnnotation(TableId.class) != null || field.getAnnotation(CreateTime.class) != null
                    || field.getAnnotation(Pass.class) != null) {
                continue;
            }
            // 获取与数据库映射的字段名
            String columnName;
            TableField tableField = field.getAnnotation(TableField.class);
            if (tableField != null && !StrUtils.isEmpty(tableField.value())) {
                columnName = tableField.value();
            } else {
                columnName = field.getName();
            }
            columns.add(new Column(columnName, getColumnValue(field), false));
        }
        return columns;
    }


    public Column getIdColumn() {
        for (Field field : entity.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            TableId tableId = field.getAnnotation(TableId.class);
            if (tableId != null) {
                // 获取与数据库映射的字段名
                String columnName;
                if (!StrUtils.isEmpty(tableId.value())) {
                    columnName = tableId.value();
                } else {
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


    private Object getColumnValue(Field filed) {
        String getMethodName = "get" + StrUtils.firstUp(filed.getName());
        try {
            Method getMethod = filed.getDeclaringClass().getDeclaredMethod(getMethodName);
            return getMethod.invoke(entity);
        } catch (NoSuchMethodException e) {
            throw new GetMethodNotFoundException(filed);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Column {

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

        public Column(String name, Object value, boolean isLike) {
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

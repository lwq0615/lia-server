package com.lia.system.crud;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class QueryParam{

    private List<Column> columns = new ArrayList<>();

    public QueryParam(Object entity) {
        Class eClass = entity.getClass();
        for (Field field : eClass.getDeclaredFields()) {
            field.setAccessible(true);
            // 值为null则不参与查询
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
                if(field.getAnnotation(DateType.class) != null){
                    List<String> btw = new ArrayList<>();
                    Collections.addAll(btw, ((String) value).split(","));
                    value = btw;
                }
                this.columns.add(new Column(columnName, value, field.getAnnotation(Like.class) != null));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public Column getColumn(int i){
        return this.columns.get(i);
    }

    public int getSize(){
        return this.columns.size();
    }


    public class Column{

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

package com.lia.system.crud;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lia.system.exception.HttpException;
import com.lia.system.security.LoginUser;
import org.springframework.dao.DuplicateKeyException;

import java.lang.reflect.Field;
import java.util.List;

public class BaseService<E> {

    private BaseMapper<E> baseMapper;

    public BaseService(BaseMapper<E> baseMapper) {
        this.baseMapper = baseMapper;
    }

    public List<E> selectList(E entity) {
        QueryWrapper<E> queryWrapper = new QueryWrapper<>();
        QueryParam queryParam = new QueryParam(entity);
        for (int i = 0; i < queryParam.getSize(); i++) {
            QueryParam.Column column = queryParam.getColumn(i);
            String name = column.getName();
            Object value = column.getValue();
            if(value instanceof List){
                queryWrapper.between(name, ((List) value).get(0), ((List) value).get(1));
            }
            // 模糊查询
            else if(column.isLike()){
                queryWrapper.like(name, value);
            }else{
                queryWrapper.eq(name, value);
            }
        }
        return baseMapper.selectList(queryWrapper);
    }

    public E selectOne(E entity) {
        QueryWrapper<E> queryWrapper = new QueryWrapper<>();
        QueryParam queryParam = new QueryParam(entity);
        for (int i = 0; i < queryParam.getSize(); i++) {
            queryWrapper.eq(queryParam.getColumn(i).getName(), queryParam.getColumn(i).getValue());
        }
        return baseMapper.selectOne(queryWrapper);
    }


    /**
     * 新增和编辑
     * @param entity
     * @return 结果信息
     */
    public String save(E entity) {
        try {
            boolean hasId = false;
            for (Field field : entity.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                // 判断有没有值为null的必填字段
                if (field.getAnnotation(Required.class) != null && field.get(entity) == null) {
                    throw new HttpException(400, "缺少参数"+field.getName());
                }
                // 判断是否有id
                if (field.getAnnotation(TableId.class) != null && field.get(entity) != null) {
                    hasId = true;
                }
            }
            // 如果id字段有值，则根据id匹配编辑
            if(hasId){
                return baseMapper.updateById(entity) > 0 ? "success" : "error";
            }else{
                // 如果有createBy字段，则新增时默认填充当前登录用户
                try {
                    Field createBy = entity.getClass().getField("createBy");
                    if(createBy.get(entity) == null){
                        createBy.setAccessible(true);
                        createBy.set(entity, LoginUser.getLoginUserId());
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                return baseMapper.insert(entity) > 0 ? "success" : "error";
            }
        }catch (DuplicateKeyException e) {
            String[] split = e.getCause().getMessage().split(" ");
            String replace = split[split.length - 1].replace("'", "");
            String name = replace.split("\\.")[1].split("-")[1];
            return name + "重复";
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "error";
        }
    }

    public int deleteByIds(List ids) {
        if (ids != null && ids.size() > 0) {
            return baseMapper.deleteBatchIds(ids);
        } else {
            return 0;
        }
    }

}

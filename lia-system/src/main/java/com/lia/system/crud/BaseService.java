package com.lia.system.crud;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lia.system.crud.anno.*;
import com.lia.system.crud.exception.NullValueOfIdException;
import com.lia.system.crud.exception.NoEntityException;
import com.lia.system.crud.exception.NotFoundBaseMapperException;
import com.lia.system.crud.exception.UniqueException;
import com.lia.system.result.SysResult;
import com.lia.system.result.exception.HttpException;
import com.lia.system.security.LoginUser;
import com.lia.system.utils.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DuplicateKeyException;

import javax.annotation.PostConstruct;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public abstract class BaseService<E> {


    @Autowired
    private ApplicationContext applicationContext;

    private BaseMapper<E> baseMapper;

    private static ConcurrentHashMap<Class, BaseMapper> mappers;


    @PostConstruct
    private void init() {
        Type type = this.getClass().getGenericSuperclass();
        // 配置了泛型参数
        if (type instanceof ParameterizedType) {
            Class entityClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
            this.baseMapper = this.getBaseMapper(entityClass);
            if (this.baseMapper == null) {
                throw new NotFoundBaseMapperException(entityClass);
            }
        } else {
            throw new NoEntityException();
        }
    }

    private BaseMapper<E> getBaseMapper(Class clazz) {
        if (mappers != null) {
            return mappers.get(clazz);
        }
        mappers = new ConcurrentHashMap<>();
        // values存放每个继承了BaseMapper的被spring代理后的对象
        Collection<BaseMapper> values = applicationContext.getBeansOfType(BaseMapper.class).values();
        for (BaseMapper mapper : values) {
            // mapperInterface为自己定义的mapper接口
            Class mapperInterface = mapper.getClass().getInterfaces()[0];
            for (Type impInterface : mapperInterface.getGenericInterfaces()) {
                // impInterface为定义mapper接口时继承的接口
                // 主要是要寻找继承BaseMapper接口时传入的泛型
                if (impInterface.getTypeName().startsWith(BaseMapper.class.getName() + "<")) {
                    // 取出BaseMapper接口泛型的数据
                    if (impInterface instanceof ParameterizedType) {
                        Class entityClass = (Class) ((ParameterizedType) impInterface).getActualTypeArguments()[0];
                        mappers.put(entityClass, mapper);
                        break;
                    }
                }
            }
        }
        return mappers.get(clazz);
    }


    /**
     * 列表查询
     *
     * @param entity 查询参数
     * @param desc   是否降序
     * @return
     */
    public List<E> selectList(E entity, boolean desc) {
        QueryWrapper<E> queryWrapper = new QueryWrapper<>();
        QueryParam queryParam = new QueryParam(entity);
        if (desc) {
            queryWrapper.orderByDesc(queryParam.getIdColumn().getName());
        }
        List<QueryParam.Column> columns = queryParam.getSelectColumn();
        queryWrapper.select(queryParam.getReturnColumn());
        for (int i = 0; i < columns.size(); i++) {
            QueryParam.Column column = columns.get(i);
            String name = column.getName();
            Object value = column.getValue();
            if (value instanceof List) {
                queryWrapper.between(name, ((List) value).get(0), ((List) value).get(1));
            }
            // 模糊查询
            else if (column.isLike()) {
                queryWrapper.like(name, value);
            } else {
                queryWrapper.eq(name, value);
            }
        }
        return baseMapper.selectList(queryWrapper);
    }


    /**
     * 列表查询，默认升序
     */
    public List<E> selectList(E entity) {
        return this.selectList(entity, false);
    }


    /**
     * 查询一条记录
     *
     * @param entity
     */
    public E selectOne(E entity) {
        QueryWrapper<E> queryWrapper = new QueryWrapper<>();
        QueryParam queryParam = new QueryParam(entity);
        List<QueryParam.Column> columns = queryParam.getSelectColumn();
        for (int i = 0; i < columns.size(); i++) {
            queryWrapper.eq(columns.get(i).getName(), columns.get(i).getValue());
        }
        return baseMapper.selectOne(queryWrapper);
    }


    /**
     * 新增和编辑
     * @param entity
     * @return 结果信息
     */
    public int save(E entity) {
        QueryParam queryParam = new QueryParam(entity);
        QueryParam.Column idColumn = queryParam.getIdColumn();
        // 如果id字段有值，则根据id匹配编辑
        if (idColumn != null && idColumn.getValue() != null) {
            return this.update(entity);
        } else {
            return this.insert(entity);
        }
    }

    /**
     * 新增
     */
    public int insert(E entity) {
        return this.insert(entity, false);
    }


    /**
     * 新增
     * @param entity 实体类
     * @param returnId 是否返回自增id
     * @return
     */
    public int insert(E entity, boolean returnId) {
        E entityCopy = (E)JSON.parseObject(JSON.toJSONString(entity), entity.getClass());
        QueryParam queryParam = new QueryParam(entityCopy);
        try {
            // 如果id有值，清空id的值
            QueryParam.Column idColumn = queryParam.getIdColumn();
            if (idColumn != null && idColumn.getValue() != null) {
                entityCopy.getClass().getDeclaredField(idColumn.getName()).set(entityCopy, null);
            }
            for (Field field : entityCopy.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if(field.getAnnotation(Pass.class) != null){
                    field.set(entityCopy, null);
                    continue;
                }
                // 判断有没有值为null的必填字段
                if (field.getAnnotation(Required.class) != null && field.get(entityCopy) == null) {
                    throw new HttpException("缺少参数" + field.getName());
                }
                // 如果有@CreateBy字段，则新增时默认填充当前登录用户
                if (field.getAnnotation(Creater.class) != null) {
                    field.setAccessible(true);
                    field.set(entityCopy, LoginUser.getLoginUserId());
                }
                if (field.getAnnotation(UpdateTime.class) != null) {
                    field.setAccessible(true);
                    field.set(entityCopy, null);
                }
                if (field.getAnnotation(CreateTime.class) != null) {
                    field.setAccessible(true);
                    field.set(entityCopy, null);
                }
            }
            int success = baseMapper.insert(entityCopy);
            // 返回自增id
            if(returnId){
                Field idField = entity.getClass().getDeclaredField(idColumn.getName());
                idField.setAccessible(true);
                Object idValue = idField.get(entityCopy);
                idField.set(entity, idValue);
            }
            return success;
        } catch (DuplicateKeyException e) {
            String[] split = e.getCause().getMessage().split(" ");
            String name = split[split.length - 1].replace("'", "");
            throw new UniqueException(name, "‘“+name+“‘字段值重复");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new HttpException(SysResult.SERVER_ERROR);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new HttpException(SysResult.SERVER_ERROR);
        }
    }


    /**
     * 编辑
     */
    public int update(E entity) {
        QueryParam queryParam = new QueryParam(entity);
        try {
            for (Field field : entity.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                // 判断有没有值为null的必填字段
                if (field.getAnnotation(Required.class) != null && field.get(entity) == null) {
                    throw new HttpException("缺少参数" + field.getName());
                }
            }
            QueryParam.Column idColumn = queryParam.getIdColumn();
            // 如果id字段值为空，抛出异常
            if (idColumn == null || idColumn.getValue() == null) {
                throw new NullValueOfIdException();
            }
            // 如果id字段有值，则根据id匹配编辑
            UpdateWrapper<E> updateWrapper = new UpdateWrapper<>();
            List<QueryParam.Column> columns = queryParam.getUpdateColumn();
            updateWrapper.eq(idColumn.getName(), idColumn.getValue());
            for (QueryParam.Column column : columns) {
                updateWrapper.set(column.getName(), column.getValue());
            }
            return baseMapper.update(null, updateWrapper);
        } catch (DuplicateKeyException e) {
            String[] split = e.getCause().getMessage().split(" ");
            String name = split[split.length - 1].replace("'", "");
            throw new UniqueException(name, "‘“+name+“‘字段值重复");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new HttpException(SysResult.SERVER_ERROR);
        }
    }


    /**
     * 根据id列表删除数据
     * @param ids
     * @return
     */
    public int deleteByIds(List ids) {
        if (ids != null && ids.size() > 0) {
            return baseMapper.deleteBatchIds(ids);
        } else {
            return 0;
        }
    }

}

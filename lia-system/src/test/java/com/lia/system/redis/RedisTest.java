package com.lia.system.redis;


import com.lia.system.crud.anno.DateType;
import com.lia.system.modules.auth.SysAuth;
import com.lia.system.modules.param.SysParamMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;

@SpringBootTest
public class RedisTest {

    @Autowired
    private SysParamMapper sysParamMapper;

    @Test
    public void test(){
//        BaseService<SysParam> baseService = new BaseService(sysParamMapper);
//        SysParam sysParam = new SysParam();
//        sysParam.setName("enable_register12");
//        System.out.println(baseService.save(sysParam));
        try {
            Field createTime = SysAuth.class.getDeclaredField("createTime");
            System.out.println(AnnotationUtils.findAnnotation(createTime, DateType.class));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

}

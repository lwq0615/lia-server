package com.lia.system.redis;


import com.lia.system.crud.anno.DateType;
import com.lia.system.entity.SysAuth;
import com.lia.system.modules.auth.SysAuthService;
import com.lia.system.modules.param.SysParamMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;

@SpringBootTest
public class RedisTest {

    @Autowired
    private SysAuthService sysParamMapper;

    @Test
    public void test(){
        System.out.println(sysParamMapper.findSysAuthByRoleId(999));
    }

}

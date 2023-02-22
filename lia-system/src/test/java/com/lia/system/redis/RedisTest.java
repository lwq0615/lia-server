package com.lia.system.redis;


import com.lia.system.modules.auth.SysAuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisTest {

    @Autowired
    private SysAuthService sysParamMapper;

    @Test
    public void test(){
        System.out.println(sysParamMapper.findSysAuthByRoleId(999));
    }

}

package com.lia.system.redis;


import com.lia.system.modules.user.SysUserMapper;
import com.lia.system.utils.ArrayUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class RedisTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void test(){
    }

}

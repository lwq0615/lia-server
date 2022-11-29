package com.lia.system.redis;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lia.system.modules.auth.SysAuthMapper;
import com.lia.system.modules.file.SysFile;
import com.lia.system.modules.file.SysFileMapper;
import com.lia.system.modules.message.SysMessage;
import com.lia.system.modules.message.SysMessageMapper;
import com.lia.system.modules.user.SysUser;
import com.lia.system.modules.user.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.sql.SQLException;
import java.sql.Wrapper;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
public class RedisTest {

    @Test
    public void test(){

        SysUser user = (SysUser) Redis.getRedisTemplateByDb(RedisDb.USERTOKEN).opsForValue().get("test");
        System.out.println(user);

    }

}

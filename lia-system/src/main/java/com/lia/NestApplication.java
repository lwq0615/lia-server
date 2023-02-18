package com.lia;

import com.lia.system.utils.SpringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


/**
 * 启动程序
 * @author liweiqiang
 */
@SpringBootApplication
public class NestApplication
{
    public static void main(String[] args)
    {
        SpringUtils.setApplicationContext(SpringApplication.run(NestApplication.class, args));
        System.out.println("(♥◠‿◠)ﾉﾞ  启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}

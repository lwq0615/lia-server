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
        ApplicationContext applicationContext = SpringApplication.run(NestApplication.class, args);
        SpringUtils.setApplicationContext(applicationContext);
        System.out.println("(♥◠‿◠)ﾉﾞ  启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}

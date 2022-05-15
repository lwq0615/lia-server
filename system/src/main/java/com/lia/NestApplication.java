package com.lia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


/**
 * 启动程序
 *
 * @author liweiqiang
 */
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class NestApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(NestApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}

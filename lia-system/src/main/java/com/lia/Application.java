package com.lia;

import com.lia.system.utils.SpringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 启动程序
 * @author liweiqiang
 */
@SpringBootApplication
public class Application
{
    public static void main(String[] args)
    {
        SpringUtils.initApplicationContext(SpringApplication.run(Application.class, args));
        System.out.println("(♥◠‿◠)ﾉﾞ  启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}

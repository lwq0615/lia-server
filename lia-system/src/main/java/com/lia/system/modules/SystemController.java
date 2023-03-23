package com.lia.system.modules;

import ch.qos.logback.core.util.SystemInfo;
import com.alibaba.fastjson2.JSON;
import com.lia.system.utils.SystemUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/system")
public class SystemController {

    /**
     * 获取系统CPU内存信息
     */
    @GetMapping("/info")
    public Map systemInfo(){
        return JSON.parseObject(JSON.toJSONString(SystemUtils.getCpuInfo()));
    }

}

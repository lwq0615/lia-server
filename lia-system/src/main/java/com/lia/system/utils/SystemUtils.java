package com.lia.system.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class SystemUtils {

    public static SystemInfo getCpuInfo() {
        final long GB = 1024 * 1024 * 1024;
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        String osJson = JSON.toJSONString(operatingSystemMXBean);
        JSONObject jsonObject = JSON.parseObject(osJson);
        // CPU占用率
        double systemCpuLoad = twoDecimal(jsonObject.getDouble("systemCpuLoad") * 100);
        Long totalPhysicalMemorySize = jsonObject.getLong("totalPhysicalMemorySize");
        Long freePhysicalMemorySize = jsonObject.getLong("freePhysicalMemorySize");
        // 总内存
        double totalMemory = twoDecimal(1.0 * totalPhysicalMemorySize / GB);
        // 剩余内存
        double freeMemory = twoDecimal(1.0 * freePhysicalMemorySize / GB);
        // 内存占用率
        double memoryUseRatio = twoDecimal(1.0 * (totalPhysicalMemorySize - freePhysicalMemorySize) / totalPhysicalMemorySize * 100);
        return new SystemInfo(systemCpuLoad, memoryUseRatio, totalMemory, freeMemory);

    }

    public static double twoDecimal(double doubleValue) {
        BigDecimal bigDecimal = new BigDecimal(doubleValue).setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}


@Data
@AllArgsConstructor
class SystemInfo{

    /**
     * CPU占用率
     */
    private double cpuPercent;

    /**
     * 内存占用率
     */
    private double momoryPercent;

    /**
     * 总内存（GB）
     */
    private double totalMemory;

    /**
     * 剩余内存（GB）
     */
    private double freeMemory;
}

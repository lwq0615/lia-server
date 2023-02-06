package com.lia.system.redis;

public enum RedisDb {

    SYSTEM(1, "系统功能相关");

    private final int dbIndex;
    private final String remark;

    private RedisDb(int dbIndex, String remark){
        this.dbIndex = dbIndex;
        this.remark = remark;
    }

    public int dbIndex(){
        return this.dbIndex;
    }

    public String remark(){
        return this.remark;
    }
}

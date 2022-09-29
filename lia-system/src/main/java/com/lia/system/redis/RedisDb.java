package com.lia.system.redis;

public enum RedisDb {

    USERTOKEN(1, "存放用户身份验证token");

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

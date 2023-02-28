package com.lia.system.modules.file;

public enum UploadDir {

    IMAGE("image"),
    FILE("file");

    private String dirName;

    UploadDir(String dirName){
        this.dirName = dirName;
    }

    public String getDirName(){
        return this.dirName;
    }

}

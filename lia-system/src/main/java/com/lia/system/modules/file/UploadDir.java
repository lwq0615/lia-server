package com.lia.system.modules.file;

public enum UploadDir {

    IMAGE("image"),
    FILE("file");

    private String dirNamme;

    UploadDir(String dirName){
        this.dirNamme = dirName;
    }

    public String getDirNamme(){
        return this.dirNamme;
    }

}

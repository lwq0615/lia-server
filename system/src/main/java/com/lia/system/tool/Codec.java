package com.lia.system.tool;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Codec {

    /***
     * 利用Apache的工具类实现SHA-256加密
     * @param str 需要加密的字符串
     * @return 加密后的报文
     */
    public static String toSHA256(String str) {
        if(str == null || str.equals("")){
            return null;
        }
        MessageDigest messageDigest;
        String encdeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
            encdeStr = Hex.encodeHexString(hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encdeStr;
    }

}

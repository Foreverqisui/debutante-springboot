package com.pc;

import com.pc.common.MD5;
import org.junit.Test;

import java.util.Random;

public class Md5Test {

    @Test
    public void md5Test(){
        //签名秘钥 通过MD5加密+随机数
        String res = "";
        String str = "一生一世只爱冀文静，他是我唯一的love";
        int rands = new Random().nextInt(str.length());
        for (int i = rands; i < str.length(); i++) {
            int rand = new Random().nextInt(1000);
            String md = String.valueOf(str.charAt(i));
            String encrypt = MD5.encrypt(md);
            res = encrypt + rand * i + encrypt;
        }
        System.out.println(res);
    }
}

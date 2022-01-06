package cn.cqsztech.string;

import com.alibaba.druid.sql.dialect.sqlserver.ast.SQLServerOutput;

import javax.sound.midi.Soundbank;

/**
 * ccmars
 * 2022/1/6
 **/
public class StringTest {
    /**
     * java中的字符串也是\0结尾的
     * @param args
     */
    public static void main(String[] args) {
        String a = "hello\0";
        String b = "hello";
        System.out.println(a );
        System.out.println(b.length());
        System.out.println(a.equals(b));
    }
}

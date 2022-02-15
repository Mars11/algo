package cn.cqsztech.sqlmap;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * ccmars
 * 2022/2/14
 **/
public class TimeUtl {
    public static void main(String[] args) {
        Timestamp t = new Timestamp(System.currentTimeMillis());
        System.out.println(t);
    }
}

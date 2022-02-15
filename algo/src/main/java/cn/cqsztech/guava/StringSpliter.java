package cn.cqsztech.guava;

import com.google.common.base.Joiner;

import java.util.HashMap;
import java.util.Map;

/**
 * ccmars
 * 2021/12/29
 **/
public class StringSpliter {
    public static final  Joiner.MapJoiner MAP_JOINER = Joiner.on("&").withKeyValueSeparator("=");
    public static String getStrFromMap(Map map){
        return MAP_JOINER.join(map);
    }
    public static void main(String[] args) {
        Map params = new HashMap();
        params.put("name","zhangsan");
        params.put("age",12);
        // assert name=zhangsan&age=12
        System.out.println(getStrFromMap(params));
    }
}

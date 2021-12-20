package cn.cqsztech.config.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * ccmars
 * 2021/12/20
 **/
public class RateLimiterTest {
    public static void main(String[] args) {
        //qps
        RateLimiter r = RateLimiter.create(2);
        //5秒内获取
        while (true){
            if(r.tryAcquire(5,TimeUnit.MILLISECONDS)){
                System.out.println(System.currentTimeMillis());
            }else {
                System.out.println("i'm busy"+System.currentTimeMillis());
            }
        }
    }
}

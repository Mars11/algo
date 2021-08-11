package cn.cqsztech.juc;

import java.util.HashMap;
import java.util.Random;

/**
 * ccmars
 * 2021/8/11
 **/
public class TestVolatile {
   static volatile int  flag = 0;
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.println(flag);
                }
            }
        });
        thread.start();
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    flag = new Random().nextInt();
                }
            }
        });
        thread2.start();
    }
}

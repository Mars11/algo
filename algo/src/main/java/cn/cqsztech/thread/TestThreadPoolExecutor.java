package cn.cqsztech.thread;

import cn.hutool.core.thread.ThreadFactoryBuilder;

import java.util.Random;
import java.util.concurrent.*;

/**
 * ccmars
 * 2021/7/1
 **/
public class TestThreadPoolExecutor {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(Integer.toBinaryString(-1<<29));
        System.out.println(Integer.toBinaryString(-1));
//        ThreadPoolExecutor threadPoolExecutor  =
//                new ThreadPoolExecutor(10,
//                        100,
//                        100,
//                        TimeUnit.SECONDS,
//                        new LinkedBlockingDeque<>(),
//                        ThreadFactoryBuilder.create().build(),
//                        new ThreadPoolExecutor.CallerRunsPolicy());
//        for (int i = 0; i < 100; i++) {
//            threadPoolExecutor.submit(new MyTask());
//        }
//        Thread.sleep(1000*100);
    }

    static class MyTask implements Runnable{


        @Override
        public void run() {
            System.out.println(Thread.currentThread()+"hello");
            try {
                Thread.sleep(new Random().nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

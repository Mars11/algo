package cn.cqsztech.juc;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * ccmars
 * 2021/8/4
 *
 **/
public class ConcurrentLinkedQueueTester {
    public static void main(String[] args) {

//        Class c = ConcurrentLinkedQueue.class.getClass();
//        ConcurrentLinkedQueue<String> clq = null;
//        try {
//            clq= (ConcurrentLinkedQueue) c.newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//
//        try {
//            Field f= c.getDeclaredField("tail");
//            Field head = c.getDeclaredField("head");
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//        System.out.println();

        ConcurrentLinkedQueue<String> clq = null;
        clq =new ConcurrentLinkedQueue<>();
        clq.add("hello");
        clq.add("b");
        clq.add("c");
    }
}

package cn.cqsztech.thread;

/**
 * ccmars
 * 测试线程阻塞状态下是否会执行finally里面的内容
 * 2021/6/29
 **/
public class TestFinally {
    public static void main(String[] args) {
        boolean b=true?false:true==true?false:true;
        System.out.println(b);
        Object o = new Object();
        try {
            synchronized (o){
                try {
                    o.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }finally {
            System.out.println("finally i processed");
        }
    }
}

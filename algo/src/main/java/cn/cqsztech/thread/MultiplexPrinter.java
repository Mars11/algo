package cn.cqsztech.thread;


/**
 * ccmars
 * 2021/6/8
 * 两线程交替打印数字
 **/
public class MultiplexPrinter {
    volatile static int a = 0 ;
   static class PrinterA implements  Runnable{

        @Override
        public void run() {
            while (true) {
                if(a%2 ==0){
                        System.out.println(Thread.currentThread().getName() + "-->a'value --->" + a);
                        a++;
                }
            }
        }
    }
   static class PrinterB implements  Runnable{

        @Override
        public void run() {
            while (true) {
                if(a%2 ==1){
                        System.out.println(Thread.currentThread().getName() + "-->a'value --->" + a);
                        a++;
                }

            }
        }
    }

    public static void main(String[] args) {
        new Thread(new PrinterA()).start();
        new Thread(new PrinterB()).start();
    }
}

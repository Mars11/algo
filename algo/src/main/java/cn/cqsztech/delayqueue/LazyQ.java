package cn.cqsztech.delayqueue;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * ccmars
 * 2021/6/9
 **/
public class LazyQ {
    public static void main(String[] args) throws InterruptedException {

        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
        System.out.println("1 "+scriptEngine.hashCode());
        ScriptEngineManager scriptEngineManager1 = new ScriptEngineManager();
        ScriptEngine scriptEngine1 = scriptEngineManager1.getEngineByName("JavaScript");
        System.out.println("2 "+scriptEngine1.hashCode());
        DelayQueue delayQueue = new DelayQueue();
        delayQueue.add(new DelayObj(System.currentTimeMillis()+1000));
        delayQueue.add(new DelayObj(System.currentTimeMillis()+4000));
        delayQueue.add(new DelayObj(System.currentTimeMillis()+7000));
        System.out.println("开始弹出数据");
        while (delayQueue.size()>0){
            DelayObj obj = (DelayObj) delayQueue.take() ;
            if ( obj!=null){
                System.out.println(obj.t);
            }
        }
    }
    static class DelayObj implements Delayed {
        long t ;

        public DelayObj(long t) {
           this.t = t;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            System.out.println(System.currentTimeMillis()-t);
            return t-System.currentTimeMillis();
        }

        @Override
        public int compareTo(Delayed o) {
            if(o instanceof DelayObj)
                //减少一次循环
                return this.t-((DelayObj) o).t<0?-1:1;
            return 0;
        }
    }
}

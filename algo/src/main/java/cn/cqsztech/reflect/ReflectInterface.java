package cn.cqsztech.reflect;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static java.lang.reflect.Proxy.newProxyInstance;

/**
 * ccmars
 * 2021/6/7
 **/
public class ReflectInterface {
    interface  movable{
      String   move(String s);
    }
    static class A implements movable{

        @Override
        public String move(String s) {
            System.out.println("A");
            return "A Class";
        }
    }

//    class B implements movable{
//
//        @Override
//        public String move(String s) {
//            System.out.println("B");
//            return "B Class";
//        }
//    }

    /**
     * 动态代理的基本实现思路
     * 实现一个invocationhandler 聚合一个被代理对象
     */
    static class MyInvocationHandler implements InvocationHandler{
        movable m;

    public MyInvocationHandler(movable m) {
        this.m = m;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        Object result =  method.invoke(m,args);
        System.out.println("after");
        return result;

    }
}

    public static void main(String[] args) {
        //基于jdk的动态代理
        movable m = new A();
        Object o = newProxyInstance(ReflectInterface.class.getClassLoader(), new Class[]{movable.class}, new MyInvocationHandler(m));
        movable proxy  = (movable) o;
        String hh = proxy.move("hh");
        System.out.println(hh);
        System.out.println(o.toString());
    }

}

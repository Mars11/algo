package cn.cqsztech.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * ccmars
 * 2021/6/7
 **/
public class ReflectInterface {
    interface  movable{
      void   move(String s);
    }
    class A implements movable{

        @Override
        public void move(String s) {
            System.out.println("A");
        }
    }

    class B implements movable{

        @Override
        public void move(String s) {
            System.out.println("B");
        }
    }

    public static void main(String[] args) {
        Object o = Proxy.newProxyInstance(ReflectInterface.class.getClassLoader(), new Class[]{movable.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                method.invoke(proxy,args);
                return null;
            }
        });
        movable m  = (movable) o;
        m.move("hh");
    }
}

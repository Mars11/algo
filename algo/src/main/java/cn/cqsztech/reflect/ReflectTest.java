package cn.cqsztech.reflect;

import java.lang.reflect.Field;

/**
 * ccmars
 * 2021/12/24
 **/
public class ReflectTest {
    private String name ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public static void modifyField(Field field,Object o,Object newValue){
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        try {
            field.set(o,newValue);
        } catch (IllegalAccessException e) {
            //ignore
            e.printStackTrace();
        }
        field.setAccessible(accessible);
    }

    public static void main(String[] args) {
        ReflectTest reflectTest = new ReflectTest();
        reflectTest.setName("zhangsan");
        System.out.println(reflectTest.getName());
        try {
            Field name = reflectTest.getClass().getDeclaredField("name");
            modifyField(name,reflectTest,"lisi");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        System.out.println(reflectTest.getName());
    }
}

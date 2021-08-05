package cn.cqsztech.objectinit;

/**
 * ccmars
 * 2021/6/7
 **/
public class Child extends Father {
    public static int i = 0;     // 3
    public int j = 0;     // 8

    public Child() {     // 10
        System.out.println("Test Constructor");
    }

    {     // 9
        System.out.println("Test not static block");
    }

    static {     // 4
        System.out.println("Test static block");
    }

    public static void main(String[] args)   {
        new Child();
    }
}

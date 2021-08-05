package cn.cqsztech.objectinit;

/**
 * ccmars
 * 2021/6/7
 **/
public class Father {
    public static  int k = 10;
    static {     // 1
        System.out.println("Father static block");
    }

    {     // 5
        System.out.println("Father not static block");
    }

    public Father(){     // 7
        System.out.println("Father Constructor");
    }

    public static int i=7;     // 2
    public int j=8;     // 6

    public static void main(String[] args) {
        new Father();
        new Father();
    }
}

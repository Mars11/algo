package cn.cqsztech.vr;

/**
 * 测试java是否是引用传递
 */
public class TestValueOrRefrenceSet {
    public static void main(String[] args) {
        String a="test";
        String b = a;
        String c ="c";
        a = c;
        System.out.println(a);
        System.out.println(b);
    }
}

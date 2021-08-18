package cn.cqsztech;

/**
 * 位操作
 */
public class BitOperation {
    public static void main(String[] args) {
        //i++ 和 ++i的区别
        int i = 10;

        // 3 iload_1
        // 4 iinc 1 by 1
        // 7 istore_1
        i=i++;
        i++;
        i=++i;
        // 3 iinc 1 by 1
        // 6 iload_1
        // 7 istore_1
        System.out.println(i);
        // % | 操作
        //11111111111111111111111111111111
        //101
        //110
        //00000000000000000000000000000001
        //输出的是补码 对应的正数取反+1就是对应负数的补码，也就是正数的相反数
        System.out.println(Integer.toBinaryString(-1));
        System.out.println(1|2&1);
        System.out.println(3&1);
        System.out.println(bitOperate(1,2));
        printMagicVersion();
    }

    /**
     * 左移动参数0八位
     * @param arg0
     * @param arg1
     * @return
     */
    public static int bitOperate(int arg0,int arg1){
        return arg0<<8|arg1;
    }

    public static String printMagicVersion(){
        System.out.println(String.valueOf(0xCAFEBABE));
        return String.valueOf((char) 0xCAFEBABE);
    }
}

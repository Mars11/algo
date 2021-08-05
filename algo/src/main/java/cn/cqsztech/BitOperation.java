package cn.cqsztech;

/**
 * 位操作
 */
public class BitOperation {
    public static void main(String[] args) {
        // % | 操作
        //11111111111111111111111111111111
        //101
        //110
        //00000000000000000000000000000001
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

package cn.cqsztech.jvm;

import cn.cqsztech.objectinit.Child;
import cn.cqsztech.objectinit.Father;
import org.openjdk.jol.info.ClassLayout;

/**
 * ccmars
 * 2021/6/8
 * java对象的对象头信息
 **/
public class ObjectHead {
    private  int head=1;
    private  int body=3;
    static SimpleObj f = new SimpleObj();
    static {
        f.i =3;
        f.j =1;
    }
    public static void main(String[] args) {
        f.hashCode();
        /**
         * 打印对象头信息
         */
        String s = ClassLayout.parseInstance(f).toPrintable();
        //-XX:-UseCompressedClassPointers
        //64 or 32 bit mark word + 32or64 class pointer 不指针压缩的情况 压缩后就是32了
        //cn.cqsztech.jvm.SimpleObj object internals:
        // OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
        //      0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
        //      4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
        //      8     4        (object header)        klass pointer      92 c3 00 f8 (10010010 11000011 00000000 11111000) (-134167662)
        //     12     4    int SimpleObj.i                               0
        //     16     4    int SimpleObj.j                               0
        //     20     4        (loss due to the next object alignment)
        //Instance size: 24 bytes
        //Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
        System.out.println(SimpleObj.class.hashCode());
        System.out.println(f.hashCode());
        System.out.println(Integer.toBinaryString(f.hashCode()));
        System.out.println(Integer.toHexString(f.hashCode()));
        System.out.println(ClassLayout.parseInstance(f).toPrintable());
        //对应class地址
        System.out.println(f.toString());
//        System.out.println(ClassLayout.parseInstance(f).instanceSize());
        System.out.println(s);
        synchronized (f){
            System.out.println(ClassLayout.parseInstance(f).toPrintable());

        }
    }
}

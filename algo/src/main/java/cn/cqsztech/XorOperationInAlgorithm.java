package cn.cqsztech;

/**
 * ccmars
 * description
 * 与异或运算相关的题目
 * <>奇数次的数字</>
 * <>两数交换</>
 * <>多个奇数次数字</>
 * 2021/8/8
 **/
public class XorOperationInAlgorithm {
    /**
     * 取一个数组中仅仅出现一次的或者奇数次的数字
     * @param arg0
     * @return
     */
    public static int  find(int[] arg0){
        if(arg0==null || arg0.length==0){
            throw new RuntimeException("invalid param arg0 in MyFindOnlyOne'method find()");
        }
        int result = 0;
        for (int i = 0; i < arg0.length; i++) {
            /**
             * 利用异或的结合法和交换法
             * a^a=0
             * 0^a=a
             * a^b=b^a
             * a^b^c=(a^c)^b
             * 用此原理同时可得出一个数组中有且出现次数为奇数次的两个不同的数字
             *
             */
            result = result^arg0[i];
        }
        return result;
    }
    public static int[] findSpecialTwo(int[] arg0){
        if(arg0==null || arg0.length==0){
            throw new RuntimeException("invalid param arg0 in MyFindOnlyOne'method find()");
        }
        int selectOne = 0;

        int[] result = new int[]{0,0};
        // 取出 a^b
        for (int i = 0; i < arg0.length; i++) {
            selectOne = selectOne^arg0[i];
        }
        //找出其中最右为为1的数字位在第几位，以及区分原数据中的数据
        int selectTwo = selectOne&(~selectOne-1);
        int selectThree = 0;
        for (int i = 0; i < arg0.length; i++) {
            if((arg0[i]&selectTwo) == 0){
                selectThree = selectThree^arg0[i];
            }
        }
        selectTwo = selectOne^selectThree;
        result[0]=selectTwo;
        result[1]=selectThree;
        return result;
    }

    /**
     *此方法有一个弊端及a和b不能指向同一个对象?
     * @param a
     * @param b
     */
    public static void swap(int a ,int b){
        a = a^b;
        b = a^b;
        a = a^b;
    }
    public static void main(String[] args) {
        int[] ar = {1,3,0,3,1,14,12,14,12};
        System.out.println(find(ar));
        ar = new int[]{1, 3, 0, 3, 1, 14, 12, 14, 12, 222};
        System.out.println(findSpecialTwo(ar)[0]+","+findSpecialTwo(ar)[1]);
        int a = 3 ;
        swap(a,a);
    }
}

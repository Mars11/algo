package cn.cqsztech.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ccmars
 * 2021/6/3
 * list删除元素中比较容易踩的坑
 **/
public class ListOperationExceptionDeamoon {
    public static void main(String[] args) {
        //正确操作方式
        modifyListIterator();
        //会出问题的代码
        modifyListByCycle();
    }
    static void modifyListByCycle(){
        List<String> list = new ArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
//        list.forEach((o)-> {
//            list.remove(o);
//        });
        for (String o : list) {
            list.remove(o);
            System.out.println(o);
        }

    }

    static void modifyListIterator(){
        List<String> list = new ArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
        Iterator<String> iterator  = list.iterator();
        while (iterator.hasNext()){
            iterator.remove();
        }


    }
}

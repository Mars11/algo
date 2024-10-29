package cn.cqsztech.twosum;

import cn.hutool.core.collection.ListUtil;

import javax.swing.*;
import java.util.*;

/**
 * 两数之和
 */
public class TwoSum {
    public static void main(String[] args) {
        List<Integer> list = new LinkedList<>();
        list.add(3);
        list.add(8);
        list.add(1);
        list.add(7);
        ListUtil.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if(o1 > o2)return 1;
                else if(o1.equals(o2))return 0;
                else return 0;

            }
        });
        System.out.println(list);
    }
}

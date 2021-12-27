package cn.cqsztech.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * ccmars
 * 2021/12/27
 **/
public class StreamAPI {
    public static void generateStream(){
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        //生成list的串行流
        List<String> filtered = strings.stream()
                //过滤掉为空的
                .filter(string -> !string.isEmpty())
                .collect(Collectors.toList());
        System.out.println(filtered.size());
    }
    public static void streamForeach(){
        Random random = new Random();
        //limit 限制流的数量
        random.ints().limit(10).forEach(System.out::println);
    }
    public static void streamMap(){
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        //map 类似于将元素映射成新的元素，差不多就是个替换操作了
        List<Integer> squaresList = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());
        squaresList.stream().forEach(System.out::println);
    }
    public static void streamFilter(){
        List<String>strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        // 获取空字符串的数量
        long count = strings.stream().filter(string -> string.isEmpty()).count();
        System.out.println(count);
    }
    public static void main(String[] args) {
        generateStream();
        streamForeach();
        streamMap();
        streamFilter();
    }
}

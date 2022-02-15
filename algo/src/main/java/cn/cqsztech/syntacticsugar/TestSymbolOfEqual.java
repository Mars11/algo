package cn.cqsztech.syntacticsugar;

/**
 * ccmars
 * 2022/1/17
 * 反编译"="符号的jvm指令
 **/
public class TestSymbolOfEqual {
    public static void main(String[] args) {
        String a = "a";
        String b = "b";
        if(a == b){
            System.out.println("true");
        }
    }
}

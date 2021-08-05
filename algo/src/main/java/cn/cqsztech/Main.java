package cn.cqsztech;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ccmars
 * 2021/7/23
 **/
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String p = in.nextLine();
        System.out.println(sum(p).toString());
    }
    public static String sum(String args){
        //假定args数据满足要求
         StringBuffer result = new StringBuffer();
        //输入0的情况

        if(args!=null && args.length()==1&& "0".equals(args)){
            return "1,";
        }
        //其他情况
        StringBuffer unit = new StringBuffer();
        unit.append("");

        for (int i = 0; i < args.length(); i=i+2) {
            unit.append(args.substring(i,i+1));
        }
        long cal=1;

        cal+= Long.parseLong(unit.toString());
        String tmp = String.valueOf(""+cal);
        for (int i = 0; i < tmp.length(); i++) {
            result.append( Integer.parseInt(tmp.substring(i,i+1)));
            result.append(",");
        }
        return result.toString();
    }
}

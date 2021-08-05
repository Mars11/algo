package cn.cqsztech.serializable;

import java.io.*;

/**
 * ccmars
 * 2021/6/8
 **/
public class SeralizableTester implements Serializable {
    int age ;

    public static void main(String[] args) {
        SeralizableTester s = new SeralizableTester();
        s.age= 10;
        System.out.println(s.toString());
        File f = new File("temp.dta");
        try {
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(f));
            o.writeObject(s);

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ObjectInputStream obi = new ObjectInputStream(new FileInputStream(f));
            try {
                SeralizableTester seralizableTester =(SeralizableTester) obi.readObject();
                System.out.println(seralizableTester.toString());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

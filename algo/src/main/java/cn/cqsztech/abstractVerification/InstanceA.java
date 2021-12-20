package cn.cqsztech.abstractVerification;

import jdk.nashorn.internal.ir.ReturnNode;

/**
 * ccmars
 * 2021/12/14
 **/
public class InstanceA extends AbstractA implements A {
    @Override
    public String say() {
        return "instance";
    }

    public static void main(String[] args) {
        InstanceA a  = new InstanceA();
        System.out.println(a.say());
    }
}

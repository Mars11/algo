package cn.cqsztech.nio;

public class SelectionKeyValue {
    public static void main(String[] args) {
        int opConnect = java.nio.channels.SelectionKey.OP_CONNECT;
        System.out.println(opConnect);
        int opAccept = java.nio.channels.SelectionKey.OP_ACCEPT;
        System.out.println(opAccept);
        int opRead = java.nio.channels.SelectionKey.OP_READ;
        System.out.println(opRead);
        int opWrite = java.nio.channels.SelectionKey.OP_WRITE;
        System.out.println(opWrite);
    }
}

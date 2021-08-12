package cn.cqsztech.list;

/**
 * ccmars
 * 2021/8/11
 **/
public class LinkedList {
    static class Node {
        int item;
        Node next;

        public Node(int item) {
            this.item = item;
        }
    }

    /**
     * 链表的翻转
     *
     * @param node
     * @return
     */
    public static Node reverseLinkedList(Node node) {
        Node pre = null;
        Node next = null;
        while (node != null) {
            //保存环境 将next节点放到next变量
          next = node.next;
          //next节点的值替换成之前的节点 也就是指针方向改变
          node.next = pre;
          //更新pre节点 保证下次节点进来的pre节点依然是有效的前置节点
          pre = node;
          //循环条件
          node = next;

        }
        return pre;
    }

    public static void main(String[] args) {
       Node no =  new Node(1);
       Node n2 = new Node(2);
       Node n3 = new Node(3);
       no.next=n2;
       n2.next = n3;
       Node n = reverseLinkedList(no);
        System.out.println(n.item);
        System.out.println(n.next.item);
        System.out.println(n.next.next.item);
    }
}

package com.java.algorithm;

public class LinkedAlgorithm {
    static class Node {
        Node next;
        Integer value;
    }

    /**
     * 判断链表是否有环，快慢指针法，如果两个指针相遇，
     * 则链表有环
     *
     * @param head
     * @return
     */
    public static boolean hasQueue(Node head) {
        Node slow = head;
        Node fast = slow;
        while (slow != null && fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
            head = head.next;
        }
        return false;
    }

    /**
     * 反转链表
     *
     * @param head
     * @return
     */
    public static Node reserveLinked(Node head) {
        Node root = new Node();
        root.next = null;

        Node next;
        while (head != null) {
            //记录要处理的下一个节点
            next = head.next;
            head.next = root.next;
            root.next = head;

            head = next;
        }
        return root.next;
    }

    /**
     * 合并两个有序数组
     *
     * @param node1
     * @param node2
     * @return
     */
    public static Node mergeLinked(Node node1, Node node2) {
        if (node1 == null)
            return node2;
        if (node2 == null)
            return node1;
        Node pre = new Node();
        pre.value = -1;
        Node end = pre;
        while (node1 != null && node2 != null) {
            if (node1.value > node2.value) {
                end.next = node2;
                node2 = node2.next;
            } else {
                end.next = node1;
                node1 = node1.next;
            }
            end = end.next;
        }
        end.next = node1 != null ? node1 : node2;
        return pre.next;
    }

    /**
     * 输出链表的元素值
     *
     * @param head 链表的头结点
     */
    public static void printList(Node head) {
        while (head != null) {
            System.out.print(head.value + "->");
            head = head.next;
        }
        System.out.println("end");
    }

    public static void main(String[] args) {
        Node head = new Node();
        head.value = 1;
        Node temp = head;

        for (int i = 1; i < 9; i++) {
            Node next = new Node();
            next.value = i + 1;
            temp.next = next;
            temp = next;
        }

//        head = reserveLinked(head);
//        printList(head);

        Node list1 = new Node();
        list1.value = 1;
        Node tp1 = list1;

        for (int i = 1; i < 9; i += 2) {
            Node next = new Node();
            next.value = i + 2;
            tp1.next = next;
            tp1 = next;
        }

        Node list2 = new Node();
        list2.value = 2;
        Node tp2 = list2;

        for (int i = 2; i < 10; i += 2) {
            Node next = new Node();
            next.value = i + 2;
            tp2.next = next;
            tp2 = next;
        }
        head = mergeLinked(list1, list2);
        printList(head);
    }
}

package com.java.datastructure;

/**
 * 链表
 * @param <E>
 */
public class Linked<E> {
    private int size = 0;
    DoubleNode<E> head;
    DoubleNode<E> tail;

    //单向链表
    private class SingleNode<T> {
        SingleNode<T> next;
        T data;

        public SingleNode(SingleNode<T> next, T data) {
            this.next = next;
            this.data = data;
        }
    }

    //双向链表
    private class DoubleNode<T> {
        DoubleNode<T> prev;
        DoubleNode<T> next;
        T data;

        public DoubleNode(DoubleNode<T> prev, DoubleNode<T> next, T data) {
            this.prev = prev;
            this.next = next;
            this.data = data;
        }
    }

    void checkPosition(int index) {
        if (index <= 0 || index > size)
            throw new IndexOutOfBoundsException();
    }

    public void insert(E element, int position) {
        checkPosition(position);
        if (position == size) {//在链表尾部插入
            final DoubleNode<E> last = tail;//将last设置为尾结点
            final DoubleNode<E> node = new DoubleNode<>(last, null, element);//构造一个新节点
            tail = node;
            if (last == null) {
                head = node;
            } else {
                last.next = node;
            }
            size++;
        } else {
            DoubleNode<E> currNode = node(position);
            final DoubleNode<E> pred = currNode.prev;//将prev设为插入节点的上一个节点
            final DoubleNode<E> node = new DoubleNode<>(pred, currNode, element);
            currNode.prev = node;
            if (pred == null)
                head = node;
            else
                pred.next = node;
            size++;
        }
    }

    public E remove(int index) {
        checkPosition(index);
        return unlink(node(index));
    }

    public E get(int index){
        checkPosition(index);
        return node(index).data;
    }

    E unlink(DoubleNode<E> node) {
        final E element = node.data;
        final DoubleNode<E> next = node.next;
        final DoubleNode<E> prev = node.prev;
        if (prev == null) {//若前驱节点为空，则表示移除的是头结点
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        node.data = null;
        size--;
        return element;
    }

    /**
     * 根据索引返回链表相应的节点
     *
     * @param index
     * @return
     */
    DoubleNode<E> node(int index) {
        if (index < (size >> 1)) {
            DoubleNode<E> x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            DoubleNode<E> x = tail;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }
}

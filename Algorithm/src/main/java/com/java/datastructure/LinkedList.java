package com.java.datastructure;

/**
 * 有 bug
 * @param <E>
 */
public class LinkedList<E> {
    private int size = 0;
    private Node<E> first;
    private Node<E> last;

    public void add(int index, E element) {
        checkIndex(index);
        if(index==size){
            final Node<E> l = last;
            final Node<E> newNode = new Node<>(element,l, null);
            last = newNode;
            if (l == null)
                first = newNode;
            else
                l.next = newNode;
        }else {
            Node<E> succ=getNode(index);
            final Node<E> pred = succ.prev;
            final Node<E> newNode = new Node<>(element,pred, succ);
            succ.prev = newNode;
            if (pred == null)
                first = newNode;
            else
                pred.next = newNode;
        }
        size++;
    }

    public Node<E> getNode(int index) {
        checkIndex(index);
        if (index < (size >> 1)) {
            Node<E> t = first;
            for (int i = 0; i < index; i++) {
                t = t.next;
            }
            return t;
        } else {
            Node<E> t = last;
            for (int l = size - 1; l > index; l--) {
                t.prev = t;
            }
            return t;
        }
    }

    public boolean remove(int index) {
        checkIndex(index);
        Node<E> temp = getNode(index);
        temp.prev.next = temp.next;
        temp.next.prev = temp.prev;
        size--;
        return true;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object obj) {
        Node<E> temp = first;
        for (int i = 0; i < size; i++) {
            if (temp.data.equals(obj))
                return true;
            temp = temp.next;
        }
        return false;
    }

    void checkIndex(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();
    }

    class Node<T> {
        Node<T> prev;
        Node<T> next;
        T data;

        public Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(0, 1);
        list.add(1, 2);
        list.add(2, 3);
        list.remove(1);
        System.out.println(list.contains(2));
    }
}

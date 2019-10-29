package com.java.datastructure;


/**
 * 先进后出
 *
 * @param <E>
 */
public class Stack<E> {

    protected Object[] elementData;

    protected int top;  //栈顶游标

    public Stack(int size) {
        elementData = new Object[size];
        top = -1;
    }

    public boolean push(E item) {
        if (isFull())
            return true;
        else {
            elementData[++top] = item;
            return true;
        }
    }

    public E pop() {
        if (isFull())
            return null;
        else {
            E topValue=elementData(top);
            --top;
            return topValue;
        }
    }

    public E getTop() {
        return elementData(top);
    }

    public int size() {
        return elementData.length;
    }

    public boolean isEmpty() {
        return size() > 0;
    }

    public boolean isFull() {
        return top == size();
    }

    public void clear() {
        for (int index = 0; index < size(); index++) {
            elementData[index] = null;
        }
        top=-1;
    }

    E elementData(int index) {
        return (E) elementData[index];
    }

    public static void main(String[] args){
        Stack<Character> stack=new Stack<>(8);
        //2状态
        System.out.println("栈是否为空："+stack.isEmpty());
        System.out.println("栈是否已满："+stack.isFull());

        //2操作
        //依次压栈
        stack.push('a');
        stack.push('b');
        stack.push('c');
        stack.clear();
        //依次弹栈
        System.out.println("弹栈顺序：");
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }

}

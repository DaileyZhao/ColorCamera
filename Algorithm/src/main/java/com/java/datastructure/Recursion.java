package com.java.datastructure;

import java.util.Arrays;

/**
 * 递归写法
 */
public class Recursion {
    /**
     * 求阶乘
     *
     * @param n
     * @return
     */
    public static int getFactoria(int n) {
        if (n > 0) {
            if (n == 1) {
                System.out.println(n + "!=1");
                return 1;
            } else {
                System.out.println(n);
                int temp = n * getFactoria(n - 1);
                System.out.println(n + "!=" + temp);
                return temp;
            }
        }
        return -1;
    }

    /**
     * 二分查找
     * 循环写法
     *
     * @param array
     * @param key
     * @return
     */
    public static int binarySearch(int[] array, int key) {
        int start = 0;
        int end = array.length - 1;
        while (start < end) {
            int mid = (start + end) >> 1;
            if (key == array[mid]) {
                return mid;
            }
            if (key > array[mid]) {
                start = mid + 1;
            }
            if (key < array[mid]) {
                end = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 二分查找 递归实现
     *
     * @param array
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static int binarySearch(int[] array, int key, int start, int end) {
        int mid = (start + end) >> 1;
        if (key == array[mid]) {
            return mid;
        } else if (start > end) {
            return -1;
        } else {
            if (key < array[mid]) {
                return binarySearch(array, key, start, mid - 1);
            }
            if (key > array[mid]) {
                return binarySearch(array, key, mid + 1, end);
            }
        }
        return -1;
    }

    /**
     * @param n
     * @param from
     * @param to
     * @param temp
     */
    public static void hanoi(int n, String from, String temp, String to) {
        if (n == 1) {
            System.out.println("将盘子" + n + "从塔座" + from + "移动到目标塔座" + to);
        } else {
            hanoi(n - 1, from, to, temp);//A为初始塔座，B为目标塔座，C为中介塔座
            System.out.println("将盘子" + n + "从塔座" + from + "移动到目标塔座" + to);
            hanoi(n - 1, temp, from, to);//B为初始塔座，C为目标塔座，A为中介塔座
        }
    }

    /**
     * 合并两个有序数组
     *
     * @param a
     * @param b
     * @return
     */
    public static int[] mergeSort(int[] a, int[] b) {
        int[] c = new int[a.length + b.length];
        int aNum = 0, bNum = 0, cNum = 0;
        while (aNum < a.length && bNum < b.length) {
            if (a[aNum] >= b[bNum]) {
                c[cNum++] = b[bNum++];
            } else {
                c[cNum++] = a[aNum++];
            }
        }
        while (aNum == a.length && bNum < b.length) {
            c[cNum++] = b[bNum++];
        }
        while (bNum == b.length && aNum < a.length) {
            c[cNum++] = a[aNum++];
        }
        return c;
    }

    public static void main(String[] args) {
        int[] array = new int[]{7, 9, 1, 0, 8, 5};
        int[] a = new int[]{1, 2, 4, 5, 7, 8};
        int[] b = new int[]{3, 4, 6, 9, 10};
        System.out.println(Arrays.toString(mergeSort(a, b)));
        System.out.println(binarySearch(array, 1, 0, array.length - 1));
        System.out.println(binarySearch(array, 1));
        getFactoria(5);
        hanoi(3, "A", "B", "C");
    }
}

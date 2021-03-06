package com.java.algorithm;

import java.util.Arrays;

public class SortAlgorithm {
    //冒泡排序
    public static void bubbleSort(int array[]) {
        int tmp = 0;    //记录最后一次交换的位置
        int lastExchangeIndex = 0;    //无序数列的边界，每次比较只需要比到这里为止
        int sortBorder = array.length - 1;
        for (int i = 0; i < array.length; i++) {        //有序标记，每一轮的初始是true
            boolean isSorted = true;
            for (int j = 0; j < sortBorder; j++) {
                if (array[j] > array[j + 1]) {
                    tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;                //有元素交换，所以不是有序，标记变为false
                    isSorted = false;                //把无序数列的边界更新为最后一次交换元素的位置
                    lastExchangeIndex = j;
                }
            }
            sortBorder = lastExchangeIndex;
            if (isSorted) {
                break;
            }
            System.out.println(Arrays.toString(array));
        }
    }

    //快速排序
    public static void quickSort(int array[], int leftIndex, int rightIndex) {
        if (leftIndex >= rightIndex) {
            return;
        }
        int left = leftIndex;
        int right = rightIndex;
        int key = array[left];
        while (left < right) {
            while (right > left && array[right] >= key) {
                right--;
            }
            array[left] = array[right];
            System.out.println(Arrays.toString(array)+"  left= "+left+"  right="+right);
            while (left < right && array[left] <= key) {
                left++;
            }
            array[right] = array[left];
            System.out.println(Arrays.toString(array)+"  left= "+left+"  right="+right);
        }
        array[left] = key;
        System.out.println(Arrays.toString(array)+"  left= "+left+"  right="+right);
        quickSort(array, leftIndex, left - 1);
        quickSort(array, right + 1, rightIndex);
    }

    public static void main(String[] args) {
        int[] array = new int[]{5, 1, 7, 3, 1, 6, 9, 4};
//        bubbleSort(array);
        quickSort(array, 0, array.length - 1);
    }
}


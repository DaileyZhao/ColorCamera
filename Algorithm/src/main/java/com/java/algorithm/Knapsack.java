package com.java.algorithm;

/**
 * 背包问题
 */
public class Knapsack {
    int[] weights;
    boolean[] selects;

    public Knapsack(int[] weights) {
        this.weights = weights;
        this.selects = new boolean[weights.length];
    }

    public void knapsack(int total, int index) {
        if (total < 0 || total > 0 && index >= weights.length) {
            return;
        }
        if (total == 0) {
            for (int i = 0; i < index; i++) {
                if (selects[i])
                    System.out.println(weights[i] + " ");
            }
            System.out.println();
            return;
        }
        selects[index] = true;
        knapsack(total - weights[index], index + 1);
        selects[index] = false;
        knapsack(total, index + 1);
    }

    public static void main(String[] args) {
        int array[] = {11,9,7,6,5};
        int total = 20;
        Knapsack k = new Knapsack(array);
        k.knapsack(total, 0);
    }
}

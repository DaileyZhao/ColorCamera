package com.java.datastructure;

/**
 * 递归写法
 */
public class Recursion {
    /**
     * 求阶乘
     * @param n
     * @return
     */
    public int getFactoria(int n) {
        if (n > 0) {
            if (n == 1)
                return 1;
            else
                return n * getFactoria(n - 1);
        }
        return -1;
    }
}

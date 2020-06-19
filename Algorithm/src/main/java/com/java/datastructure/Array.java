package com.java.datastructure;

/**
 * 数组
 */
public class Array {
    //一位数组
    Object[] linearArray = new Object[0];
    //二维数组
    Object[][] matrix = new Object[0][0];
    //三维数组
    //每一维都是一个二维数组，可以以索引的形式来理解
    Object[][][] threeArray = new Object[0][0][0];

    /**
     * 在一个二维数组中，每一行都按照从左到右递增的顺序排序，
     * 每一列都按照从上到下递增的顺序排序。请完成一个函数，
     * 输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
     */
    public static boolean containsNumber(int[][] matrix, int number) {
        if (matrix == null || matrix.length < 1 || matrix[0].length < 1) {
            return false;
        }
        int rows = matrix.length;// 行数
        int columns = matrix[0].length; //列数
        int row = 0;
        int column = columns - 1;
        while (row >= 0 && row < rows && column >= 0 && column < columns) {
            if (matrix[row][column] == number) {
                return true;
            } else if (matrix[row][column] < number) {
                row++;
            } else if (matrix[row][column] > number) {
                column--;
            }
        }
        return false;
    }

    public static void main(String[] args) {

    }
}

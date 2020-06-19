package com.java.algorithm;

public class StringAlgorithm {
    /**
     * 反转字符串
     * @param s
     */
    public static void reverseString(char[] s) {
        int length = s.length;
        char temp ;
        for(int i=0;i<length/2;i++){
            temp = s[i];
            s[i] = s[length -1 -i];
            s[length -1 - i] = temp;
        }
        System.out.println(s);
    }

    public static void main(String[] args) {
        reverseString(new char[]{'h','e','l','l','o',',','w','o','r','l','d'});
    }
}

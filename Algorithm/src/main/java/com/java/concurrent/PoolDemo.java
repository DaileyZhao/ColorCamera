package com.java.concurrent;

public class PoolDemo {
    public static void main(String[] args){
        retry:
        for (int i=0;i<5;i++){
            for (int j=0;j<5;j++){
                System.out.println(j);
                if (j==3) break retry;
            }
        }
    }
}

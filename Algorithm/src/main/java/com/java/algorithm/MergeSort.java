package com.java.algorithm;

/**
 * 参考 {@link System#arraycopy(Object, int, Object, int, int)}
 */
public class MergeSort {
    private static Object s=new Object();
    private static int count =0;
    public static void main(String[] args) {
//        String regex="^((0?[1-9])|((1|2)[0-9])|30|31)$";
//        String params="2019-11-30";
//        System.out.println(Pattern.matches(regex,params));
        while(true){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (s){
                        count+=1;
                        System.out.println("New Thread#"+count);
                    }
                    for (;;){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }
}

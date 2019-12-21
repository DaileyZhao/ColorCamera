package com.java.algorithm;

public class ReplaceSort {
    public static int replaceBlank(char[] string,int usedLength){
        if (string==null||string.length<usedLength){
            return -1;
        }
        int whiteCount=0;
        for (int i=0;i<usedLength;i++){
            if (string[i]==' '){
                whiteCount++;
            }
        }
        int targetLength=whiteCount*2+usedLength;
        int tmp=targetLength;
        if (targetLength>string.length){
            return -1;
        }
        if (whiteCount==0){
            return usedLength;
        }
        usedLength--;
        targetLength--;
        while (usedLength>=0&&usedLength<targetLength){
            if (string[usedLength]==' '){
                string[targetLength--]='0';
                string[targetLength--]='2';
                string[targetLength--]='%';
            } else {
                string[targetLength--]=string[usedLength];
            }
            usedLength--;
        }
        return tmp;
    }

    public static void main(String[] args) {
        char[] string = new char[50];
        string[0] = ' ';
        string[1] = 'e';
        string[2] = ' ';
        string[3] = ' ';
        string[4] = 'r';
        string[5] = 'e';
        string[6] = ' ';
        string[7] = ' ';
        string[8] = 'a';
        string[9] = ' ';
        string[10] = 'p';
        string[11] = ' ';

        int length = replaceBlank(string, 12);
        System.out.println(new String(string, 0, length));
    }
}

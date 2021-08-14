package com.changgou.entity;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        int[] sort = {54,58,456,19,34};
        mp(sort);
        System.out.println(Arrays.toString(sort));
        int[] chaR = {84,35,16,74,31,64};
        cr(chaR);
        System.out.println(Arrays.toString(chaR));

    }

    /**
     * 冒泡排序
     * @param sort
     */
    public static void mp(int[] sort){
        int temp;
        for (int i = 0; i < sort.length; i++) {
            for (int j = 0; j < sort.length-1; j++) {
                if (sort[j]<sort[j+1]){
                    temp = sort[j];
                    sort[j] = sort[j+1];
                    sort[j+1]=temp;
                }
            }
        }
    }

    /**
     * 插入排序
     * @param chaR
     */
    public static void cr(int[] chaR){
        for (int i = 1; i < chaR.length; i++) {
            int temp = chaR[i];
            int j = i-1;
            while (j>=0){
                if (chaR[j]>temp){//表示插入位置的数据小于起前面的那个数据
                   chaR[j+1]=chaR[j];//把数据小的往后移
                }else {
                    break;
                }
                j--;
            }
            chaR[j+1]=temp;
        }
    }


}

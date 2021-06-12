package com.mfk.ws.bitmap;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

/**
 * BitMap 的基本原理就是用一个bit 位来存放某种状态，适用于大规模数据，但数据状态又不是很多的情况。
 * 通常是用来判断某个数据存不存在的。
 * 在Java里面，BitMap已经有对应实现的数据结构类java.util.BitSet，
 * BitSet的底层使用的是long类型的数组来存储元素。
 */
public class TestBitMap {
    //假设数据是以数组的形式给我们的
    public static Set test(int[] arr) {
        int j = 0;
        //避免返回重复的数，存在Set里
        Set output = new HashSet();
        BitSet bitSet = new BitSet(Integer.MAX_VALUE);
        int i = 0;
        while (i < arr.length) {
            int value = arr[i];
            //判断该数是否存在bitSet里
            if (bitSet.get(value)) {
                output.add(value);
            } else {
                bitSet.set(value, true);
            }
            i++;
        }
        return output;
    }
    //测试
    public static void main(String[] args) {
        int[] t = {1,2,3,4,5,6,7,8,3,4,9};
        Set t2 = test(t);
        System.out.println(t2);
    }
}
package org.mynah.follower;

import java.util.ArrayList;

public class CombineUtil {

    public static void main(String[] args) {
        ArrayList<String> list = CombineUtil.combine(5, 3);
        System.out.println(list);
        // 求5中选3的组合
        // 1 1 1 0 0 //1,2,3
        // 1 1 0 1 0 //1,2,4
        // 1 0 1 1 0 //1,3,4
        // 0 1 1 1 0 //2,3,4
        // 1 1 0 0 1 //1,2,5
        // 1 0 1 0 1 //1,3,5
        // 0 1 1 0 1 //2,3,5
        // 1 0 0 1 1 //1,4,5
        // 0 1 0 1 1 //2,4,5
        // 0 0 1 1 1 //3,4,5
    }

    public static final String SPLIT = ":";

    // 首先初始化，将数组前n个元素置1，表示第一个组合为前n个数。
    // 然后从左到右扫描数组元素值的“10”组合，找到第一个“10”组合后将其变为“01”组合，
    // 同时将其左边的所有“1”全部移动到数组的最左端（只有第一位变为0才需要移动，
    // 否则其左边的1本来就在最左端，无需移动）。
    // 当第一个“1”移动到数组的m-n的位置，即n个“1”全部移动到最右端时，就得到了最后一个组合。
    public static ArrayList<String> combine(int m, int n) {
        ArrayList<String> combines = new ArrayList<String>();
        if (m >= n && m > 0 && n > 0) {
            int[] array = new int[m];
            array[0] = 1;
            for (int i = 1; i < array.length; i++) {
                array[i] = array[i - 1] + 1;
            }
            // 初始化移位法需要的数组
            byte[] bits = new byte[m];
            for (int i = 0; i < bits.length; i++) {
                bits[i] = i < n ? (byte) 1 : (byte) 0;
            }
            boolean find = false;
            do {
                addCombination(array, bits, combines);
                // 找到10，换成01
                find = false;
                for (int i = 0; i < m - 1; i++) {
                    if (bits[i] == 1 && bits[i + 1] == 0) {
                        find = true;
                        bits[i] = 0;
                        bits[i + 1] = 1;
                        // 如果第一位为0，则将第i位置之前的1移到最左边，如为1则第i位置之前的1就在最左边，无需移动
                        if (bits[0] == 0) {
                            // O(n)复杂度使1在前0在后
                            for (int k = 0, j = 0; k < i; k++) {
                                if (bits[k] == 1) {
                                    byte temp = bits[k];
                                    bits[k] = bits[j];
                                    bits[j] = temp;
                                    j++;
                                }
                            }
                        }
                        break;
                    }
                }
            } while (find);
        }
        return combines;
    }

    private static void addCombination(int[] array, byte[] bits, ArrayList<String> combines) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bits.length; i++) {
            if (bits[i] == (byte) 1) {
                sb.append(array[i]).append(SPLIT);
            }
        }
        String temp = sb.toString();
        combines.add(temp.substring(0, temp.length() - SPLIT.length()));
    }

}
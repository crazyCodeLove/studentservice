package com.sse.util;

import java.util.Arrays;

/**
 * <p></p>
 * author pczhao<br/>
 * date  2020-03-02 16:05
 */

public class SortUtil {

    public static int[] mergeSortAsc(int[] data) {
        if (data.length < 2) {
            return data;
        }
        int[] left = Arrays.copyOfRange(data,0,data.length/2);
        int[] right = Arrays.copyOfRange(data, data.length/2, data.length);
        return merge(mergeSortAsc(left), mergeSortAsc(right));
    }

    /**
     * 归并排序
     *
     * @param left  左排序对列
     * @param right 右排序队列
     * @return 合并后的排序队列
     */
    public static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        for (int i = 0, j = 0, index = 0; index < result.length; index++) {
            if (i >= left.length) {
                result[index] = right[j++];
            } else if (j >= right.length) {
                result[index] = left[i++];
            } else if (left[i] > right[j]) {
                result[index] = right[j++];
            } else {
                result[index] = left[i++];
            }
        }
        return result;
    }
}

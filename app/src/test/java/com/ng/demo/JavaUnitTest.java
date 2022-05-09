package com.ng.demo;

import org.junit.Test;

import java.util.Arrays;

public class JavaUnitTest {
    private void test(){

    }

    /**
     * 比较v1是否大于v2
     *
     * @param v1 要比较的元素
     * @param v2 要比较的元素
     * @return v1是否大于v2
     */
    private static boolean greater(Comparable v1, Comparable v2) {
        return v1.compareTo(v2) > 0;
    }

    /**
     * 交换索引位置的值
     *
     * @param arr 需要交换的arr
     * @param i   对应v1的索引值
     * @param j   对应v2的索引值
     */
    private static void exch(Comparable[] arr, int i, int j) {
        Comparable temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    /**
     * 冒泡排序
     * 原理：
     * 1.比较相邻的元素。如果前一个元素比后一个元素大，就交换这两个元素的位置。
     * 2.对每一对相邻元素做同样的工作，从开始第一对元素到结尾的最后一对元素。最终最后位置的元素就是最大值。
     */
    private static void m冒泡排序(Comparable[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (greater(arr[j], arr[j + 1])) {
                    exch(arr, j, j + 1);
                }
            }
        }
    }

    /**
     * 选择排序
     * 原理：
     * 1.每一次遍历的过程中，都假定第一个索引处的元素是最小值，和其他索引处的值依次进行比较，如果当前索引处的值大于其他某个索引处的值，则假定其他某个索引出的值为最小值，最后可以找到最小值所在的索引
     * 2.交换第一个索引处和最小值所在的索引处的值
     */
    private static void s选择排序(Comparable[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            //定义一个变量，记录最小元素处的索引值，默认选在排序的第一元素的索引
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                //比较最小索引minIndex处的值和j索引处的值
                if (greater(arr[minIndex], arr[j])) {
                    minIndex = j;
                }
            }
            //交换最小元素所在索引minIndex处的值和索引i处的值
            exch(arr, i, minIndex);
        }
    }

    /**
     * 插入排序
     * 原理：
     * 1.把所有的元素分为两组，已经排序的和未排序的;
     * 2.找到未排序的组中的第一个元素，向已经排序的组中进行插入;
     * 3.倒叙遍历已经排序的元素，依次和待插入的元素进行比较，直到找到一个元素小于等于待插入元素，那么就把待插入元素放到这个位置，其他的元素向后移动一位;
     */
    private static void i插入排序(Comparable[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                //比较索引（j-1）和索引j处的值，如果索引j-1处的值得大于索引j处的值，交换数据，else找到合适的位置了，退出循环
                if (greater(arr[j - 1], arr[j])) {
                    exch(arr, j - 1, j);
                } else {
                    break;
                }
            }
        }
    }

    /**
     * 快速排序
     * 原理：
     * 1.首先设定一个分界值，通过该分界值将数组分成左右两部分;
     * 2.将大于或等于分界值的数据放到到数组右边，小于分界值的数据放到数组的左边。此时左边部分中各元素都小于或等于分界值，而右边部分中各元素都大于或等于分界值;
     * 3.然后，左边和右边的数据可以独立排序。对于左侧的数组数据，又可以取一个分界值，将该部分数据分成左右两部分，同样在左边放置较小值，右边放置较大值。右侧的数组数据也可以做类似处理。
     * 4.重复上述过程，可以看出，这是一个递归定义。通过递归将左侧部分排好序后，再递归排好右侧部分的顺序。当左侧和右侧两个部分的数据排完序后，整个数组的排序也就完成了。
     */
    private static void q快速排序(Comparable[] arr) {

    }

    @Test
    public void test1() {
        Comparable[] arr = {1, 5, 3, 8, 4, 9, 5, 5, 6, 7, 2, 88, 54, 666, 47};

        //m冒泡排序(arr);
        //s选择排序(arr);
        i插入排序(arr);
        System.out.print(Arrays.toString(arr));
    }
}


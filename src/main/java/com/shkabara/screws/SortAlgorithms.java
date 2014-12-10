package com.shkabara.screws;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by anatoliy on 10.12.2014.
 */
public class SortAlgorithms {

    public static void bubbleSort(int[] mas) {
        for (int i = mas.length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (mas[j] > mas[j + 1]) {
                    int tmp = mas[j];
                    mas[j] = mas[j + 1];
                    mas[j + 1] = tmp;
                }
            }
        }
    }

    public static void insertSort(int[] mas) {
        for (int i = 1; i < mas.length; i++) {
            int value = mas[i];
            int j;
            for (j = i - 1; j >= 0 && mas[j] > value; j--) {
                mas[j + 1] = mas[j];
            }
            mas[j + 1] = value;
        }
    }

    public static void selectSort(int[] mas) {
        for (int i = 0; i < mas.length - 1; i++) {
            // устанавливаем начальное значение минимального индекса
            int minI = i;
            //находим индекс минимального элемента
            for (int j = i + 1; j < mas.length; j++) {
                if (mas[j] < mas[minI]) {
                    minI = j;
                }
            }
            //меняем значения местами
            int temp = mas[i];
            mas[i] = mas[minI];
            mas[minI] = temp;
        }
    }

    public static int[] mergeSort(int[] mas) {
        return mergeSort(mas, new int[mas.length], 0, mas.length - 1);
    }

    /**
     * Сортирует массив используя рекурсивную сортировку слиянием
     *
     * @param up    массив который нужно сортировать
     * @param down  массив с, как минимум, таким же размерои как у 'up', используется как буфер
     * @param left  левая граница массива, передайте 0 чтобы сортировать массив с начала
     * @param right правая граница массива, передайте длинну массива - 1 чтобы сортировать массив до последнего элемента
     * @returen отсортированный массив. Из за особенностей работы данной имплементации, отсортированная версия массива может оказаться либо в 'up' либо в 'down
     */
    private static int[] mergeSort(int[] up, int[] down, int left, int right) {
        if (left == right) {
            down[left] = up[left];
            return down;
        }

        int middle = (int) ((left + right) * 0.5f);

        // разделяй и сортируй
        int[] l_buff = mergeSort(up, down, left, middle);
        int[] r_buff = mergeSort(up, down, middle + 1, right);

        // слияние двух отсортированных половин
        int[] target = l_buff == up ? down : up;

        int width = right - left, l_cur = left, r_cur = middle + 1;
        for (int i = left; i <= right; i++) {
            if (l_cur <= middle && r_cur <= right) {
                if (l_buff[l_cur] < r_buff[r_cur]) {
                    target[i] = l_buff[l_cur];
                    l_cur++;
                } else {
                    target[i] = r_buff[r_cur];
                    r_cur++;
                }
            } else if (l_cur <= middle) {
                target[i] = l_buff[l_cur];
                l_cur++;
            } else {
                target[i] = r_buff[r_cur];
                r_cur++;
            }
        }

        return target;
    }

    public static void quickSort(int[] mas){
        quickSort(mas, 0, mas.length - 1);
    }

    private static void quickSort(int[] a, int first, int last)
    {
        int i = first, j = last, x = a[(first + last) / 2];
        do {
            while (a[i] < x) i++;
            while (a[j] > x) j--;

            if(i <= j) {
                if (i < j){
                    int tmp = a[i];
                    a[i] = a[j];
                    a[j] = tmp;
                }
                i++;
                j--;
            }
        } while (i <= j);
        if (i < last)
            quickSort(a, i, last);
        if (first < j)
            quickSort(a, first, j);
    }

    public static void main(String[] args) {
//        //TEST
//        int[] mas = createMas();
//        System.out.println("Bubble Sort");
//        long start = System.currentTimeMillis();
//        bubbleSort(mas);
//        //System.out.println(Arrays.toString(mas));
//        System.out.println(System.currentTimeMillis() - start);
//
//        mas = createMas();
//        System.out.println("Insert Sort");
//        start = System.currentTimeMillis();
//        insertSort(mas);
//        //System.out.println(Arrays.toString(mas));
//        System.out.println(System.currentTimeMillis() - start);
//
//        mas = createMas();
//        System.out.println("Select Sort");
//        start = System.currentTimeMillis();
//        selectSort(mas);
//        //System.out.println(Arrays.toString(mas));
//        System.out.println(System.currentTimeMillis() - start);

        int[] mas = createMas();
//        System.out.println("Merge Sort");
//        long start = System.currentTimeMillis();
//        mas = mergeSort(mas);
//        //System.out.println(Arrays.toString(mas));
//        System.out.println(System.currentTimeMillis() - start);

        mas = createMas();
        System.out.println("Quick Sort");
        long start = System.currentTimeMillis();
        quickSort(mas);
        //System.out.println(Arrays.toString(mas));
        System.out.println(System.currentTimeMillis() - start);
    }

    private static int[] createMas() {
        int[] mas = new int[10000000];
        Random r = new Random();
        for(int i = 0; i < mas.length; i++){
            mas[i] = r.nextInt(10000000);
        }
        return mas;
    }
}

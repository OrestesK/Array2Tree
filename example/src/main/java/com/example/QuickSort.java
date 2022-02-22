package com.example;

public class QuickSort {
    private AnimationController animation;

    public QuickSort(int[] ar, AnimationController animation) {
        int[] arr = ar.clone();
        this.animation = animation;
        // System.out.println(Arrays.toString(arr) + " Unsorted");
        quickSort(arr, 0, arr.length - 1); // the pivot is going to start at the last index
        // System.out.println(Arrays.toString(arr) + " Sorted");
        // App.setData(arr);
    }

    private void quickSort(int[] arr, int low, int high) {
        if (low < high) { // while the left bound is less than the right bound
            int segment = partition(arr, low, high); // partitions the array, first time thorugh it encompasses the
                                                     // whole array
            quickSort(arr, low, segment - 1); // sorts the left side of the partitioned array
            quickSort(arr, segment + 1, high); // sorts the right side of the partitioned array
        }
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // value of the right bound
        int i = low - 1; // values of the left bound
        for (int j = low; j <= high - 1; j++) { // loops through the array within the given bounds
            if (arr[j] < pivot) { // if the value is less than the set pivot:
                i++; // increase i by one
                swap(arr, i, j); // swap

            }
        }
        swap(arr, i + 1, high); // once the right bound has been reached, swap the pivot value with the i value
        return i + 1; // return the right bound + 1
    }

    private void swap(int[] arr, int i, int j) {// swaps two values in an array
        if (i != j) {
            animation.addAnimation(i, j, true);
        }
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
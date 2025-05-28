package com.visualizer.sorting;

import java.util.*;

import com.visualizer.sorting.SortStep.StepType;

class SortingAlgorithms {

    // Utility method to create a range of indices from start to end inclusive
    private int[] createRange(int start, int end) {
        int[] range = new int[end - start + 1];
        for (int i = start; i <= end; i++) {
            range[i - start] = i;
        }
        return range;
    }

    public List<SortStep> bubbleSort(int[] array) {
        List<SortStep> steps = new ArrayList<>();
        int n = array.length;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                steps.add(new SortStep(SortStep.StepType.COMPARE, new int[]{j, j + 1}, array.clone(),
                        String.format("🔍 Comparing %d and %d", array[j], array[j + 1])));

                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;

                    steps.add(new SortStep(SortStep.StepType.SWAP, new int[]{j, j + 1}, array.clone(),
                            String.format("🔄 Swapped %d and %d", array[j + 1], array[j])));
                }
            }

            steps.add(new SortStep(SortStep.StepType.SORTED, new int[]{n - i - 1}, array.clone(),
                    String.format("✅ Element at position %d is sorted", n - i - 1)));

            if (!swapped) break;
        }

        for (int i = 0; i < n; i++) {
            steps.add(new SortStep(SortStep.StepType.SORTED, new int[]{i}, array.clone(),
                    String.format("✅ Position %d confirmed sorted", i)));
        }

        steps.add(new SortStep(SortStep.StepType.COMPLETED, new int[0], array.clone(),
                "🎉 Bubble Sort completed!"));
        return steps;
    }

    public List<SortStep> selectionSort(int[] array) {
        List<SortStep> steps = new ArrayList<>();
        int n = array.length;

        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;

            for (int j = i + 1; j < n; j++) {
                steps.add(new SortStep(SortStep.StepType.COMPARE, new int[]{minIdx, j}, array.clone(),
                        String.format("🔍 Comparing %d and %d", array[minIdx], array[j])));
                if (array[j] < array[minIdx]) {
                    minIdx = j;
                }
            }

            if (minIdx != i) {
                int temp = array[i];
                array[i] = array[minIdx];
                array[minIdx] = temp;

                steps.add(new SortStep(SortStep.StepType.SWAP, new int[]{i, minIdx}, array.clone(),
                        String.format("🔄 Swapped %d and %d", array[i], array[minIdx])));
            }

            steps.add(new SortStep(SortStep.StepType.SORTED, new int[]{i}, array.clone(),
                    String.format("✅ Position %d sorted", i)));
        }

        steps.add(new SortStep(SortStep.StepType.SORTED, new int[]{n - 1}, array.clone(),
                String.format("✅ Position %d sorted", n - 1)));

        steps.add(new SortStep(SortStep.StepType.COMPLETED, new int[0], array.clone(),
                "🎉 Selection Sort completed!"));

        return steps;
    }

    public List<SortStep> insertionSort(int[] array) {
        List<SortStep> steps = new ArrayList<>();
        int n = array.length;

        // Mark first element as sorted initially
        steps.add(new SortStep(SortStep.StepType.SORTED, new int[]{0}, array.clone(),
                "✅ First element is initially sorted"));

        for (int i = 1; i < n; i++) {
            int key = array[i];
            steps.add(new SortStep(SortStep.StepType.CURRENT, new int[]{i}, array.clone(),
                    String.format("🎯 Selected %d to insert into sorted section", key)));

            int j = i - 1;

            // Find the correct position and shift elements
            while (j >= 0 && array[j] > key) {
                steps.add(new SortStep(SortStep.StepType.COMPARE, new int[]{j, j + 1}, array.clone(),
                        String.format("🔍 Comparing %d with %d", array[j], key)));

                // Shift element to the right
                array[j + 1] = array[j];
                steps.add(new SortStep(SortStep.StepType.SHIFT, new int[]{j, j + 1}, array.clone(),
                        String.format("➡️ Shifting %d one position right", array[j + 1])));
                
                j--;
            }

            // Insert the key at its correct position
            array[j + 1] = key;
            steps.add(new SortStep(SortStep.StepType.INSERT, new int[]{j + 1}, array.clone(),
                    String.format("📍 Inserted %d at position %d", key, j + 1)));

            // Mark all elements from 0 to i as sorted
            int[] sortedRange = createRange(0, i);
            steps.add(new SortStep(SortStep.StepType.SORTED, sortedRange, array.clone(),
                    String.format("✅ Elements from index 0 to %d are now sorted", i)));
        }

        steps.add(new SortStep(SortStep.StepType.COMPLETED, new int[0], array.clone(),
                "🎉 Insertion Sort completed!"));

        return steps;
    }

    public List<SortStep> mergeSort(int[] array) {
        List<SortStep> steps = new ArrayList<>();
        mergeSortHelper(array, 0, array.length - 1, steps);
        
        // Final step to mark all elements as sorted
        steps.add(new SortStep(SortStep.StepType.COMPLETED, new int[0], array.clone(),
                "🎉 Merge Sort completed!"));
        return steps;
    }

    private void mergeSortHelper(int[] array, int left, int right, List<SortStep> steps) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            // Divide phase
            steps.add(new SortStep(SortStep.StepType.PARTITION_START, createRange(left, right), array.clone(),
                    String.format("🔄 Dividing array from %d to %d", left, right)));

            mergeSortHelper(array, left, mid, steps);
            mergeSortHelper(array, mid + 1, right, steps);
            merge(array, left, mid, right, steps);
        }
    }

    private void merge(int[] array, int left, int mid, int right, List<SortStep> steps) {
        int[] leftArr = Arrays.copyOfRange(array, left, mid + 1);
        int[] rightArr = Arrays.copyOfRange(array, mid + 1, right + 1);

        steps.add(new SortStep(SortStep.StepType.MERGE_START, createRange(left, right), array.clone(),
                String.format("🛠️ Starting merge for range %d-%d", left, right)));

        int i = 0, j = 0, k = left;
        
        // Merge the two arrays
        while (i < leftArr.length && j < rightArr.length) {
            steps.add(new SortStep(SortStep.StepType.COMPARE, new int[]{left + i, mid + 1 + j}, array.clone(),
                    String.format("🔍 Comparing %d and %d", leftArr[i], rightArr[j])));

            if (leftArr[i] <= rightArr[j]) {
                array[k] = leftArr[i];
                steps.add(new SortStep(SortStep.StepType.MERGE_PLACE, new int[]{k}, array.clone(),
                        String.format("📥 Placing %d from left array at position %d", leftArr[i], k)));
                i++;
            } else {
                array[k] = rightArr[j];
                steps.add(new SortStep(SortStep.StepType.MERGE_PLACE, new int[]{k}, array.clone(),
                        String.format("📥 Placing %d from right array at position %d", rightArr[j], k)));
                j++;
            }
            k++;
        }

        // Copy remaining elements from left array
        while (i < leftArr.length) {
            array[k] = leftArr[i];
            steps.add(new SortStep(SortStep.StepType.MERGE_PLACE, new int[]{k}, array.clone(),
                    String.format("📥 Placing remaining %d from left array at position %d", leftArr[i], k)));
            i++;
            k++;
        }

        // Copy remaining elements from right array
        while (j < rightArr.length) {
            array[k] = rightArr[j];
            steps.add(new SortStep(SortStep.StepType.MERGE_PLACE, new int[]{k}, array.clone(),
                    String.format("📥 Placing remaining %d from right array at position %d", rightArr[j], k)));
            j++;
            k++;
        }

        // Mark the merged range as sorted
        steps.add(new SortStep(SortStep.StepType.MERGED, createRange(left, right), array.clone(),
                String.format("✅ Merged range %d-%d successfully", left, right)));
    }

    public List<SortStep> quickSort(int[] array) {
        List<SortStep> steps = new ArrayList<>();
        quickSortHelper(array, 0, array.length - 1, steps);
        
        steps.add(new SortStep(SortStep.StepType.COMPLETED, new int[0], array.clone(),
                "🎉 Quick Sort completed!"));
        return steps;
    }

    private void quickSortHelper(int[] array, int low, int high, List<SortStep> steps) {
        if (low < high) {
            steps.add(new SortStep(SortStep.StepType.PARTITION_START, createRange(low, high), array.clone(),
                    String.format("🔄 Processing partition from %d to %d", low, high)));

            int pi = partition(array, low, high, steps);

            // Recursively sort left and right partitions
            quickSortHelper(array, low, pi - 1, steps);
            quickSortHelper(array, pi + 1, high, steps);
        }
    }

    private int partition(int[] array, int low, int high, List<SortStep> steps) {
        int pivot = array[high];
        steps.add(new SortStep(SortStep.StepType.PIVOT_SELECT, new int[]{high}, array.clone(),
                String.format("🎯 Selected pivot: %d", pivot)));

        int i = low - 1; // Index of smaller element

        for (int j = low; j < high; j++) {
            steps.add(new SortStep(SortStep.StepType.COMPARE, new int[]{j, high}, array.clone(),
                    String.format("🔍 Comparing %d with pivot %d", array[j], pivot)));

            if (array[j] <= pivot) {
                i++;
                if (i != j) {
                    // Swap elements
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;

                    steps.add(new SortStep(SortStep.StepType.SWAP, new int[]{i, j}, array.clone(),
                            String.format("🔄 Swapped %d and %d", array[i], array[j])));
                }
            }
        }

        // Place pivot in its final position
        if (i + 1 != high) {
            int temp = array[i + 1];
            array[i + 1] = array[high];
            array[high] = temp;

            steps.add(new SortStep(SortStep.StepType.PIVOT_FINAL, new int[]{i + 1, high}, array.clone(),
                    String.format("📍 Placed pivot %d at its final position %d", pivot, i + 1)));
        }

        // Mark the pivot position as sorted
        steps.add(new SortStep(SortStep.StepType.SORTED, new int[]{i + 1}, array.clone(),
                String.format("✅ Pivot %d is now in its final sorted position %d", pivot, i + 1)));

        return i + 1;
    }

    private void swap(int[] array, int i, int j, List<SortStep> steps) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        steps.add(new SortStep(
            SortStep.StepType.SWAP,
            new int[]{i, j},
            array.clone(),
            String.format("🔄 Swapped %d and %d", array[i], array[j])
        ));
    }
}
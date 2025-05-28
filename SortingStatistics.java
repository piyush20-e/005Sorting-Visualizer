package com.visualizer.sorting;

class SortingStatistics {
    private int comparisons;
    private int swaps;
    private int arrayAccesses;
    private long startTime;
    private long endTime;
    private String algorithmName;
    
    public SortingStatistics(String algorithmName) {
        this.algorithmName = algorithmName;
        reset();
    }
    
    public void reset() {
        comparisons = 0;
        swaps = 0;
        arrayAccesses = 0;
        startTime = 0;
        endTime = 0;
    }
    
    public void startTiming() {
        startTime = System.currentTimeMillis();
    }
    
    public void endTiming() {
        endTime = System.currentTimeMillis();
    }
    
    public void incrementComparisons() { comparisons++; }
    public void incrementSwaps() { swaps++; }
    public void incrementArrayAccesses() { arrayAccesses++; }

    public int getComparisons() { return comparisons; }
    public int getSwaps() { return swaps; }
    public int getArrayAccesses() { return arrayAccesses; }
    public long getExecutionTime() { return endTime - startTime; }
    public String getAlgorithmName() { return algorithmName; }
    
    @Override
    public String toString() {
        return String.format(
            "%s Statistics:\n" +
            "Comparisons: %d\n" +
            "Swaps: %d\n" +
            "Array Accesses: %d\n" +
            "Execution Time: %d ms",
            algorithmName, comparisons, swaps, arrayAccesses, getExecutionTime()
        );
    }
}

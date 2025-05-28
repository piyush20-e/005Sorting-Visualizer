package com.visualizer.sorting;

class SortStep {
    public enum StepType {
        COMPARE, SWAP, SORTED, CURRENT, SELECT, INSERT, SHIFT,
        DIVIDE, MERGE_START, MERGE_PREP, MERGE_PLACE, MERGED,
        PARTITION_START, PIVOT_SELECT, PIVOT_PLACED, PIVOT_FINAL,
        COMPLETED
    }
    
    private StepType type;
    private int[] highlightIndices;
    private int[] arrayState;
    private String description;
    
    public SortStep(StepType type, int[] highlightIndices, int[] arrayState, String description) {
        this.type = type;
        this.highlightIndices = highlightIndices.clone();
        this.arrayState = arrayState.clone();
        this.description = description;
    }
    
    public StepType getType() { return type; }
    public int[] getHighlightIndices() { return highlightIndices; }
    public int[] getArrayState() { return arrayState; }
    public String getDescription() { return description; }
}

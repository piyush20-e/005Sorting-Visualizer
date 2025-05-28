package com.visualizer.sorting;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class VisualizerController {
    private int[] array;
    private int arraySize = 50;
    private BarVisualizer barVisualizer;
    private SortingAlgorithms sortingAlgorithms;
    private AnimationManager animationManager;
    private CodeTracker codeTracker;
    private VBox visualizationArea;
    private Label statusLabel;
    private boolean isRunning = false;
    
    public VisualizerController() {
        initializeComponents();
        generateRandomArray();
    }
    
    private void initializeComponents() {
        barVisualizer = new BarVisualizer();
        sortingAlgorithms = new SortingAlgorithms();
        animationManager = new AnimationManager();
        
        visualizationArea = new VBox(10);
        visualizationArea.setAlignment(Pos.CENTER);
        visualizationArea.setPadding(new Insets(20));
        
        statusLabel = new Label("Ready to sort! 🎯");
        statusLabel.setTextFill(Color.WHITE);
        statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        statusLabel.setStyle(
            "-fx-background-color: rgba(0, 0, 0, 0.5); " +
            "-fx-background-radius: 10; " +
            "-fx-padding: 10;"
        );
        
        visualizationArea.getChildren().addAll(barVisualizer.getVisualizationPane(), statusLabel);
    }
    
    public VBox getVisualizationArea() {
        return visualizationArea;
    }

    public void setCodeTracker(CodeTracker codeTracker) {
        this.codeTracker = codeTracker;
    }
    
    public void setArraySize(int size) {
        if (!isRunning) {
            this.arraySize = size;
            generateRandomArray();
        }
    }
    
    public void generateRandomArray() {
        array = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = ThreadLocalRandom.current().nextInt(10, 400);
        }
        barVisualizer.updateVisualization(array);
        updateStatus("Array generated with " + arraySize + " elements 📊");
    }
    
    public void shuffleArray() {
        if (!isRunning) {
            List<Integer> list = new ArrayList<>();
            for (int value : array) {
                list.add(value);
            }
            Collections.shuffle(list);
            for (int i = 0; i < array.length; i++) {
                array[i] = list.get(i);
            }
            barVisualizer.updateVisualization(array);
            updateStatus("Array shuffled! 🎲");
        }
    }
    
    public void resetArray() {
        if (isRunning) {
            animationManager.stopAnimation();
            isRunning = false;
        }
        barVisualizer.resetAll();

        if (codeTracker != null) {
            codeTracker.clearHighlight();
        }
        
        updateStatus("Ready to sort! 🎯");
    }

    public void startSorting(String algorithm, double speed, boolean showCode) {
        if (isRunning) return;
        
        barVisualizer.resetAll();
        
        isRunning = true;
        updateStatus("Starting " + algorithm + "... ⚡");
        
        int[] arrayCopy = array.clone();
        List<SortStep> steps = new ArrayList<>();
        
        switch (algorithm) {
            case "Bubble Sort":
                steps = sortingAlgorithms.bubbleSort(arrayCopy);
                break;
            case "Selection Sort":
                steps = sortingAlgorithms.selectionSort(arrayCopy);
                break;
            case "Insertion Sort":
                steps = sortingAlgorithms.insertionSort(arrayCopy);
                break;
            case "Merge Sort":
                steps = sortingAlgorithms.mergeSort(arrayCopy);
                break;
            case "Quick Sort":
                steps = sortingAlgorithms.quickSort(arrayCopy);
                break;
        }

        barVisualizer.updateVisualization(arrayCopy);

        animationManager.playStepsWithCodeTracking(steps, barVisualizer, speed, 
            () -> {
                isRunning = false;
                updateStatus("Sorting completed! ✨ Array is now sorted.");

                if (codeTracker != null && showCode) {
                    codeTracker.clearHighlight();
                }
            },
            (step) -> {
                updateStatus(step.getDescription());

                if (showCode && codeTracker != null) {
                    int lineIndex = getCodeLineForStep(step, algorithm);
                    if (lineIndex >= 0) {
                        codeTracker.highlightLine(lineIndex);
                    }
                }
            }
        );
    }
    private int getCodeLineForStep(SortStep step, String algorithm) {

        switch (algorithm) {
            case "Bubble Sort":
                switch (step.getType()) {
                    case COMPARE: return 5;
                    case SWAP: return 6; 
                    case SORTED: return 9;
                    default: return 2;
                }
                
            case "Selection Sort":
                switch (step.getType()) {
                    case CURRENT: return 2;
                    case COMPARE: return 5;
                    case SELECT: return 6;
                    case SWAP: return 7;
                    default: return 3;
                }
                
            case "Insertion Sort":
                switch (step.getType()) {
                    case CURRENT: return 2; 
                    case COMPARE: return 4; 
                    case SHIFT: return 5;  
                    case INSERT: return 7;
                    default: return 1; 
                }
                
            case "Merge Sort":
                switch (step.getType()) {
                    case DIVIDE: return 2;        
                    case MERGE_PREP: return 8;   
                    case COMPARE: return 11;     
                    case MERGE_PLACE: return 12; 
                    default: return 3;  
                }
                
            case "Quick Sort":
                switch (step.getType()) {
                    case PARTITION_START: return 2; 
                    case PIVOT_SELECT: return 7; 
                    case COMPARE: return 10;    
                    case SWAP: return 12;          
                    case PIVOT_FINAL: return 13;   
                    default: return 1;             
                }
                
            default:
                return -1; 
        }
    }
    
    public void pauseSorting() {
        if (isRunning) {
            animationManager.pauseAnimation();
            updateStatus("Sorting paused ⏸️");
        }
    }
    
    private void updateStatus(String message) {
        statusLabel.setText(message);
    }
}
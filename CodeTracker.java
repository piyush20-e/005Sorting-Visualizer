package com.visualizer.sorting;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.*;

public class CodeTracker {
    private VBox codePanel;
    private Map<String, List<String>> algorithmCodes;
    private List<Label> codeLabels;
    private String currentAlgorithm;
    private int currentLineIndex = -1;
    
    public CodeTracker() {
        initializeCodePanel();
        initializeAlgorithmCodes();
        loadAlgorithmCode("Bubble Sort");
    }

    private void initializeCodePanel() {
        codePanel = new VBox(3);
        codePanel.setPadding(new Insets(15));
        codePanel.setPrefWidth(350);
        codePanel.setStyle(
            "-fx-background-color: rgba(40, 44, 52, 0.95); " +
            "-fx-background-radius: 10; " +
            "-fx-border-color: #61afef; " +
            "-fx-border-width: 2; " +
            "-fx-border-radius: 10;"
        );
        Label title = new Label("💻 Algorithm Code");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        title.setAlignment(Pos.CENTER);
        title.setMaxWidth(Double.MAX_VALUE);
        title.setStyle("-fx-background-color: #61afef; -fx-background-radius: 5; -fx-padding: 8;");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefHeight(300);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        
        VBox codeContainer = new VBox(1);
        codeContainer.setPadding(new Insets(10));
        codeLabels = new ArrayList<>();
        scrollPane.setContent(codeContainer);
        
        codePanel.getChildren().addAll(title, scrollPane);
    }
    private void initializeAlgorithmCodes() {
        algorithmCodes = new HashMap<>();

        algorithmCodes.put("Bubble Sort", Arrays.asList(
            "function bubbleSort(array):",
            "  n = array.length",
            "  for i = 0 to n-2:",
            "    swapped = false",
            "    for j = 0 to n-i-2:",
            "      if array[j] > array[j+1]:",
            "        swap(array[j], array[j+1])",
            "        swapped = true",
            "    if not swapped:",
            "      break",
            "  return array"
        ));

        algorithmCodes.put("Selection Sort", Arrays.asList(
            "function selectionSort(array):",
            "  n = array.length",
            "  for i = 0 to n-2:",
            "    minIndex = i",
            "    for j = i+1 to n-1:",
            "      if array[j] < array[minIndex]:",
            "        minIndex = j",
            "    swap(array[i], array[minIndex])",
            "  return array"
        ));

        algorithmCodes.put("Insertion Sort", Arrays.asList(
            "function insertionSort(array):",
            "  for i = 1 to n-1:",
            "    key = array[i]",
            "    j = i - 1",
            "    while j >= 0 and array[j] > key:",
            "      array[j+1] = array[j]",
            "      j = j - 1",
            "    array[j+1] = key",
            "  return array"
        ));

        algorithmCodes.put("Merge Sort", Arrays.asList(
            "function mergeSort(array, left, right):",
            "  if left < right:",
            "    mid = (left + right) / 2",
            "    mergeSort(array, left, mid)",
            "    mergeSort(array, mid+1, right)",
            "    merge(array, left, mid, right)",
            "",
            "function merge(array, left, mid, right):",
            "  create leftArray and rightArray",
            "  i = 0, j = 0, k = left",
            "  while i < leftSize and j < rightSize:",
            "    if leftArray[i] <= rightArray[j]:",
            "      array[k] = leftArray[i]",
            "      i++",
            "    else:",
            "      array[k] = rightArray[j]",
            "      j++",
            "    k++",
            "  copy remaining elements"
        ));

        algorithmCodes.put("Quick Sort", Arrays.asList(
            "function quickSort(array, low, high):",
            "  if low < high:",
            "    pivotIndex = partition(array, low, high)",
            "    quickSort(array, low, pivotIndex-1)",
            "    quickSort(array, pivotIndex+1, high)",
            "",
            "function partition(array, low, high):",
            "  pivot = array[high]",
            "  i = low - 1",
            "  for j = low to high-1:",
            "    if array[j] < pivot:",
            "      i++",
            "      swap(array[i], array[j])",
            "  swap(array[i+1], array[high])",
            "  return i+1"
        ));
    }

    public void loadAlgorithmCode(String algorithm) {
        currentAlgorithm = algorithm;
        currentLineIndex = -1;
        
        ScrollPane scrollPane = (ScrollPane) codePanel.getChildren().get(1);
        VBox codeContainer = (VBox) scrollPane.getContent();
        codeContainer.getChildren().clear();
        codeLabels.clear();
        
        List<String> lines = algorithmCodes.get(algorithm);
        if (lines != null) {
            for (int i = 0; i < lines.size(); i++) {
                Label lineLabel = createCodeLineLabel(lines.get(i), i);
                codeLabels.add(lineLabel);
                codeContainer.getChildren().add(lineLabel);
            }
        }
    }
    private Label createCodeLineLabel(String codeLine, int lineNumber) {
        String formattedLine = String.format("%2d │ %s", lineNumber + 1, codeLine);
        
        Label label = new Label(formattedLine);
        label.setTextFill(Color.LIGHTGRAY);
        label.setFont(Font.font("Consolas", 12));
        label.setPadding(new Insets(3, 8, 3, 8));
        label.setMaxWidth(Double.MAX_VALUE);
        label.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-background-radius: 3;"
        );
        label.setOnMouseEntered(e -> {
            if (codeLabels.indexOf(label) != currentLineIndex) {
                label.setStyle(
                    "-fx-background-color: rgba(255, 255, 255, 0.1); " +
                    "-fx-background-radius: 3;"
                );
            }
        });
        
        label.setOnMouseExited(e -> {
            if (codeLabels.indexOf(label) != currentLineIndex) {
                label.setStyle(
                    "-fx-background-color: transparent; " +
                    "-fx-background-radius: 3;"
                );
            }
        });
        
        return label;
    }

    public void highlightLine(int lineIndex) {
        if (currentLineIndex >= 0 && currentLineIndex < codeLabels.size()) {
            Label prevLabel = codeLabels.get(currentLineIndex);
            prevLabel.setStyle(
                "-fx-background-color: transparent; " +
                "-fx-background-radius: 3;"
            );
            prevLabel.setTextFill(Color.LIGHTGRAY);
        }
        if (lineIndex >= 0 && lineIndex < codeLabels.size()) {
            Label currentLabel = codeLabels.get(lineIndex);
            currentLabel.setStyle(
                "-fx-background-color: #e06c75; " +
                "-fx-background-radius: 3; " +
                "-fx-effect: dropshadow(gaussian, rgba(224, 108, 117, 0.8), 8, 0, 0, 0);"
            );
            currentLabel.setTextFill(Color.WHITE);
            
            currentLineIndex = lineIndex;

            scrollToLine(lineIndex);
        }
    }

    private void scrollToLine(int lineIndex) {
        ScrollPane scrollPane = (ScrollPane) codePanel.getChildren().get(1);
        VBox codeContainer = (VBox) scrollPane.getContent();
        
        if (lineIndex >= 0 && lineIndex < codeContainer.getChildren().size()) {
            double totalHeight = codeContainer.getHeight();
            double lineHeight = totalHeight / codeContainer.getChildren().size();
            double targetPosition = (lineIndex * lineHeight) / totalHeight;

            scrollPane.setVvalue(Math.max(0, Math.min(1, targetPosition - 0.3)));
        }
    }

    public void clearHighlight() {
        if (currentLineIndex >= 0 && currentLineIndex < codeLabels.size()) {
            Label currentLabel = codeLabels.get(currentLineIndex);
            currentLabel.setStyle(
                "-fx-background-color: transparent; " +
                "-fx-background-radius: 3;"
            );
            currentLabel.setTextFill(Color.LIGHTGRAY);
        }
        currentLineIndex = -1;
    }

    public VBox getCodePanel() {
        return codePanel;
    }
    
    public String getCurrentAlgorithm() {
        return currentAlgorithm;
    }
    
    public int getCurrentLineIndex() {
        return currentLineIndex;
    }
}
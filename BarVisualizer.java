package com.visualizer.sorting;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.util.*;

class BarVisualizer {
    private HBox visualizationPane;
    private List<Rectangle> bars;
    private List<Text> valueLabels;
    private Set<Integer> sortedIndices = new HashSet<>();
    
    public BarVisualizer() {
        visualizationPane = new HBox(2);
        visualizationPane.setAlignment(Pos.BOTTOM_CENTER);
        visualizationPane.setPrefHeight(400);
        visualizationPane.setStyle("-fx-background-color: rgba(255, 255, 255, 0.05); -fx-background-radius: 10;");
        bars = new ArrayList<>();
        valueLabels = new ArrayList<>();
    }
    
    public HBox getVisualizationPane() {
        return visualizationPane;
    }
    
    public void updateVisualization(int[] array) {
        visualizationPane.getChildren().clear();
        bars.clear();
        valueLabels.clear();
        
        // Handle edge cases
        if (array == null || array.length == 0) {
            return;
        }
        
        double maxValue = Arrays.stream(array).max().getAsInt();
        // Prevent division by zero
        if (maxValue == 0) {
            maxValue = 1;
        }
        
        double barWidth = Math.max(8, Math.min(50, 800.0 / array.length));
        
        for (int i = 0; i < array.length; i++) {
            VBox barContainer = new VBox();
            barContainer.setAlignment(Pos.BOTTOM_CENTER);
            barContainer.setSpacing(2);

            Text valueLabel = new Text();
            if (array.length <= 30) {
                valueLabel.setText(String.valueOf(array[i]));
                valueLabel.setFill(Color.WHITE);
                valueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));
            }
            valueLabels.add(valueLabel);

            Rectangle bar = new Rectangle();
            bar.setWidth(barWidth);
            
            // Calculate height with minimum height for zero values
            double barHeight = array[i] == 0 ? 5 : (array[i] / maxValue) * 350;
            bar.setHeight(Math.max(5, barHeight)); // Minimum height of 5 pixels
            
            // Set default color - check if already marked as sorted
            if (sortedIndices.contains(i)) {
                bar.setFill(ColorScheme.SORTED);
            } else {
                bar.setFill(Color.LIGHTBLUE);
            }
            
            bar.setStroke(Color.DARKBLUE);
            bar.setStrokeWidth(1);
            bar.setArcWidth(3);
            bar.setArcHeight(3);
            
            bars.add(bar);
            
            if (array.length <= 30) {
                barContainer.getChildren().addAll(valueLabel, bar);
            } else {
                barContainer.getChildren().add(bar);
            }
            
            visualizationPane.getChildren().add(barContainer);
        }
    }
    
    public void highlightBars(int[] indices, Color color) {
        if (indices == null) return;
        
        // Reset non-sorted bars first
        resetColors();
        
        // Then highlight the specified bars
        for (int index : indices) {
            if (index >= 0 && index < bars.size() && !sortedIndices.contains(index)) {
                bars.get(index).setFill(color);
            }
        }
    }
    
    public void resetColors() {
        for (int i = 0; i < bars.size(); i++) {
            if (!sortedIndices.contains(i)) {
                bars.get(i).setFill(Color.LIGHTBLUE);
            } else {
                bars.get(i).setFill(ColorScheme.SORTED);
            }
        }
    }
    
    public void swapBars(int i, int j, int[] array) {
        if (array == null || i < 0 || i >= bars.size() || j < 0 || j >= bars.size()) {
            return;
        }
        
        Rectangle bar1 = bars.get(i);
        Rectangle bar2 = bars.get(j);
        
        double maxValue = Arrays.stream(array).max().getAsInt();
        if (maxValue == 0) {
            maxValue = 1;
        }
        
        // Update bar heights
        double height1 = array[i] == 0 ? 5 : (array[i] / maxValue) * 350;
        double height2 = array[j] == 0 ? 5 : (array[j] / maxValue) * 350;
        
        bar1.setHeight(Math.max(5, height1));
        bar2.setHeight(Math.max(5, height2));
        
        // Update value labels if they exist
        if (array.length <= 30 && !valueLabels.isEmpty() && 
            i < valueLabels.size() && j < valueLabels.size() &&
            valueLabels.get(i) != null && valueLabels.get(j) != null) {
            valueLabels.get(i).setText(String.valueOf(array[i]));
            valueLabels.get(j).setText(String.valueOf(array[j]));
        }
    }
    
    public void markSorted(int index) {
        if (index >= 0 && index < bars.size()) {
            bars.get(index).setFill(ColorScheme.SORTED);
            sortedIndices.add(index);
        }
    }
    
    public void markSorted(int[] indices) {
        if (indices == null) return;
        
        for (int index : indices) {
            markSorted(index);
        }
    }

    public void resetAll() {
        sortedIndices.clear();
        for (Rectangle bar : bars) {
            bar.setFill(Color.LIGHTBLUE);
        }
    }
    
    // Additional utility methods for better visualization control
    public void updateBarsFromArray(int[] array) {
        if (array == null || array.length != bars.size()) {
            updateVisualization(array);
            return;
        }
        
        double maxValue = Arrays.stream(array).max().getAsInt();
        if (maxValue == 0) {
            maxValue = 1;
        }
        
        for (int i = 0; i < array.length; i++) {
            Rectangle bar = bars.get(i);
            double barHeight = array[i] == 0 ? 5 : (array[i] / maxValue) * 350;
            bar.setHeight(Math.max(5, barHeight));
            
            // Keep the sorted color if the bar is marked as sorted
            if (!sortedIndices.contains(i)) {
                bar.setFill(Color.LIGHTBLUE);
            }
            
            if (array.length <= 30 && i < valueLabels.size() && valueLabels.get(i) != null) {
                valueLabels.get(i).setText(String.valueOf(array[i]));
            }
        }
    }
    
    public boolean isSorted(int index) {
        return sortedIndices.contains(index);
    }
    
    public int getBarCount() {
        return bars.size();
    }
}
package com.visualizer.sorting;

import javafx.scene.paint.Color;

class ColorScheme {

    public static final Color DEFAULT_BAR = Color.rgb(36, 184, 243);  // Light Sky Blue
    public static final Color COMPARING = Color.rgb(255, 165, 0);      // Orange
    public static final Color SWAPPING = Color.rgb(255, 69, 0);        // Red-Orange
    public static final Color SORTED = Color.rgb(50, 205, 50);         // Lime Green
    public static final Color CURRENT = Color.rgb(255, 215, 0);        // Gold
    public static final Color SELECTED = Color.rgb(147, 112, 219);     // Medium Purple
    public static final Color PIVOT = Color.rgb(220, 20, 60);          // Crimson
    public static final Color MERGING = Color.rgb(30, 144, 255);       // Dodger Blue
    public static final Color SHIFTING = Color.rgb(255, 182, 193);     // Light Pink

    public static final Color COMPLETION_START = Color.rgb(50, 205, 50);    // Lime Green
    public static final Color COMPLETION_END = Color.rgb(34, 139, 34);      // Forest Green

    public static final Color BACKGROUND_PRIMARY = Color.rgb(25, 25, 112);   // Midnight Blue
    public static final Color BACKGROUND_SECONDARY = Color.rgb(0, 0, 0);     // Black
    public static final Color PANEL_BACKGROUND = Color.rgb(255, 255, 255, 0.1);

    public static final Color TEXT_PRIMARY = Color.WHITE;
    public static final Color TEXT_SECONDARY = Color.rgb(220, 220, 220);
    
    public static Color interpolateColor(Color start, Color end, double ratio) {
        double red = start.getRed() + (end.getRed() - start.getRed()) * ratio;
        double green = start.getGreen() + (end.getGreen() - start.getGreen()) * ratio;
        double blue = start.getBlue() + (end.getBlue() - start.getBlue()) * ratio;
        
        return Color.color(
            Math.max(0, Math.min(1, red)),
            Math.max(0, Math.min(1, green)),
            Math.max(0, Math.min(1, blue))
        );
    }
}
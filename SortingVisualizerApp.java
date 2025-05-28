package com.visualizer.sorting;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SortingVisualizerApp extends Application {
    
    private VisualizerController controller;
    private CodeTracker codeTracker;
    private Scene scene;
    
    @Override
    public void start(Stage primaryStage) {
    
        controller = new VisualizerController();
        codeTracker = new CodeTracker();

        controller.setCodeTracker(codeTracker);
  
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background: linear-gradient(to bottom, #1e3c72, #2a5298);");

        VBox topSection = createTopSection();

        VBox centerSection = controller.getVisualizationArea();

        VBox rightPanel = createRightPanel();
        
        root.setTop(topSection);
        root.setCenter(centerSection);
        root.setRight(rightPanel);
        
        scene = new Scene(root, 1400, 800);
        primaryStage.setTitle("🎨 Sorting Visualizer with Code Tracking v1.1");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(600);
        
        primaryStage.show();

        controller.generateRandomArray();
    }
    
    private VBox createTopSection() {
        VBox topSection = new VBox(10);
        topSection.setAlignment(Pos.CENTER);
        topSection.setPadding(new Insets(20));
        
        Text title = new Text("Sorting Algorithm Visualizer");
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        
        Text subtitle = new Text("Watch and learn how different sorting algorithms work!");
        subtitle.setFill(Color.LIGHTGRAY);
        subtitle.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        
        HBox controlsPanel = createControlsPanel();
        
        topSection.getChildren().addAll(title, subtitle, controlsPanel);
        return topSection;
    }
    
    private VBox createRightPanel() {
        VBox rightPanel = new VBox(10);
        rightPanel.setPadding(new Insets(10));
        rightPanel.setPrefWidth(380);

        VBox codePanel = codeTracker.getCodePanel();

        VBox codeControls = createCodeControls();
        
        rightPanel.getChildren().addAll(codePanel, codeControls);
        return rightPanel;
    }
    
    private VBox createCodeControls() {
        VBox controlsBox = new VBox(8);
        controlsBox.setPadding(new Insets(10));
        controlsBox.setStyle(
            "-fx-background-color: rgba(255, 255, 255, 0.1); " +
            "-fx-background-radius: 10;"
        );
        
        Label title = new Label("🎛️ Code Tracking Controls");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        CheckBox showCodeExecution = new CheckBox("Show Code Execution");
        showCodeExecution.setTextFill(Color.WHITE);
        showCodeExecution.setSelected(true);
        showCodeExecution.setId("showCodeExecution");
        showCodeExecution.setStyle("-fx-font-weight: bold;");

        Button testHighlightBtn = new Button("🔍 Test Highlight");
        testHighlightBtn.setStyle(
            "-fx-background-color: #9b59b6; -fx-text-fill: white; " +
            "-fx-font-weight: bold; -fx-background-radius: 5; -fx-padding: 5 10;"
        );
        testHighlightBtn.setOnAction(e -> {

            int randomLine = (int)(Math.random() * 8);
            codeTracker.highlightLine(randomLine);
        });

        Button clearHighlightBtn = new Button("🧹 Clear Highlight");
        clearHighlightBtn.setStyle(
            "-fx-background-color: #34495e; -fx-text-fill: white; " +
            "-fx-font-weight: bold; -fx-background-radius: 5; -fx-padding: 5 10;"
        );
        clearHighlightBtn.setOnAction(e -> codeTracker.clearHighlight());
        
        controlsBox.getChildren().addAll(
            title, showCodeExecution, testHighlightBtn, clearHighlightBtn
        );
        
        return controlsBox;
    }
    
    private HBox createControlsPanel() {
        HBox controlsPanel = new HBox(15);
        controlsPanel.setAlignment(Pos.CENTER);
        controlsPanel.setPadding(new Insets(15));
        controlsPanel.setStyle(
            "-fx-background-color: rgba(255, 255, 255, 0.1); " +
            "-fx-background-radius: 15; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 2);"
        );

        VBox sizeSection = createSizeControls();

        VBox algorithmSection = createAlgorithmControls();

        VBox speedSection = createSpeedControls();

        HBox buttonSection = createActionButtons();
        
        controlsPanel.getChildren().addAll(
            sizeSection,
            createSeparator(),
            algorithmSection,
            createSeparator(),
            speedSection,
            createSeparator(),
            buttonSection
        );
        
        return controlsPanel;
    }
    
    private VBox createAlgorithmControls() {
        VBox algorithmSection = new VBox(8);
        algorithmSection.setAlignment(Pos.CENTER);
        
        Label algoLabel = createStyledLabel("Algorithm:");
        
        ComboBox<String> algorithmCombo = new ComboBox<>();
        algorithmCombo.getItems().addAll(
            "Bubble Sort", "Selection Sort", "Insertion Sort", 
            "Merge Sort", "Quick Sort"
        );
        algorithmCombo.setValue("Bubble Sort");
        algorithmCombo.setPrefWidth(150);
        algorithmCombo.setStyle(
            "-fx-base: #4a90e2; " +
            "-fx-background-radius: 5; " +
            "-fx-font-weight: bold;"
        );
        algorithmCombo.setId("algorithmCombo");

        algorithmCombo.setOnAction(e -> {
            String selectedAlgo = algorithmCombo.getValue();
            codeTracker.loadAlgorithmCode(selectedAlgo);
        });
        
        algorithmSection.getChildren().addAll(algoLabel, algorithmCombo);
        return algorithmSection;
    }
    
    private VBox createSizeControls() {
        VBox sizeSection = new VBox(8);
        sizeSection.setAlignment(Pos.CENTER);
        
        Label sizeLabel = createStyledLabel("Array Size:");
        
        Slider sizeSlider = new Slider(10, 100, 50);
        sizeSlider.setShowTickLabels(true);
        sizeSlider.setShowTickMarks(true);
        sizeSlider.setMajorTickUnit(20);
        sizeSlider.setBlockIncrement(5);
        sizeSlider.setPrefWidth(200);
        sizeSlider.setStyle("-fx-base: #4a90e2;");
        
        Label sizeValue = createStyledLabel("50");
        sizeValue.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        
        sizeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int size = newVal.intValue();
            sizeValue.setText(String.valueOf(size));
            controller.setArraySize(size);
        });
        
        sizeSection.getChildren().addAll(sizeLabel, sizeSlider, sizeValue);
        return sizeSection;
    }
    
    private VBox createSpeedControls() {
        VBox speedSection = new VBox(8);
        speedSection.setAlignment(Pos.CENTER);
        
        Label speedLabel = createStyledLabel("Speed:");
        
        Slider speedSlider = new Slider(1, 10, 5);
        speedSlider.setShowTickLabels(true);
        speedSlider.setShowTickMarks(true);
        speedSlider.setMajorTickUnit(3);
        speedSlider.setPrefWidth(150);
        speedSlider.setStyle("-fx-base: #4a90e2;");
        speedSlider.setId("speedSlider");
        
        Label speedValue = createStyledLabel("5x");
        speedValue.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        speedSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            speedValue.setText(String.format("%.0fx", newVal.doubleValue()));
        });
        
        speedSection.getChildren().addAll(speedLabel, speedSlider, speedValue);
        return speedSection;
    }
    
    private HBox createActionButtons() {
        HBox buttonSection = new HBox(10);
        buttonSection.setAlignment(Pos.CENTER);
        
        Button startBtn = createStyledButton("🚀 Start", "#4CAF50");
        Button pauseBtn = createStyledButton("⏸️ Pause", "#FF9800");
        Button resetBtn = createStyledButton("🔄 Reset", "#f44336");
        Button shuffleBtn = createStyledButton("🎲 Shuffle", "#2196F3");

        startBtn.setOnAction(e -> {
            ComboBox<String> algorithmCombo = (ComboBox<String>) scene.lookup("#algorithmCombo");
            Slider speedSlider = (Slider) scene.lookup("#speedSlider");
            CheckBox showCodeExecution = (CheckBox) scene.lookup("#showCodeExecution");
            
            String selectedAlgo = algorithmCombo.getValue();
            double speed = speedSlider.getValue();
            boolean showCode = showCodeExecution.isSelected();
            
            controller.startSorting(selectedAlgo, speed, showCode);
        });
        
        pauseBtn.setOnAction(e -> controller.pauseSorting());
        resetBtn.setOnAction(e -> controller.resetArray());
        shuffleBtn.setOnAction(e -> controller.shuffleArray());
        
        buttonSection.getChildren().addAll(startBtn, pauseBtn, resetBtn, shuffleBtn);
        return buttonSection;
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        return label;
    }
    
    private Button createStyledButton(String text, String color) {
        Button btn = new Button(text);
        btn.setStyle(String.format(
            "-fx-background-color: %s; -fx-text-fill: white; " +
            "-fx-font-weight: bold; -fx-background-radius: 5; " +
            "-fx-padding: 8 16 8 16; -fx-font-size: 12px;", color
        ));
        
        btn.setOnMouseEntered(e -> btn.setStyle(btn.getStyle() + "-fx-scale-x: 1.05; -fx-scale-y: 1.05;"));
        btn.setOnMouseExited(e -> btn.setStyle(btn.getStyle().replace("-fx-scale-x: 1.05; -fx-scale-y: 1.05;", "")));
        
        return btn;
    }
    
    private Separator createSeparator() {
        Separator separator = new Separator();
        separator.setOrientation(javafx.geometry.Orientation.VERTICAL);
        separator.setStyle("-fx-background-color: rgba(255, 255, 255, 0.3);");
        separator.setPrefHeight(60);
        return separator;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
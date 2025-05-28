package com.visualizer.sorting;

import javafx.animation.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.util.List;
import java.util.function.Consumer;

class AnimationManager {
    private Timeline timeline;
    private boolean isPaused = false;
    private int currentStepIndex = 0;
    private List<SortStep> steps;
    private BarVisualizer visualizer;
    private Consumer<SortStep> onStepUpdate;

    public void playSteps(List<SortStep> steps, BarVisualizer visualizer, double speed, 
                         Runnable onComplete, Consumer<SortStep> onStepUpdate) {
        playStepsWithCodeTracking(steps, visualizer, speed, onComplete, onStepUpdate);
    }

    public void playStepsWithCodeTracking(List<SortStep> steps, BarVisualizer visualizer, double speed, 
                                         Runnable onComplete, Consumer<SortStep> onStepUpdate) {
        this.steps = steps;
        this.visualizer = visualizer;
        this.onStepUpdate = onStepUpdate;
        this.currentStepIndex = 0;
        this.isPaused = false;

        double durationMs = Math.max(100, 1200.0 / speed);
        
        timeline = new Timeline();
        
        for (int i = 0; i < steps.size(); i++) {
            final int stepIndex = i;
            KeyFrame keyFrame = new KeyFrame(
                Duration.millis(durationMs * (i + 1)),
                e -> executeStep(stepIndex)
            );
            timeline.getKeyFrames().add(keyFrame);
        }
        
        timeline.setOnFinished(e -> {
            if (onComplete != null) {
                onComplete.run();
            }
        });
        
        timeline.play();
    }
    
    private void executeStep(int stepIndex) {
        if (stepIndex >= steps.size()) return;
        
        SortStep step = steps.get(stepIndex);
        currentStepIndex = stepIndex;

        // Update the entire array state first
        visualizer.updateBarsFromArray(step.getArrayState());
        
        // Then apply specific highlighting based on step type
        switch (step.getType()) {
            case COMPARE:
                visualizer.highlightBars(step.getHighlightIndices(), ColorScheme.COMPARING);
                break;
                
            case SWAP:
                visualizer.highlightBars(step.getHighlightIndices(), ColorScheme.SWAPPING);
                break;
                
            case SHIFT:
                visualizer.highlightBars(step.getHighlightIndices(), ColorScheme.SHIFTING);
                break;
                
            case MERGE_PLACE:
                visualizer.highlightBars(step.getHighlightIndices(), ColorScheme.MERGING);
                break;
                
            case PIVOT_SELECT:
                visualizer.highlightBars(step.getHighlightIndices(), ColorScheme.PIVOT);
                break;
                
            case SORTED:
                for (int index : step.getHighlightIndices()) {
                    visualizer.markSorted(index);
                }
                break;
                
            case MERGED:
                visualizer.highlightBars(step.getHighlightIndices(), ColorScheme.MERGING);
                for (int index : step.getHighlightIndices()) {
                    visualizer.markSorted(index);
                }
                break;
                
            case COMPLETED:
                createCompletionAnimation();
                break;
        }

        if (onStepUpdate != null) {
            onStepUpdate.accept(step);
        }
    }
    
    private void createCompletionAnimation() {
        Timeline waveAnimation = new Timeline();
        
        for (int i = 0; i < visualizer.getBarCount(); i++) {
            final int index = i;
            KeyFrame keyFrame = new KeyFrame(
                Duration.millis(i * 30),
                e -> visualizer.markSorted(index)
            );
            waveAnimation.getKeyFrames().add(keyFrame);
        }
        
        waveAnimation.play();
    }
    
    public void pauseAnimation() {
        if (timeline != null && !isPaused) {
            timeline.pause();
            isPaused = true;
        } else if (timeline != null && isPaused) {
            timeline.play();
            isPaused = false;
        }
    }
    
    public void stopAnimation() {
        if (timeline != null) {
            timeline.stop();
        }
        isPaused = false;
        currentStepIndex = 0;
    }
    
    public boolean isRunning() {
        return timeline != null && timeline.getStatus() == Animation.Status.RUNNING;
    }
    
    public boolean isPaused() {
        return isPaused;
    }
    
    public int getCurrentStepIndex() {
        return currentStepIndex;
    }
    
    public int getTotalSteps() {
        return steps != null ? steps.size() : 0;
    }
}
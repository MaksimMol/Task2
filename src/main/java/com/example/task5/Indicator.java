package com.example.task5;

import javafx.scene.layout.Pane;

public class Indicator {

    private Pane panel = new Pane();

    public void add(Pane pane) {
        panel.getChildren().add(pane);
    }

    public void show(Pane pane) {
        pane.getChildren().add(panel);
    }
}

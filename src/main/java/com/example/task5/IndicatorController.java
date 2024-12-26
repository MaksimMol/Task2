package com.example.task5;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class IndicatorController {

    @FXML
    private TextField startField;

    @FXML
    private TextField endField;

    @FXML
    private TextField currentField;

    @FXML
    private Pane indicatorPane;

    @FXML
    private Label statusLabel;

    public void createIndicator() {
        try {
            float start = Float.parseFloat(startField.getText());
            float end = Float.parseFloat(endField.getText());
            float current = Float.parseFloat(currentField.getText());

            if (start >= end) {
                statusLabel.setText("Ошибка: начало должно быть меньше конца.");
                return;
            }

            indicatorPane.getChildren().clear();
            Builder builder = new BuilderIndicatorMini();
            Director director = new Director();
            Indicator indicator = director.construct(builder, start, end, current);
            indicator.show(indicatorPane);
            updateStatus(start, end, current);

        } catch (NumberFormatException e) {
            statusLabel.setText("Ошибка: введите корректные числовые значения.");
        }
    }

    private void updateStatus(float start, float end, float current) {
        float range = end - start;
        float lowThreshold = start + range * 0.25f;
        float highThreshold = start + range * 0.75f;

        String status;
        if (current < lowThreshold) {
            status = "Ниже нормы";
            statusLabel.setStyle("-fx-text-fill: Синий;");
        } else if (current > highThreshold) {
            status = "Выше нормы";
            statusLabel.setStyle("-fx-text-fill: Оранжевый;");
        } else {
            status = "В норме";
            statusLabel.setStyle("-fx-text-fill: Зеленый;");
        }

        statusLabel.setText("Состояние: " + status);
    }
}

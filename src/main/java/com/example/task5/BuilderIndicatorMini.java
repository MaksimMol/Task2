package com.example.task5;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class BuilderIndicatorMini implements Builder {

    private Indicator indicator = new Indicator();
    private float start, stop;

    @Override
    public void setView(int N, char norm, char select) {
    }

    @Override
    public void lineBounds(float start, float stop) {
        this.start = start;
        this.stop = stop;

        Pane pane = new Pane();
        Text textStart = new Text(String.valueOf(start));
        textStart.setX(0);  // Позиция по горизонтали
        textStart.setY(170); // Позиция по вертикали, чуть ниже линии
        Text textStop = new Text(String.valueOf(stop));
        textStop.setX(490);  // Позиция по горизонтали
        textStop.setY(170);  // Позиция по вертикали, чуть ниже линии
        Line line = new Line();
        line.setStartX(5);
        line.setStartY(150);
        line.setEndX(500);
        line.setEndY(150);

        pane.getChildren().addAll(textStart, line, textStop);
        indicator.add(pane);
    }

    @Override
    public void linePaint(float measure) {
        AnchorPane pane = new AnchorPane();
        double x = (measure - start) / (stop - start) * 400;

        Arc arc = new Arc();
        arc.setFill(Color.RED);
        arc.setCenterX(x);
        arc.setCenterY(150);
        arc.setRadiusX(10);
        arc.setRadiusY(10);
        arc.setLength(180);
        arc.setStartAngle(0);

        pane.getChildren().add(arc);
        indicator.add(pane);
    }


    @Override
    public void lineMark(String measure) {
        Text mark = new Text(measure);
        indicator.add(new FlowPane(mark));
    }

    @Override
    public void addTitle(String title) {
        Text title1 = new Text(title);
        indicator.add(new FlowPane(title1));
    }

    @Override
    public Indicator build() {
        return indicator;
    }
}

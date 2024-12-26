package com.example.task5;

public class Director {
    public Indicator construct(Builder builder, float start, float stop, float current) {
        builder.lineBounds(start, stop);
        builder.linePaint(current);
        builder.lineMark(String.format("%.1f", current));
        return builder.build();
    }
}

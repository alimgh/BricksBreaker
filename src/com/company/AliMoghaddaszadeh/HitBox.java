package com.company.AliMoghaddaszadeh;

import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Shape;

public class HitBox {

    private static final int R = Ball.R + 2;
    private Arc top, left, bottom, right;
    private Ball ball;
    private double centerX, centerY;

    public HitBox(Ball ball){

        centerX = ball.getX();
        centerY = ball.getY();

        top = new Arc(ball.getX(), ball.getY(), R, R, 45, 90);
        top.setType(ArcType.ROUND);

        left = new Arc(ball.getX(), ball.getY(), R, R, 135, 90);
        left.setType(ArcType.ROUND);

        bottom = new Arc(ball.getX(), ball.getY(), R, R, -135, 90);
        bottom.setType(ArcType.ROUND);

        right = new Arc(ball.getX(), ball.getY(), R, R, -45, 90);
        right.setType(ArcType.ROUND);

        this.ball = ball;
    }

    public void relocate(double x, double y){

        centerX = x;
        centerY = y;

        top.setCenterX(centerX);
        top.setCenterY(centerY);

        left.setCenterX(centerX);
        left.setCenterY(centerY);

        bottom.setCenterX(centerX);
        bottom.setCenterY(centerY);

        right.setCenterX(centerX);
        right.setCenterY(centerY);
    }

    public boolean intersectLeft(Shape shape){
        return !Shape.intersect(shape, left).getBoundsInParent().isEmpty();
    }

    public boolean intersectRight(Shape shape){
        return !Shape.intersect(shape, right).getBoundsInParent().isEmpty();
    }

    public boolean intersectTop(Shape shape){
        return !Shape.intersect(shape, top).getBoundsInParent().isEmpty();
    }

    public boolean intersectBottom(Shape shape){
        return !Shape.intersect(shape, bottom).getBoundsInParent().isEmpty();
    }
}

package com.company.AliMoghaddaszadeh;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * this is the Rocket which move left and right
 * with Left Arrow and Right Arrow.
 * if ball intersects with Rocket reflect in correct angle
 */

public class Rocket {

    /*
    WIDTH:    is rocket width
    HEIGHT:   is rocket height
     */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 10;

    private double defaultCenterX;
    private double defaultCenterY;
    private double centerX;
    private double centerY;

    private Rectangle rectangle;

    /**
     * constructor of Rocket
     * @param centerX is centerX of Rocket
     * @param centerY is centerY of Rocket
     */
    public Rocket(double centerX, double centerY) {
        rectangle = new Rectangle(centerX-((double)WIDTH/2), centerY-((double)HEIGHT/2), WIDTH, HEIGHT);
        this.centerX = centerX;
        this.centerY = centerY;
        defaultCenterX = centerX;
        defaultCenterY = centerY;
    }

    /**
     * relocate Rocket to default position
     */
    public void recenter() {
        centerX = defaultCenterX;
        centerY = defaultCenterY;
        rectangle.relocate(centerX-((double)WIDTH/2), centerY-((double)HEIGHT/2));
    }

    /**
     * give Rectangle Shape of Rocket to add root Group or ...
     * @return shape of Rocket that is Rectangle
     */
    public Rectangle getRectangle(){
        return rectangle;
    }

    /**
     * change color of Rocket
     * @param paint is the color which we want to change to
     */
    public void setFill(Paint paint){
        rectangle.setFill(paint);
    }

    /**
     * @return centerX of Rocket
     */
    public double getCenterX(){
        return centerX;
    }

    /**
     * @return centerY of Rocket
     */
    public double getCenterY(){
        return centerY;
    }

    /**
     * @return width of Rocket
     */
    public int getWidth() {
        return (int) rectangle.getWidth();
    }

    /**
     * duplicate the width of Rocket
     */
    public void doubleWidth() {
        rectangle.setWidth(WIDTH * 2);
        rectangle.relocate(centerX-(double)WIDTH, centerY-((double)HEIGHT/2));
    }

    /**
     * change width of Rocket to default
     * which Rocket has created with
     */
    public void defaultWidth() {
        rectangle.setWidth(WIDTH);
        rectangle.relocate(centerX-((double)WIDTH/2), centerY-((double)HEIGHT/2));
    }

    /**
     * Rocket move right by 1 pixel
     */
    public void moveRight(){
        centerX += 1;
        rectangle.relocate(centerX-((double)this.getWidth()/2), centerY-((double)HEIGHT/2));
    }

    /**
     * Rocket move left by 1 pixel
     */
    public void moveLeft(){
        centerX -= 1;
        rectangle.relocate(centerX-((double)this.getWidth()/2), centerY-((double)HEIGHT/2));
    }
}

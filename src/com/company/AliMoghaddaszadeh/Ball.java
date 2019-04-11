package com.company.AliMoghaddaszadeh;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * this is the Ball class that move in direction
 * of angle which is similar with triangle circle angle
 * and break Bricks.
 */

public class Ball {

    // radius of Ball and HitBox
    public static final int R = 8;
    public static final int HITBOX_R = 9;

    // speed that has use in choosing difficulties
    public static final double SLOW = 2;
    public static final double MEDIUM = 4;
    public static final double FAST = 6;
    private double speed;
    private boolean halfSpeed = false;

    private Circle hitBox;
    private Circle circle;

    // position of ball (centerX,centerY)  ---> <i can use Circle methods instead of these two>
    private double defaultCenterX;
    private double defaultCenterY;
    private double centerX;
    private double centerY;

    // the Angle that determine move direction of ball
    private double angle;

//    private long oldTime = -1;

    /**
     * constructor of Ball which is a Circle with 10 radius
     * @param x is centerX of Ball.
     * @param y is centerY of Ball.
     * @param speed determine speed of Ball between SLOW, MEDIUM and FAST constants
     */
    public Ball(double x, double y, double speed){

        circle = new Circle(x, y, R);
        hitBox = new Circle(x, y, HITBOX_R);

        this.speed = speed;
        angle = Math.random() * 15 + 85;
        centerX = x;
        centerY = y;
        defaultCenterX = x;
        defaultCenterY = y;

//        hitBox = new HitBox(this);
    }

    /**
     * change the speed of Ball
     * @param speed is the speed that we want to change to
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * reset the position of Ball to default
     * which Ball has created with
     */
    public void reset() {
        centerX = defaultCenterX;
        centerY = defaultCenterY;
        relocate(centerX, centerY);
        angle = Math.random() * 15 + 85;
    }

    /**
     * @return Circle Shape of Ball
     */
    public Circle getCircle(){
        return circle;
    }

    /**
     * change color of Ball
     * @param paint is the Color that we want to change to
     */
    public void setFill(Paint paint) {
        circle.setFill(paint);
    }

    /**
     * this method change Ball move angle direction with @param angle
     * @param angle is the angle that Ball move angle change to
     */
    public void setAngle(double angle){
        this.angle = angle;
    }

    /**
     * this method change speed to half with bonus of Bricks
     * @param halfSpeed is boolean dataType that tell if ball's speed
     *                  is half or not
     */
    public void setHalfSpeed(boolean halfSpeed) {
        this.halfSpeed = halfSpeed;
    }

    /**
     * this method move Ball and it's HitBox in angle direction
     */
    public void move(){
/*
        if (oldTime == -1)
            oldTime = timestamp;
        long diff = (timestamp - oldTime) / 10;*/

        if (halfSpeed) {
            centerX += Math.cos( Math.toRadians(angle) ) * (speed / 20);
            centerY += Math.sin( Math.toRadians(angle) ) * (speed / 20);

        } else {

            centerX += Math.cos( Math.toRadians(angle) ) * speed / 10;
            centerY += Math.sin( Math.toRadians(angle) ) * speed / 10;
        }

        circle.setCenterX(centerX);
        circle.setCenterY(centerY);

        hitBox.setCenterX(centerX);
        hitBox.setCenterY(centerY);

//        relocate(centerX - R, centerY - R);
//        oldTime = timestamp;
    }

    /**
     * change location of Ball and it's HitBox to x & y
     * @param x: 0 - Width of Scene
     * @param y: 0 - Height of Scene
     */
    public void relocate(double x, double y) {
        circle.setCenterX(x);
        circle.setCenterY(y);

        hitBox.setCenterX(x);
        hitBox.setCenterY(y);
    }

    /**
     * tell if Ball's HitBox has intersects with
     * @param rectangle
     * @return true or false value
     */
    public boolean collision(Rectangle rectangle) {
        return !Shape.intersect(hitBox, rectangle).getBoundsInParent().isEmpty();
    }

    /**
     * change Angle of Ball depended on it's previous Angle
     * when intersects with right or left of objects or screen
     */
    public void collisionHorizontal(){

        if (angle <= 180)
            angle = 180 - angle;
        else
            angle = 540 - angle;
    }

    /**
     * change Angle of Ball depended on it's previous Angle
     * when intersects with top or bottom of objects or screen
     */
    public void collisionVertical(){
        angle = 360 - angle;
    }

    /**
     * @return centerX of Ball
     */
    public double getX(){
        return centerX;
    }

    /**
     * @return centerY of Ball
     */
    public double getY(){
        return centerY;
    }
}

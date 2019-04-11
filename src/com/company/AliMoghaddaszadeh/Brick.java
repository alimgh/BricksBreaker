package com.company.AliMoghaddaszadeh;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * this is class of Bricks that breaks
 * and give score in game.
 * they randomly have Bonus that help
 * player to play better
 */

public class Brick {

    /*
    WIDTH:    is brick width
    HEIGHT:   is brick height
     */
    public static final int WIDTH = 40;
    public static final int HEIGHT = 40;

    private double centerX;
    private double centerY;
    private double x;
    private double y;

    private Rectangle rectangle;
    private Text text;
    private StackPane brick;

    private int life;

    private Bonus bonus;
    private boolean hasBonus;

    /**
     * constructor of Brick
     * create Brick in (x + width/2, y + height/2)
     * determinate life of Brick between 1 - 5
     * determinate if Brick has Bonus or not with probability of 1/8
     * @param x is position of top left corner of Brick
     * @param y is position of top left corner of Brick
     */
    public Brick(int x, int y){

        rectangle = new Rectangle();
        rectangle.setWidth(WIDTH);
        rectangle.setHeight(HEIGHT);

        this.x = x;
        this.y = y;
        centerX = x + (double) WIDTH / 2;
        centerY = y + (double) HEIGHT / 2;

        life = (int) (Math.random() * 5 + 1);
        text = new Text(String.valueOf(life));

        if (Math.random() * 8 < 1) {
            bonus = new Bonus((int) (Math.random() * 3) + 1 );
            rectangle.setFill(Color.YELLOW);
            hasBonus = true;

        } else {
            rectangle.setFill(Color.RED);
            hasBonus = false;
        }

        rectangle.setStroke(Color.DARKRED);

        brick = new StackPane();
        brick.setLayoutX(x);
        brick.setLayoutY(y);
        brick.getChildren().addAll(rectangle, text);
    }

    /**
     * tell if Brick has Bonus or not
     * @return true or false
     */
    public boolean hasBonus() {
        return hasBonus;
    }

    /**
     * @return Bonus object of Brick if it has
     *         and if it does'nt have return null
     */
    public Bonus getBonus() {
        if (hasBonus) {
            bonus.start();
            return bonus;
        }
        return null;
    }

    /**
     * @return Shape of Brick with Rectangle and life Text
     */
    public StackPane getBrick(){
        return brick;
    }

    /**
     * @return Rectangle Shape of Brick
     */
    public Rectangle getRectangle(){
        return rectangle;
    }

    /**
     * @return centerX of Brick
     */
    public double getCenterX(){
        return centerX;
    }

    /**
     * @return centerY of Brick
     */
    public double getCenterY(){
        return centerY;
    }

    /**
     * change life of Brick by hit
     */
    public void hit(){

        if (life > 0)
            life--;
        text.setText(String.valueOf(life));
    }

    /**
     * tell if life of Brick is 0 or not
     * @return true for 0 and false
     */
    public boolean isBroke(){
        return life == 0;
    }
}
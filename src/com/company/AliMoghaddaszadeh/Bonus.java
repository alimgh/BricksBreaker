package com.company.AliMoghaddaszadeh;

public class Bonus {

    private static final int DOUBLE_SCORE = 1;
    private static final int DOUBLE_ROCKET_WIDTH = 2;
    private static final int HALF_BALL_SPEED = 3;

    private int bonusType;

    private long bonusTotalTime;

    private boolean hasStarted;

    public Bonus (int bonusType) {

        this.bonusType = bonusType;

        switch (bonusType) {
            case DOUBLE_SCORE:
                bonusTotalTime = 20000;
                break;

            case DOUBLE_ROCKET_WIDTH:
                bonusTotalTime = 20000;
                break;

            case HALF_BALL_SPEED:
                bonusTotalTime = 15000;
                break;

            default:
                throw new IllegalArgumentException("Bonus Type is undefined");

        }

        hasStarted = false;
    }

    public void start() {
        hasStarted = true;
    }

    public boolean isDoubleScore() {

        if (hasStarted)
            return bonusType == DOUBLE_SCORE;

        return false;
    }

    public boolean isDoubleRocketWidth() {

        if (hasStarted)
            return bonusType == DOUBLE_ROCKET_WIDTH;

        return false;
    }

    public boolean isHalfBallSpeed() {

        if (hasStarted)
            return bonusType == HALF_BALL_SPEED;

        return false;
    }

    public long getBonusTotalTime() {
        return bonusTotalTime;
    }

}

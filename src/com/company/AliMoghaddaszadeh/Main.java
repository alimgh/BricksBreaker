package com.company.AliMoghaddaszadeh;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class Main extends Application {

    Stage primaryStage;

    private final double W = 455, H = 800;

    private boolean played = false;

    private final int HOME = 1;
    private final int GAME = 2;
    private final int RESULT = 3;

    private Scene homeScene;
    private Timeline homeTimer;

    private Scene gameScene;
    private Timeline gameTimer;

    private Scene resultScene;
    private Timeline resultTimer;

    private int showScene;

    private final int EASY = 1;
    private final int MEDIUM = 2;
    private final int HARD = 3;
    private int difficulty = EASY;

    private final double EASY_TIME = 300000;
    private final double MEDIUM_TIME = 210000;
    private final double HARD_TIME = 150000;

    private final int BRICKS_ROWS = 4;
    private final int BRICKS_COLUMNS = 10;
    private final int SPACE_BETWEEN_BRICKS = 5;

    private final int BRICK_HIT_SCORE = 10;
    private final int BRICK_BREAK_SCORE = 90;


    private Group home = new Group();

    private Rectangle easyRec;
    private Text easyText;
    private StackPane easy;

    private Rectangle mediumRec;
    private Text mediumText;
    private StackPane medium;

    private Rectangle hardRec;
    private Text hardText;
    private StackPane hard;

    private boolean buttonUp = false, buttonDown = false, playButton = false;


    private Group game = new Group();
    private Rocket rocket;
    private Ball ball;
    private Brick [][] bricks = new Brick[4][10];
    private ArrayList<Bonus> bonuses;

    private Rectangle time;
    private double totalTime = MEDIUM_TIME;
    private double remainTime;

    private Text showScore;
    private int score;
    private boolean doubleScore = false;

    private boolean hasDoubleRocketWidthBonus, hasDoubleScore, hasHalfBallSpeed;
    private long doubleRocketWidthTime, doubleScoreTime, halfBallSpeedTime;

    private boolean moveRight = false, moveLeft = false;


    private Group result = new Group();
    private Text resultScore;
    private Text resultTime;
    private Rectangle playAgainRec;
    private Text playAgainText;
    private StackPane playAgain;

    private boolean playAgainButton = false;

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Bricks Breaker");

        homeScene = new Scene(home, W, H);
        gameScene = new Scene(game, W, H);
        resultScene = new Scene(result, W, H);

        showScene = HOME;

        createHome();
        createGame();
        createResult();

//        primaryStage.setScene(gameScene);
        this.primaryStage.show();

        updateStage();

    }

    private void updateGame() {
        double ballSpeed = Ball.MEDIUM;
        totalTime = MEDIUM_TIME;

        switch (difficulty) {
            case EASY:
                ballSpeed = Ball.SLOW;
                totalTime = EASY_TIME;
                break;

            case MEDIUM:
                ballSpeed = Ball.MEDIUM;
                totalTime = MEDIUM_TIME;
                break;

            case HARD:
                ballSpeed = Ball.FAST;
                totalTime = HARD_TIME;

        }

        rocket.recenter();
        ball.reset();
        ball.setSpeed(ballSpeed);

        for (int i = 0; i < BRICKS_ROWS; i++) {
            for (int j = 0; j < BRICKS_COLUMNS; j++) {

                if (bricks[i][j] != null)
                    game.getChildren().remove(bricks[i][j].getBrick());

                bricks[i][j] = new Brick(SPACE_BETWEEN_BRICKS + j * (Brick.WIDTH + SPACE_BETWEEN_BRICKS),
                        i * (Brick.HEIGHT + SPACE_BETWEEN_BRICKS) + SPACE_BETWEEN_BRICKS);
                game.getChildren().add(bricks[i][j].getBrick());
            }
        }

        score = 0;
        showScore.setText(String.valueOf(score));

        time.setWidth(W);
    }

    private void updateStage() {
        switch (showScene) {
            case HOME:
                resetButtons();
                primaryStage.setScene(homeScene);
                createHomeKeyHandler(homeScene);
                homeAnimation();
                break;

            case GAME:
                resetButtons();
                primaryStage.setScene(gameScene);
                createGameKeyHandler(gameScene);
                gameAnimation();
                break;

            case RESULT:
                resetButtons();
                resultScore.setText(String.valueOf(score));
                resultTime.setText((int) (totalTime - remainTime) / 1000 + " seconds");
                primaryStage.setScene(resultScene);
                createResultKeyHandler(resultScene);
                resultAnimation();
                break;
        }
    }

    private void resetButtons() {
        playButton = false;
        buttonUp = false;
        buttonDown = false;
        moveLeft = false;
        moveRight = false;
        playAgainButton = false;
    }

    private void createHome() {
        easyRec = new Rectangle(150, 100, 155, 100);
        easyRec.setFill(Color.LIGHTSKYBLUE);
        easyText = new Text("Easy");
        easyText.setFont(new Font(30));
        easy = new StackPane();
        easy.setLayoutX(150);
        easy.setLayoutY(100);
        easy.getChildren().addAll(easyRec, easyText);

        mediumRec = new Rectangle(150, 350, 155, 100);
        mediumRec.setFill(Color.LIGHTSKYBLUE);
        mediumText = new Text("Medium");
        mediumText.setFont(new Font(30));
        medium = new StackPane();
        medium.setLayoutX(150);
        medium.setLayoutY(350);
        medium.getChildren().addAll(mediumRec, mediumText);

        hardRec = new Rectangle(150, 600, 155, 100);
        hardRec.setFill(Color.LIGHTSKYBLUE);
        hardText = new Text("Hard");
        hardText.setFont(new Font(30));
        hard = new StackPane();
        hard.setLayoutX(150);
        hard.setLayoutY(600);
        hard.getChildren().addAll(hardRec, hardText);

        home.getChildren().addAll(easy, medium, hard);
    }

    private void createGame() {

        rocket = new Rocket(227.5, 700);
        rocket.setFill(Color.GREEN);

        ball = new Ball(227.5, 400, Ball.MEDIUM);
        ball.setFill(Color.BLUE);

        game.getChildren().add(rocket.getRectangle());
        game.getChildren().add(ball.getCircle());

        for (int i = 0; i < BRICKS_ROWS; i++) {
            for (int j = 0; j < BRICKS_COLUMNS; j++) {

                bricks[i][j] = new Brick(SPACE_BETWEEN_BRICKS + j * (Brick.WIDTH + SPACE_BETWEEN_BRICKS),
                        i * (Brick.HEIGHT + SPACE_BETWEEN_BRICKS) + SPACE_BETWEEN_BRICKS);
                game.getChildren().add(bricks[i][j].getBrick());
            }
        }

        showScore = new Text(String.valueOf(score));
        showScore.setLayoutX(3);
        showScore.setLayoutY(H-8);
        showScore.maxHeight(5);
        showScore.setStrokeWidth(2);

        time = new Rectangle(0, H-3, W, 3);
        time.setFill(Color.BROWN);

        game.getChildren().addAll(time, showScore);
    }

    private void createResult() {

        resultScore = new Text();
        resultScore.setLayoutX(190);
        resultScore.setLayoutY(200);
        resultScore.setFont(new Font(50));
        resultScore.setText(String.valueOf(score));

        resultTime = new Text();
        resultTime.setLayoutX(150);
        resultTime.setLayoutY(400);
        resultTime.setFont(new Font(35));
        resultTime.setText((int) (totalTime - remainTime) / 1000 + " seconds");

        playAgainRec = new Rectangle(125, 500, 205, 100);
        playAgainRec.setFill(Color.DARKBLUE);
        playAgainText = new Text("Play Again");
        playAgainText.setFont(new Font(30));
        playAgainText.setFill(Color.WHITE);
        playAgain = new StackPane();
        playAgain.setLayoutX(130);
        playAgain.setLayoutY(500);
        playAgain.getChildren().addAll(playAgainRec, playAgainText);

        result.getChildren().addAll(resultScore, resultTime, playAgain);
    }

    private void createHomeKeyHandler(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                if ( keyEvent.getCode() == KeyCode.UP )
                    buttonUp = true;

                if ( keyEvent.getCode() == KeyCode.DOWN )
                    buttonDown = true;

                if ( keyEvent.getCode() == KeyCode.ENTER )
                    playButton = true;
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                if ( keyEvent.getCode() == KeyCode.UP )
                    buttonUp = false;

                if ( keyEvent.getCode() == KeyCode.DOWN )
                    buttonDown = false;

                if ( keyEvent.getCode() == KeyCode.ENTER )
                    playButton = false;
            }
        });
    }

    private void createGameKeyHandler(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                if ( keyEvent.getCode() == KeyCode.RIGHT )
                    moveRight = true;

                if ( keyEvent.getCode() == KeyCode.LEFT )
                    moveLeft = true;
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                if ( keyEvent.getCode() == KeyCode.RIGHT )
                    moveRight = false;

                if ( keyEvent.getCode() == KeyCode.LEFT )
                    moveLeft = false;
            }
        });
    }

    private void createResultKeyHandler(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                if ( keyEvent.getCode() == KeyCode.ENTER )
                    playAgainButton = true;

            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                if ( keyEvent.getCode() == KeyCode.ENTER )
                    playAgainButton = false;
            }
        });
    }

    private void homeAnimation() {

        if (played)
            resultTimer.stop();

        homeTimer = new Timeline(new KeyFrame(Duration.millis(80), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {

                if (buttonDown && difficulty < 3)
                    difficulty++;

                if (buttonUp && difficulty > 1)
                    difficulty--;

                switch (difficulty) {
                    case EASY:
                        easyRec.setFill(Color.DARKBLUE);
                        easyText.setFill(Color.WHITE);
                        mediumRec.setFill(Color.LIGHTSKYBLUE);
                        mediumText.setFill(Color.BLACK);
                        hardRec.setFill(Color.LIGHTSKYBLUE);
                        hardText.setFill(Color.BLACK);
                        break;

                    case MEDIUM:
                        easyRec.setFill(Color.LIGHTSKYBLUE);
                        easyText.setFill(Color.BLACK);
                        mediumRec.setFill(Color.DARKBLUE);
                        mediumText.setFill(Color.WHITE);
                        hardRec.setFill(Color.LIGHTSKYBLUE);
                        hardText.setFill(Color.BLACK);
                        break;

                    case HARD:
                        easyRec.setFill(Color.LIGHTSKYBLUE);
                        easyText.setFill(Color.BLACK);
                        mediumRec.setFill(Color.LIGHTSKYBLUE);
                        mediumText.setFill(Color.BLACK);
                        hardRec.setFill(Color.DARKBLUE);
                        hardText.setFill(Color.WHITE);
                        break;

                    default:
                        difficulty = EASY;
                        easyRec.setFill(Color.DARKBLUE);
                        mediumRec.setFill(Color.LIGHTSKYBLUE);
                        hardRec.setFill(Color.LIGHTSKYBLUE);
                }

                if (playButton) {
                    showScene = GAME;
                    updateGame();
                    updateStage();
                }
            }
        }));
        homeTimer.setCycleCount(Timeline.INDEFINITE);
        homeTimer.play();
    }

    private void gameAnimation() {

        homeTimer.stop();

        moveLeft = false;
        moveRight = false;

        remainTime = totalTime;
        score = 0;

        bonuses = new ArrayList<>();
        hasDoubleRocketWidthBonus = false;
        hasDoubleScore = false;
        hasHalfBallSpeed = false;

        gameTimer = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {

                checkBonus();

                ball.move();

                if ( ball.getX() + Ball.R >= W ||
                        ball.getX() - Ball.R <= 0)
                    ball.collisionHorizontal();

                if (ball.getY() - Ball.R <= 0)
                    ball.collisionVertical();

                if ( ball.getY() + Ball.R > rocket.getCenterY() - Rocket.HEIGHT/2 &&
                        ball.getY() - Ball.R < rocket.getCenterY() + Rocket.HEIGHT/2 )
                    collisionWithRocket();


                checkNearbyBricksCollision(game);

                if (moveRight &&
                        rocket.getCenterX() + rocket.getWidth()/2 < W)
                    rocket.moveRight();

                if (moveLeft &&
                        rocket.getCenterX() - rocket.getWidth()/2 > 0)
                    rocket.moveLeft();

                updateTime();

                if ( ball.getY() + Ball.R >= H-3 ||
                        remainTime <= 0 ) {
                    showScene = RESULT;
                    updateStage();
                }
            }
        }));
        gameTimer.setCycleCount(Timeline.INDEFINITE);
        gameTimer.play();
    }

    private void resultAnimation() {

        gameTimer.stop();

        resultTimer = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {

                if (playAgainButton) {
                    showScene = HOME;
                    updateStage();
                }
            }
        }));
        resultTimer.setCycleCount(Timeline.INDEFINITE);
        resultTimer.play();
    }

    private void checkBonus() {
        for (int i = 0; i < bonuses.size(); i++) {

            Bonus bonus = bonuses.get(i);

            if (bonus.isDoubleRocketWidth()) {
                if (!hasDoubleRocketWidthBonus) {

                    rocket.doubleWidth();
                    doubleRocketWidthTime = bonus.getBonusTotalTime();
                    hasDoubleRocketWidthBonus = true;
                    bonuses.remove(i);

                } else {
                    doubleRocketWidthTime += bonus.getBonusTotalTime();
                    bonuses.remove(i);
                }

            } else if (bonus.isDoubleScore()) {
                if (!hasDoubleScore) {

                    doubleScore = true;
                    doubleScoreTime = bonus.getBonusTotalTime();
                    hasDoubleScore = true;
                    bonuses.remove(i);

                } else {
                    doubleScoreTime += bonus.getBonusTotalTime();
                    bonuses.remove(i);
                }

            } else if (bonus.isHalfBallSpeed()) {
                if (!hasHalfBallSpeed) {

                    ball.setHalfSpeed(true);
                    halfBallSpeedTime = bonus.getBonusTotalTime();
                    hasHalfBallSpeed = true;
                    bonuses.remove(i);

                } else {
                    halfBallSpeedTime += bonus.getBonusTotalTime();
                    bonuses.remove(i);
                }
            }
        }
    }

    private void updateTime() {

        if (doubleScoreTime > 0) {
            doubleScoreTime--;
        } else {
            doubleScore = false;
            hasDoubleScore = false;
        }

        if (doubleRocketWidthTime > 0) {
            doubleRocketWidthTime--;
        } else {
            rocket.defaultWidth();
            hasDoubleRocketWidthBonus = false;
        }

        if (halfBallSpeedTime > 0) {
            halfBallSpeedTime--;
        } else {
            ball.setHalfSpeed(false);
            hasHalfBallSpeed = false;
        }

        if (remainTime > 0) {
            remainTime--;
            time.setWidth(W * remainTime / totalTime);
        }
    }

    private void checkNearbyBricksCollision(Group game) {
        if (ball.getY() <= (Brick.HEIGHT + SPACE_BETWEEN_BRICKS) * BRICKS_ROWS + SPACE_BETWEEN_BRICKS + Ball.HITBOX_R ){

            int i = (int) ball.getY() / (Brick.HEIGHT + 5);
            int j = (int) ball.getX() / (Brick.WIDTH + 5);
            boolean collision = false;

            if (i > 0) {

                if (j > 0)
                    collision = collisionWithBrick(i - 1, j - 1);

                if (!collision)
                    collision = collisionWithBrick(i - 1, j);

                else if (bricks[i - 1][j] != null &&
                            ball.collision(bricks[i - 1][j].getRectangle())){
                    collision = false;

                    bricks[i - 1][j].hit();
                    addScore(BRICK_HIT_SCORE);
                    if (bricks[i - 1][j].isBroke()) {

                        if (bricks[i - 1][j].hasBonus())
                            bonuses.add(bricks[i - 1][j].getBonus());

                        game.getChildren().remove(bricks[i - 1][j].getBrick());
                        bricks[i - 1][j] = null;
                        addScore(BRICK_BREAK_SCORE);
                    }
                }

                if (j < BRICKS_COLUMNS - 1) {

                    if (!collision)
                        collisionWithBrick(i - 1, j + 1);

                    else if (bricks[i - 1][j + 1] != null &&
                                ball.collision(bricks[i - 1][j + 1].getRectangle())) {

                        bricks[i - 1][j + 1].hit();
                        addScore(BRICK_HIT_SCORE);
                        if (bricks[i - 1][j + 1].isBroke()) {

                            if (bricks[i - 1][j].hasBonus())
                                bonuses.add(bricks[i - 1][j].getBonus());

                            game.getChildren().remove(bricks[i - 1][j + 1].getBrick());
                            bricks[i - 1][j + 1] = null;
                            addScore(BRICK_BREAK_SCORE);
                        }
                    }
                }
            }

            if (i < BRICKS_ROWS) {
                if (j > 0)
                    collisionWithBrick(i, j - 1);

                if (j < BRICKS_COLUMNS - 1)
                    collisionWithBrick(i, j + 1);

                collision = false;
                if (i < BRICKS_ROWS - 1) {

                    if (j > 0)
                        collision = collisionWithBrick(i + 1, j - 1);

                    if (!collision)
                        collision = collisionWithBrick(i + 1, j);

                    else if (bricks[i + 1][j] != null &&
                                ball.collision(bricks[i + 1][j].getRectangle())){
                        collision = false;

                        bricks[i + 1][j].hit();
                        addScore(BRICK_HIT_SCORE);
                        if (bricks[i + 1][j].isBroke()) {

                            if (bricks[i - 1][j].hasBonus())
                                bonuses.add(bricks[i - 1][j].getBonus());

                            game.getChildren().remove(bricks[i + 1][j].getBrick());
                            bricks[i + 1][j] = null;
                            addScore(BRICK_BREAK_SCORE);
                        }
                    }

                    if (j < BRICKS_COLUMNS - 1) {

                        if (!collision)
                            collisionWithBrick(i + 1, j + 1);

                        else if (bricks[i + 1][j + 1] != null &&
                                    ball.collision(bricks[i + 1][j + 1].getRectangle())) {

                            bricks[i + 1][j + 1].hit();
                            addScore(BRICK_HIT_SCORE);
                            if (bricks[i + 1][j + 1].isBroke()) {

                                if (bricks[i - 1][j].hasBonus())
                                    bonuses.add(bricks[i - 1][j].getBonus());

                                game.getChildren().remove(bricks[i + 1][j + 1].getBrick());
                                bricks[i + 1][j + 1] = null;
                                addScore(BRICK_BREAK_SCORE);
                            }
                        }
                    }
                }
            }
        }
    }

    private void addScore(int add) {

        if (doubleScore)
            add *= 2;

        score += add;
        showScore.setText(String.valueOf(score));
    }

    private void collisionWithRocket() {
        if (ball.collision(rocket.getRectangle())) {

            if (ball.getX()- Ball.R == rocket.getCenterX()+ rocket.getWidth()/2)
                ball.setAngle(330);

            else if (ball.getX()+Ball.R == rocket.getCenterX()-rocket.getWidth()/2)
                ball.setAngle(210);

            else {

                double dx = ball.getX() - rocket.getCenterX();
                double angle = 270 + ( dx * 60 ) / ((double)rocket.getWidth() / 2);

                if (angle >= 330)
                    ball.setAngle(330);

                else if (angle <= 210)
                    ball.setAngle(210);

                else
                    ball.setAngle(angle);

            }

        }
    }

    private boolean collisionWithBrick(int i, int j) {

        if (bricks[i][j] != null) {
            if (ball.collision(bricks[i][j].getRectangle())) {

                if (Math.abs(bricks[i][j].getCenterX() - ball.getX()) >
                        Math.abs(bricks[i][j].getCenterY() - ball.getY()))
                    ball.collisionHorizontal();

                else
                    ball.collisionVertical();

                bricks[i][j].hit();
                addScore(BRICK_HIT_SCORE);
                if (bricks[i][j].isBroke()) {

                    if (bricks[i][j].hasBonus())
                        bonuses.add(bricks[i][j].getBonus());

                    game.getChildren().remove(bricks[i][j].getBrick());
                    bricks[i][j] = null;
                    addScore(BRICK_BREAK_SCORE);
                }

                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

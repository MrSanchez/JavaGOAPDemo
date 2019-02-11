package main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Engine extends Application {

    /* For simplicity, this project will deliberately ignore a few concepts:
    ** MVC pattern will not be used
    ** Absolute XYZ coordinates will be used instead of a tile-based engine.
    */

    public final static int windowWidth = 1380;
    public final static int windowHeight = 800;

    public static Game game;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        root.getStylesheets().add(getClass().getResource("sample.css").toExternalForm());

        Scene scene = new Scene(root, windowWidth - 10, windowHeight - 10);

        primaryStage.setTitle("GOAP POC");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        final Canvas canvas = new Canvas(windowWidth, windowHeight);
        GraphicsContext g = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);

        game = new Game(g);
        game.initialize();

        Time.initialize();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Time.update(now);

                game.update();
                game.draw();
            }
        };
        timer.start();


        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

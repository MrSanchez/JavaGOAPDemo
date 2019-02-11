package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import main.util.DrawUtils;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class GameObject {
    protected String name;

    protected double x, y;
    protected double vx = 0, vy = 0;
    protected double speed;
    protected double angle;

    protected double textureSize;
    protected ImageView textureView;
    protected Image image;


    protected Pane parentPane;

    protected Circle debugDot;


    public GameObject(String name, double x, double y, double angle, double textureSize, Image texture) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.textureSize = textureSize;

        this.image = texture;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }


    public void setSpeed(double speed)
    {
        this.speed = speed;
    }

    public void update() {
        double timeSinceLastFrame = Time.deltaTime();
        this.x += this.vx * this.speed * timeSinceLastFrame;
        this.y += this.vy * this.speed * timeSinceLastFrame;
        //System.out.printf("posX: %.2f, speed: %.2f, elapsedTime: %.2f\n", this.x, this.speed, timeSinceLastFrame);
    }

    public void draw(GraphicsContext g)
    {
        g.save();

        double textureCenterX = this.x - this.textureSize / 2;
        double textureCenterY = this.y - this.textureSize / 2;

        DrawUtils.rotate(g, this.angle, this.x, this.y);
        g.drawImage(this.image, textureCenterX, textureCenterY, this.textureSize, this.textureSize);

        g.restore();
    }
}

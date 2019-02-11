package main.GOAP;

import javafx.scene.canvas.GraphicsContext;

public interface IGoapEntity
{
    public void moveEntity(GoapAction currentAction);

    public void draw(GraphicsContext g);
}

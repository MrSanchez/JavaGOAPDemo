package main;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import main.GOAP.FSM.FSM;
import main.GOAP.FSM.FSMState;
import main.GOAP.GoapAction;
import main.GOAP.GoapAgent;
import main.GOAP.IGoapEntity;

public class Man extends GameObject implements IGoapEntity
{

    public Man(String name, double x, double y, double angle, double textureSize, Image texture) {
        super(name, x, y, angle, textureSize, texture);
    }

    public void moveEntity(GoapAction currentAction)
    {
        if(currentAction.target != null)
        {
            double targetX = currentAction.target.getX();
            double targetY = currentAction.target.getY();

            double vectorX = targetX - this.x;
            double vectorY = targetY - this.y;

            double distance = Math.sqrt(Math.pow(vectorX, 2) + Math.pow(vectorY, 2));

            this.vx = vectorX / distance;
            this.vy = vectorY / distance;
            this.angle = Math.atan2(vx, -vy) * 180 / Math.PI;

            super.update();

            int uglyRadius = 2;

            if( this.x > targetX - uglyRadius &&
                this.x < targetX + uglyRadius &&
                this.y > targetY - uglyRadius &&
                this.y < targetY + uglyRadius)
            {
                currentAction.setInRange(true);
            }

            //System.out.println("Moving entity!");
        }

    }
}

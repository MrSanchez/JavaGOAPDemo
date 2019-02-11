package main.AI;

import main.Engine;
import main.GOAP.GoapAction;

public class GetInBoatAction extends GoapAction
{
    public boolean inBoat = false;

    public GetInBoatAction()
    {
        this.target = Engine.game.boat;

        addPrecondition("openedBox", true);
        addPrecondition("hasSword", true);
        addEffect("inBoat", true);
    }

    @Override
    public boolean checkProceduralPrecondition()
    {
        return true;
    }

    @Override
    public void performAction()
    {
        System.out.println("You got into the boat. Good job AI! Proud of you.");
        inBoat = true;
    }

    @Override
    public boolean isDone()
    {
        return inBoat;
    }
}

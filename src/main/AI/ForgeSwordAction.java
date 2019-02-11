package main.AI;

import main.GOAP.GoapAction;
import main.Engine;

public class ForgeSwordAction extends GoapAction
{
    private boolean forgedSword = false;


    private long forgeTime = 5000;
    private long startedForgingTime = 0;

    public ForgeSwordAction()
    {
        this.target = Engine.game.anvil;

        addEffect("hasSword", true);

        cost = 500;
    }

    @Override
    public boolean checkProceduralPrecondition()
    {
        return true;
    }

    @Override
    public void performAction()
    {
        if(startedForgingTime == 0)
            startedForgingTime = System.currentTimeMillis();

        if(System.currentTimeMillis() - startedForgingTime > forgeTime)
        {
            forgedSword = true;
        }
    }

    @Override
    public boolean isDone()
    {
        return forgedSword;
    }
}

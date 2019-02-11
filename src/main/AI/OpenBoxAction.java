package main.AI;

import main.Engine;
import main.GOAP.GoapAction;

public class OpenBoxAction extends GoapAction
{
    public boolean boxOpened = false;

    public OpenBoxAction()
    {
        this.target = Engine.game.food;

        addPrecondition("hasSword", true);
        addEffect("openedBox", true);
    }

    @Override
    public boolean checkProceduralPrecondition()
    {
        return true;
    }

    @Override
    public void performAction()
    {
        Engine.game.destroyGameObject(this.target);
        boxOpened = true;
    }

    @Override
    public boolean isDone()
    {
        return boxOpened;
    }
}

package main.AI;

import main.GOAP.GoapAction;
import main.Engine;

public class PickupSwordAction extends GoapAction
{
    public boolean pickedUpSword = false;

    public PickupSwordAction()
    {
        this.target = Engine.game.sword;

        addEffect("hasSword", true);

        cost = 5000;
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
        pickedUpSword = true;
    }

    @Override
    public boolean isDone()
    {
        return pickedUpSword;
    }
}

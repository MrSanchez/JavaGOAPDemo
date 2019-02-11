package main.GOAP;

import main.GameObject;
import main.Man;

import java.util.HashMap;

public abstract class GoapAction {

    private GoapWorldPropertyList effects;
    private GoapWorldPropertyList preconditions;

    private boolean inRange = false;

    public GameObject target; // Target to perform the action on

    public int cost = 0;

    public GoapAction()
    {
        this.effects = new GoapWorldPropertyList(new HashMap<>());
        this.preconditions = new GoapWorldPropertyList(new HashMap<>());
    }

    // Precondition that must return true for it to be included in the available actions
    public abstract boolean checkProceduralPrecondition();

    // Performs the action when in range
    public abstract void performAction();

    // Returns true when the action is done.
    public abstract boolean isDone();


    public boolean isInRange()
    {
        return inRange;
    }

    public void setInRange(boolean inRange)
    {
        this.inRange = inRange;
    }

    public GoapWorldPropertyList getEffects()
    {
        return this.effects;
    }

    public GoapWorldPropertyList getPreconditions()
    {
        return this.preconditions;
    }

    public void addPrecondition(String name, boolean value)
    {
        this.preconditions.add(name, value);
    }

    public void addEffect(String name, boolean value)
    {
        this.effects.add(name, value);
    }
}

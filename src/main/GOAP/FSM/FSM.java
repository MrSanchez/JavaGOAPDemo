package main.GOAP.FSM;

import main.GameObject;
import java.util.Stack;

public class FSM
{
    private Stack<FSMState> states;

    // TODO: add GameObject attribute?

    public FSM()
    {
        this.states = new Stack<>();
    }

    public void update()
    {
        states.peek().execute();
    }

    public void popState()
    {
        states.pop();
    }

    public void pushState(FSMState state)
    {
        states.push(state);
    }
}

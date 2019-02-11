package main.GOAP;

import javafx.scene.canvas.GraphicsContext;
import main.AI.ForgeSwordAction;
import main.AI.GetInBoatAction;
import main.AI.OpenBoxAction;
import main.AI.PickupSwordAction;
import main.GOAP.FSM.FSM;
import main.GOAP.FSM.FSMState;

import java.util.*;

public class GoapAgent
{

    private IGoapEntity entity;

    private FSM fsm;

    private FSMState idleState;
    private FSMState moveState;
    private FSMState performState;

    private GoapAction currentAction;

    private Queue<GoapAction> plannedActionsQueue;

    private GoapPlanner planner;

    public GoapAgent(IGoapEntity entity)
    {
        this.entity = entity;

        this.fsm = new FSM();
        this.plannedActionsQueue = new LinkedList<>();

        this.createIdleState();
        this.createMoveState();
        this.createPerformState();

        fsm.pushState(idleState);

        System.out.println("Creating planner");
        this.planner = new GoapPlanner();

        // Creating starting conditions here for now
        HashMap<String, Boolean> startMap = new HashMap<>();
        startMap.put("inBoat", false);
        startMap.put("openedBox", false);
        startMap.put("hasSword", false);


        HashMap<String, Boolean> goalMap = new HashMap<>();
        goalMap.put("inBoat", true);

        GoapWorldState startState = new GoapWorldState(new GoapWorldPropertyList(startMap));
        GoapWorldState goalState = new GoapWorldState(new GoapWorldPropertyList(goalMap));

        List<GoapAction> allActions = new ArrayList<>();
        Collections.addAll(allActions, new PickupSwordAction(), new ForgeSwordAction(), new GetInBoatAction(), new OpenBoxAction());

        List<GoapAction> actions = planner.A_Star(goalState, startState, allActions);
        this.plannedActionsQueue.addAll(actions);

        System.out.println("Done planning");


        System.out.println(actions);
        for(GoapAction a : actions)
        {
            System.out.println("Next action: " + a);
        }
    }


    private void createPerformState()
    {
        performState = () -> {
            if(!currentAction.isDone()) {
                currentAction.performAction();
            } else {
                this.fsm.popState(); // move
                this.fsm.pushState(idleState);
            }
        };
    }

    private void createIdleState()
    {
        idleState = () -> {
            if(!plannedActionsQueue.isEmpty())
            {
                currentAction = plannedActionsQueue.remove(); // remove top action from queue
                this.fsm.popState(); // idle state
                this.fsm.pushState(moveState); // move state
            } else {
                // Do fuck all
            }
        };
    }

    private void createMoveState()
    {
        moveState = () -> {
            if(currentAction.isInRange())
            {
                this.fsm.popState(); // move
                this.fsm.pushState(performState);
            } else {
                entity.moveEntity(this.currentAction);
            }
        };
    }

    public void update() {
        fsm.update();
    }

    public void draw(GraphicsContext g)
    {
        entity.draw(g);
    }
}

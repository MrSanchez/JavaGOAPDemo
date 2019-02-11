package main.GOAP;

import java.util.*;

public class GoapPlanner
{

    public ArrayList<Node> getNeighbouringNodes(Node currentNode, List<GoapAction> allActions)
    {
        GoapWorldState currentState = currentNode.worldState;

        ArrayList<Node> neighbours = new ArrayList<>();
        for(GoapAction action : allActions)
        {
            GoapWorldPropertyList effects = action.getEffects();
            if(effects.satisfies(currentState.getPropertyList()))
            {
                // This action will lead to some satisfaction of the world state.
                GoapWorldState newWorldState = currentState.copyAddAction(action);

                Node node = new Node(newWorldState, action);
                neighbours.add(node);
            }
        }

        return neighbours;
    }

    private ArrayList<GoapAction> reconstructPath(Map<Node,Node> bestPrevious, Node currentNode) {
        System.out.println("Reconstructing path");
        System.out.println("Reconstructing path, starting with: " + currentNode);
        System.out.println("Reconstructing path, keyset: " + bestPrevious.keySet());
        System.out.println("Reconstructing path, values: " + bestPrevious.values());
        ArrayList<GoapAction> bestPath = new ArrayList<>();
        bestPath.add(currentNode.cameFromAction);
        while (bestPrevious.containsKey(currentNode))
        {
            currentNode = bestPrevious.get(currentNode);
            if(currentNode.cameFromAction != null) {
                bestPath.add(currentNode.cameFromAction);
            }
        }
        return bestPath;
    }

    public ArrayList<GoapAction> A_Star(GoapWorldState startState, GoapWorldState goalState, List<GoapAction> allActions)
    {
        System.out.println("Start planning");

        System.out.println("Start state properties: " + startState.getPropertyList());
        System.out.println("Goal state properties: " + goalState.getPropertyList());

        Node startNode = new Node(startState, null);

        // TODO: Why not put this in the neighbour code, after the continue;?
        List<GoapAction> availableActions = new ArrayList<GoapAction>();
        for(GoapAction a : allActions)
        {
            if(a.checkProceduralPrecondition())
            {
                availableActions.add(a);
            }
        }

        System.out.println("Available actions: " + availableActions);

        // Actions that have been checked and are not part of the best path(?)
        Set<Node> closedSet = new HashSet<>();

        // Nodes that have been found but not been checked yet
        // Starting with the first starting cell
        Set<Node> openSet = new HashSet<>();
        openSet.add(startNode);

        Map<Node, Node> bestPrevious = new HashMap<>();

        // Total cost from startCell to Cell.
        Map<Node, Integer> gScore = new HashMap<>();

        // From start to start = 0
        gScore.put(startNode, 0);

        // Cost from startCell to Cell + heuristic from Cell to goalCell (f(cell) = g(cell) + h(cell))
        Map<Node, Integer> fScore = new HashMap<>();

        // gScore for startCell is 0, so fScore is 0 + heuristic
        fScore.put(startNode, heuristicCost(startState, goalState));

        System.out.println("Starting fScore: " + fScore.get(startNode));

        System.out.println("\n---------");

        while(!openSet.isEmpty())
        {
            System.out.println("");
            Node currentNode = getLowestFscore(openSet, fScore);
            //System.out.println("currentNode gScore: " + gScore.get(currentNode));
            System.out.println("Open nodes list: " + openSet);

            System.out.printf("Current node: %s properties: %s\n", currentNode, currentNode.worldState.getPropertyList());
            if(currentNode.worldState.getPropertyList().containsAll(goalState.getPropertyList())) return reconstructPath(bestPrevious, currentNode);

            openSet.remove(currentNode);
            closedSet.add(currentNode);

            ArrayList<Node> neighbours = getNeighbouringNodes(currentNode, availableActions);
            System.out.println("Amount of neighbours: " + neighbours.size());

            for (Node neighbour : neighbours)
            {
                if(closedSet.contains(neighbour))
                    continue; // Neighbour has already been checked. Don't bother checking again.

                int tempGScore = gScore.get(currentNode) + costBetween(currentNode, neighbour);

                if(!openSet.contains(neighbour)) { // Neighbour hasn't been discovered before
                    openSet.add(neighbour);
                } else if(tempGScore >= gScore.get(neighbour)) { // Neighbour has been discovered before
                    continue; // And the current gScore hasn't improved.
                }

                // In all other cases, it's the best path

                int heuristicCost = heuristicCost(neighbour.worldState, goalState);
                System.out.printf("neighbour: %s | g(n): %d, h(n): %d, f(n): %d\n", neighbour,tempGScore, heuristicCost, tempGScore + heuristicCost);

                bestPrevious.put(neighbour, currentNode);
                gScore.put(neighbour, tempGScore);
                fScore.put(neighbour, tempGScore + heuristicCost);
            }
        }
        return new ArrayList<>();
    }


    private int costBetween(Node currentNode, Node neighbour) {
        return neighbour.cameFromAction.cost;
    }

    private int heuristicCost(GoapWorldState currentState, GoapWorldState goalState) {
        return goalState.getPropertyList().getChangedValuesAmount(currentState.getPropertyList());
    }

    private Node getLowestFscore(Set<Node> openSet, Map<Node, Integer> fScore) {
        // Probably better to use a priority queue [Optimization tip!]
        double lowestScore = Double.MAX_VALUE;
        Node lowestScoringNode = openSet.iterator().next();

        Iterator<Node> i = openSet.iterator();
        while (i.hasNext()) {
            Node cell = i.next();
            double score = fScore.get(cell);
            if(score < lowestScore)
            {
                lowestScore = score;
                lowestScoringNode = cell;
            }
        }

        return lowestScoringNode;
    }

    private Node getHighestFScore(Set<Node> openSet, Map<Node, Integer> fScore) {
        // Probably better to use a priority queue [Optimization tip!]
        double highestScore = -Double.MAX_VALUE;
        Node highestScoringNode = openSet.iterator().next();

        Iterator<Node> i = openSet.iterator();
        while (i.hasNext()) {
            Node cell = i.next();
            double score = fScore.get(cell);
            if(score > highestScore)
            {
                highestScore = score;
                highestScoringNode = cell;
            }
        }

        return highestScoringNode;
    }

    class Node {
        public GoapWorldState worldState;
        public GoapAction cameFromAction;

        public Node(GoapWorldState worldState, GoapAction cameFromAction)
        {
            this.worldState = worldState;
            this.cameFromAction = cameFromAction;
        }

        @Override
        public String toString()
        {
            String actionName = "NULL";
            if(cameFromAction != null)
                actionName = cameFromAction.getClass().toString();

            return String.format("[Action name: %s, world state: %s]", actionName, worldState.getPropertyList().toString());
        }
    }
}

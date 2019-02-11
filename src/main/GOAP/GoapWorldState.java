package main.GOAP;

import java.util.HashMap;

public class GoapWorldState
{
    private GoapWorldPropertyList propertyList;

    @Override
    public String toString()
    {
        return propertyList.toString();
    }

    public GoapWorldState(GoapWorldPropertyList propertyList)
    {
        this.propertyList = propertyList;
    }

    public GoapWorldPropertyList getPropertyList()
    {
        return propertyList;
    }

    public GoapWorldState copyAddAction(GoapAction action)
    {
        /*
        What happens here:
            The current worldstate is copied.
            The effects of the action are applied to it.
            The preconditions of the action are added to it.
        */

        // Todo: yeah...fix this? It makes a deep copy of a worldstate
        HashMap<String, Boolean> clonedMap = new HashMap<String, Boolean>(this.propertyList.getPropertiesClone());
        GoapWorldPropertyList copiedProperties = new GoapWorldPropertyList(clonedMap);
        GoapWorldState newWorldState = new GoapWorldState(copiedProperties);

        newWorldState.propertyList.mergeInversed(action.getEffects());
        newWorldState.propertyList.merge(action.getPreconditions());

        return newWorldState;
    }
}

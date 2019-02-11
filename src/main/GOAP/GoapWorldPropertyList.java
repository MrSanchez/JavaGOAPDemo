package main.GOAP;

//import util.MapUtils;

import java.util.HashMap;
import java.util.Map;

public class GoapWorldPropertyList
{
    // Just a wrapper class for the hashset of properties
    private HashMap<String, Boolean> properties;

    public GoapWorldPropertyList(HashMap<String, Boolean> properties)
    {
        this.properties = properties;
    }

    public HashMap<String, Boolean> getPropertiesClone()
    {
        HashMap<String, Boolean> newMap = new HashMap<String, Boolean>(this.properties);
        return newMap;
    }

    @Override
    public String toString()
    {
        return properties.toString();
    }

    public void add(String key, boolean value)
    {
        this.properties.put(key, value);
    }

    // alias: satisfies
    public boolean hasChangedValues(GoapWorldPropertyList otherList)
    {
        for(String key : otherList.properties.keySet())
        {
            if(this.properties.containsKey(key))
            {
                if(this.properties.get(key) != otherList.properties.get(key)) return true;
            }
        }
        return false;
    }

    public boolean satisfies(GoapWorldPropertyList otherList)
    {
        for(String key : otherList.properties.keySet())
        {
            if(this.properties.containsKey(key))
            {
                if(this.properties.get(key) == otherList.properties.get(key)) return true;
            }
        }
        return false;
    }

    // alias: amountUnsatisfiedProperties
    public int getChangedValuesAmount(GoapWorldPropertyList otherList)
    {
        int count = 0;
        for(String key : otherList.properties.keySet())
        {
            if(this.properties.containsKey(key))
            {
                if(this.properties.get(key) != otherList.properties.get(key))
                {
                    count++;
                }
            } else {
                count++;
            }
        }
        return count;
    }

    public void merge(GoapWorldPropertyList list)
    {
        properties.putAll(list.properties);
    }

    public void mergeInversed(GoapWorldPropertyList effects)
    {
        for(Map.Entry<String, Boolean> entry : effects.properties.entrySet())
        {
            properties.put(entry.getKey(), !entry.getValue());
        }
    }
/*
    public boolean equals(GoapWorldPropertyList otherList)
    {
        return MapUtils.mapsAreEqual(this.properties, otherList.properties);
    }*/

    public boolean containsAll(GoapWorldPropertyList otherList)
    {
        return this.properties.entrySet().containsAll(otherList.properties.entrySet());
    }
}

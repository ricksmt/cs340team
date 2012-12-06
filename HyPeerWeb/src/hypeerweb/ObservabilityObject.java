package hypeerweb;

import java.util.Observable;

public class ObservabilityObject extends Observable implements java.io.Serializable
{
    public void changed()
    {
        setChanged();
    }
}

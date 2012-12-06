package hypeerweb;

import java.util.Observable;

public class HyPeerWebObservable extends Observable implements java.io.Serializable
{
    public void changed()
    {
        setChanged();
    }
}

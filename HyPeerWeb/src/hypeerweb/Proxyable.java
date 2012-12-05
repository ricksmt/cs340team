package hypeerweb;

public interface Proxyable {

    /*
    
    private transient command.GlobalObjectId id;
    
    public ProxyableObject()
    {
        id = new command.GlobalObjectId();
        command.ObjectDB.getSingleton().store(id.getLocalObjectId(), this);
    }
    
    public ProxyableObject(command.GlobalObjectId globalObjectId)
    {
        id = globalObjectId;
        command.ObjectDB.getSingleton().store(id.getLocalObjectId(), this);
    }*/
    
    public command.GlobalObjectId getId();
}
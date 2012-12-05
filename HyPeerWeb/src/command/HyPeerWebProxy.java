package command;

import hypeerweb.Node;

import java.util.Collection;

//import GlobalObjectId;
public class HyPeerWebProxy
    extends hypeerweb.HyPeerWeb
{
    private static GlobalObjectId globalObjectId;

    public HyPeerWebProxy(GlobalObjectId globalObjectId)
    {
        this.globalObjectId = globalObjectId;
    }

    public static hypeerweb.HyPeerWeb getSingleton()
    {
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "getSingleton", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.HyPeerWeb)result;
    }

    public void saveToDatabase()
    {
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "saveToDatabase", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void addNode(hypeerweb.Node p0)
    {
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "addNode", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void addToHyPeerWeb(hypeerweb.Node p0, hypeerweb.Node p1)
    {
        String[] parameterTypeNames = new String[2];
        parameterTypeNames[0] = "hypeerweb.Node";
        parameterTypeNames[1] = "hypeerweb.Node";
        Object[] actualParameters = new Object[2];
        actualParameters[0] = null;//we don't really need to pass p0
        actualParameters[1] = p1;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "addToHyPeerWeb", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void removeFromHyPeerWeb()
    {
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "removeFromHyPeerWeb", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void removeFromHyPeerWeb(int p0)
    {
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "int";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "removeFromHyPeerWeb", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void clear()
    {
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "clear", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public boolean contains(hypeerweb.Node p0)
    {
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "contains", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Boolean)result;
    }

    public int size()
    {
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "size", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Integer)result;
    }

    public void reload(java.lang.String p0)
    {
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "java.lang.String";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "reload", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void reload()
    {
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "reload", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public hypeerweb.Node getNode(int p0)
    {
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "int";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "getNode", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        if (result.getClass()==NodeProxy.class || result.getClass()==hypeerweb.Node.class)
            return (hypeerweb.Node)result;
        else
            return null;
    }

    public boolean equals(java.lang.Object p0)
    {
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "java.lang.Object";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "java.lang.Object", "equals", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Boolean)result;
    }

    public java.lang.String toString()
    {
        /*String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "java.lang.Object", "toString", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (java.lang.String)result;*/
        return "I'm a proxy hypeerweb: " + globalObjectId.toString();
    }

    public int hashCode()
    {
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "java.lang.Object", "hashCode", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Integer)result;
    }
    
    public void connectToSegment(String ipAddress, int portNumber, int localObjectId)
    {
        String[] parameterTypeNames = new String[3];
        parameterTypeNames[0] = "java.lang.String";
        parameterTypeNames[1] = "int";
        parameterTypeNames[2] = "int";
        Object[] actualParameters = new Object[3];
        actualParameters[0] = ipAddress;
        actualParameters[1] = portNumber;
        actualParameters[2] = localObjectId;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "connectToSegment", parameterTypeNames, actualParameters, true);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }
    
    public Collection<Node> getNodesInHyPeerWeb()
    {
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "getNodesInHyPeerWeb", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Collection<Node>)result;
    }
    
    public void migrateNodes()
    {
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "migrateNodes", parameterTypeNames, actualParameters, true);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }
    
    public Node migrateNodeToSegment(Node node)
    {
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = node;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.HyPeerWeb", "migrateNodeToSegment", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Node)result;
    }

}

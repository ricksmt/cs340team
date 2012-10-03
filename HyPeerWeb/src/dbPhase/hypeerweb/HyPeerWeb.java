package dbPhase.hypeerweb;

import java.util.*;


public class HyPeerWeb
{
    private HashMap<Integer,Node> nodes;
    @SuppressWarnings("unused")
    private int size;
    
    private static HyPeerWeb singleton;
    private HyPeerWebDatabase database;

    
    HyPeerWeb()
    {
        nodes = new HashMap<Integer,Node>();
        database = HyPeerWebDatabase.getSingleton();
        size = 0;
    }
	
    public static HyPeerWeb getSingleton()
    {
        if(singleton == null) singleton = new HyPeerWeb();
        return singleton;

	}

	public void clear()
	{
	    nodes.clear();
	}

	public int size()
	{
	    return nodes.size();
	}

	public void reload(final String string)
	{   
	    HyPeerWebDatabase.initHyPeerWebDatabase(string);
        database = HyPeerWebDatabase.getSingleton();
	    
        nodes.clear(); 
        nodes.putAll(database.loadNodeSet());
	}

	public Node getNode(final int i)
	{
	    final Node temp = nodes.get(i);
	    if(temp == null) return Node.NULL_NODE;
	    else return temp;
	}

	public void reload()
	{
		reload(null);	
	}

	public void saveToDatabase()
	{
	    database.save(nodes.values());
	}

	public void addNode(final Node node0)
	{
	    nodes.put(node0.getWebId(), node0);
	}

	public boolean contains(final Node node0)
	{
	    return nodes.containsValue(node0);
	}
	
	/**
	 * 
	 * @param newNode
	 * @param startNode
	 */
	public void addToHyPeerWeb(final Node newNode, final Node startNode)
	{
	    assert newNode != null && newNode != Node.NULL_NODE;
	    if ( nodes.size() > 0)
	    {
	        assert startNode != null;
	        newNode.insertSelf(startNode);
	    }
	    else
	    {
	        addNode(newNode);
	    }
	}
}

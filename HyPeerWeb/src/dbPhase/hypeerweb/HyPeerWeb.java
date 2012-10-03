package dbPhase.hypeerweb;

import java.util.*;


/**
 * The Class HyPeerWeb.
 */
public class HyPeerWeb
{
    
    /** The nodes. */
    private HashMap<Integer,Node> nodes;
    
    /** The singleton. */
    private static HyPeerWeb singleton;
    
    /** The database. */
    private HyPeerWebDatabase database;

    
    /**
     * Instantiates a new HyPeerWeb.
     */
    HyPeerWeb()
    {
        nodes = new HashMap<Integer,Node>();
        database = HyPeerWebDatabase.getSingleton();
    }
	
    /**
     * Gets the singleton HyPeerWeb.
     *
     * @obvious
     * @return the singleton
     */
    public static HyPeerWeb getSingleton()
    {
        if(singleton == null) singleton = new HyPeerWeb();
        return singleton;

	}

	/**
	 * Clear.
	 * 
     * @obvious
	 */
	public void clear()
	{
	    nodes.clear();
	}

	/**
	 * Size.
	 *
     * @obvious
	 * @return the size of the HyPeerWeb
	 */
	public int size()
	{
	    return nodes.size();
	}

	/**
	 * Reloads the HyPeerWeb from database who's name is given.
	 *
	 * @param string the database name
	 */
	public void reload(final String string)
	{   
	    HyPeerWebDatabase.initHyPeerWebDatabase(string);
        database = HyPeerWebDatabase.getSingleton();
	    
        nodes.clear(); 
        nodes.putAll(database.loadNodeSet());
	}

	/**
	 * Gets the node.
	 *
     * @obvious
	 * @param i a whole number
	 * @return the node
	 */
	public Node getNode(final int i)
	{
	    final Node temp = nodes.get(i);
	    if(temp == null) return Node.NULL_NODE;
	    else return temp;
	}

	/**
	 * Reload.
	 */
	public void reload()
	{
		reload(null);	
	}

	/**
	 * Save to database.
	 */
	public void saveToDatabase()
	{
	    database.save(nodes.values());
	}

	/**
	 * Adds the node.
	 *
	 * @param node0 the node to add to the HyPeerWeb
	 */
	public void addNode(final Node node0)
	{
	    nodes.put(node0.getWebId(), node0);
	}

	/**
	 * Contains.
	 *
     * @obvious
	 * @param node0 a Node
	 * @return true, if successful
	 */
	public boolean contains(final Node node0)
	{
	    return nodes.containsValue(node0);
	}
	
	/**
	 * Adds the to HyPeerWeb.
	 *
	 * @param newNode the new node
	 * @param startNode the start node
	 */
	public void addToHyPeerWeb(final Node newNode, final Node startNode)
	{
	    assert newNode != null && newNode != Node.NULL_NODE;
	    if ( nodes.size() > 0)
	    {
	        assert startNode != null;
	        newNode.insertSelf(startNode);
	    }
        addNode(newNode);
	}
}

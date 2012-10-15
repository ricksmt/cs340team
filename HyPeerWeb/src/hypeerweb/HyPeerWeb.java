package hypeerweb;

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
     * 
     * @pre None
     * @post A valid HyPeerWeb with height == 0
     */
    HyPeerWeb()
    {
        nodes = new HashMap<Integer,Node>();
        database = HyPeerWebDatabase.getSingleton();
    }
	
    /**
     * Gets the single HyPeerWeb.
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
     * @pre None
     * @post This is now a valid HyPeerWeb based
     *  on the given database connection string
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
	 * 
     * @pre None
     * @post This is now a valid HyPeerWeb based
     *  on the default database connection string
	 */
	public void reload()
	{
		reload(null);	
	}

	/**
	 * Save to database.
	 * 
	 * @pre None
	 * @post the corresponding database reflects the current HyPeerWeb
	 */
	public void saveToDatabase()
	{
	    database.save(nodes.values());
	}

	/**
	 * Adds the node.
	 *
	 * @param node0 the node to add to the HyPeerWeb
     * @pre HyPeerWeb does not contain a node with node0's ID
     * @post HyPeerWeb contains node0
	 */
	public void addNode(final Node node0)
	{
	    assert !nodes.containsKey(node0.getWebId());
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
     * @pre newNode is a valid Node and startNode is contained with this valid HyPeerWeb
     * @post this HyPeerWeb is valid, no nodes previously within HyPeerWeb have changed IDs,
     *  and HyPeerWeb contains newNode. 
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
	
	/** 
	 * Remove a random node from the HypeerWeb
	 * @pre HypeerWeb has at least 2 nodes
	 * @post one node is removed from the HypeerWeb
	 */
	public void removeFromHyPeerWeb()
	{
	    // pick a random node in the HyPeerWeb
	    // node.removeFromHyPeerWeb();
	}
}
package hypeerweb;

import java.util.*;

import command.GlobalObjectId;
import command.HyPeerWebProxy;
import command.LocalObjectId;
import command.PortNumber;


/**
 * The Class HyPeerWeb.
 */
public class HyPeerWeb extends ProxyableObject
{
    
    /** The nodes. */
    private transient Map<Integer,Node> nodes;
    
    /** The singleton. */
    private transient static HyPeerWeb singleton;
    
    /** The database. */
    private transient HyPeerWebDatabase database;
    
    private transient HyPeerWeb nextSegment;
    private transient HyPeerWeb previousSegment;

    
    /**
     * Instantiates a new HyPeerWeb.
     * 
     * @pre None
     * @post A valid HyPeerWeb with height == 0
     */
    protected HyPeerWeb()
    {
        super();
        nodes = new HashMap<Integer,Node>();
        database = HyPeerWebDatabase.getSingleton();
        nextSegment = null;
        previousSegment = null;
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
	public synchronized void clear()
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
	public synchronized void reload(final String string)
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
	 * @return the node or Node.NULL_NODE if it doesn't exist.
	 */
	public Node getNode(final int i)
	{
	    assert i >= 0 && i < size();
	    return nodes.get(i);
	}

	/**
	 * Reload.
	 * 
     * @pre None
     * @post This is now a valid HyPeerWeb based
     *  on the default database connection string
	 */
	public synchronized void reload()
	{
		reload(null);	
	}

	/**
	 * Save to database.
	 * 
	 * @pre None
	 * @post the corresponding database reflects the current HyPeerWeb
	 */
	public synchronized void saveToDatabase()
	{
	    database.save(nodes.values());
	}

	/**
	 * Adds the node.
	 *
	 * @param node0 the node to add to the HyPeerWeb's Map
     * @pre HyPeerWeb does not contain a node with node0's ID
     * @post HyPeerWeb contains node0
	 */
	public synchronized void addNode(final Node node0)
	{
	    assert !nodes.containsKey(node0.getWebId());
	    nodes.put(node0.getWebId(), node0);
	}

	/**
	 * Returns whether this segment contains a node with the given id as specified by the parameter.
	 *
	 * @param node0 a Node
	 * @pre none
     * @post no change
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
     * @pre startNode is contained with this valid HyPeerWeb if the HyPeerWeb size is greater than 0
     * @post this HyPeerWeb is valid, no nodes previously within HyPeerWeb have changed IDs,
     *  and HyPeerWeb contains newNode or a newly created Node. 
	 */
	public synchronized void addToHyPeerWeb(final Node newNode, final Node startNode)
	{
	    Node toAdd = newNode;
	    if (toAdd == null || toAdd == Node.NULL_NODE)
	    {
	        toAdd = new Node();
	    }
	    if (nodes.size() > 0)
	    {
	        assert startNode != null && startNode != Node.NULL_NODE;
	        toAdd.insertSelf(startNode);
	    }
	    
	    
	    addNode(toAdd);
	}
	
	/** 
	 * Remove a random node from the HypeerWeb
	 * @pre HypeerWeb has at least 2 nodes
	 * @post one node is removed from the HypeerWeb and
	 *     the HypeerWeb is valid
	 */
	public synchronized void removeFromHyPeerWeb()
	{
	    removeFromHyPeerWeb((new Random()).nextInt(nodes.size()));
	}
	
	/** 
     * Remove a node from the HypeerWeb
     * @pre HypeerWeb has at least 2 nodes and 
     *  id is the WebID of one of those nodes
     * @post one node is removed from the HypeerWeb
     * @param id the webId of the node you want to remove
     */
    public synchronized void removeFromHyPeerWeb(final int id)
    {
        assert id >= 0 && id < size();
        final Node node = nodes.get(id);
        node.removeFromHyPeerWeb();
        nodes.put(id, nodes.get(size() - 1));
        nodes.remove(size() - 1);
    }
    
    public synchronized void connectToSegment(String ipAddress, int portNumber, int localObjectId)
    {
        GlobalObjectId segmentId = new GlobalObjectId(ipAddress, new PortNumber(portNumber),
                new LocalObjectId(localObjectId));
        
        HyPeerWeb segment = new HyPeerWebProxy(segmentId);
        Collection<Node> toInsert = segment.getNodesInHyPeerWeb();
        
        if (nextSegment != null)
        {
            segment.setNextSegment(nextSegment);
        }
            
        nextSegment = segment;
        segment.setPreviousSegment(this);
        
        //Retrieve any node in this segment
        Node startNode = null;
        
        if (nodes.size() > 0)
            startNode = nodes.values().iterator().next();
        
        //Insert all the nodes from the other segment's hypeerweb into my hypeerweb
        for (Node nodeToInsert : toInsert)
        {
            addToHyPeerWeb(startNode,nodeToInsert);
        }
    }

    public synchronized void setNextSegment(HyPeerWeb nextSegment2)
    {
        nextSegment = nextSegment2;
    }

    public synchronized void setPreviousSegment(HyPeerWeb hyPeerWeb)
    {
        previousSegment = hyPeerWeb;
    }

    public Collection<Node> getNodesInHyPeerWeb()
    {
        if (nodes.size() > 0)
        {
            HyPeerWebVisitor visitor = new HyPeerWebVisitor();
            Node startNode = nodes.values().iterator().next();
            visitor.visit(startNode, BroadcastVisitor.createInitialParameters());
            return visitor.getNodes();
        }
        return new HashSet<Node>(0);
    }
    
    
    
    public void migrateNodes()
    {
        
    }
}

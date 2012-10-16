
/** 
 * A node in the HyPeerWeb<br>
 * <br>
 * 
 * <pre>
 * <b>Domain</b>
 *      webid : WedId
 *      connections : Connections
 * </pre>
 * 
 * @author Trevor Bentley, Brian Davis, Matthew
 * 
 * */

package hypeerweb;

import java.util.HashSet;
import java.util.Iterator;

public class Node implements Comparable<Node>
{
    /**
     * This represents the state of the node in the cap node finding algorithm.
     * The findCapNode method is called to produce the correct behavior at each step.
     * 
     *   @author Matthew, Brian, Trevor
     */
    public enum State
    {   
        CAP
        {
            @Override
            public Node findCapNode(final Node n)
            {
                return n;
            }
            public State getInitialStateofChild()
            {
                return CAP;
            }
            public State getNextState()
            {
                return STANDARD;
            }
        },
        DOWN
        {
            @Override
            public Node findCapNode(final Node n)
            {
                if(n.connections.getSurrogateNeighbors().size() > 0) return n.getHighestSurrogateNeighbor();
                else return n.getHighestNeighbor();
            }
            public State getInitialStateofChild()
            {
                return DOWN;
            }
            
            public State getNextState()
            {
                return STANDARD;
            }
        },
        STANDARD
        {
            @Override
            public Node findCapNode(final Node n)
            {
                return n.getHighestNeighbor();//Or highest fold?
            }
            public State getInitialStateofChild()
            {
                return DOWN;
            }
            
            public State getNextState()
            {
                return STANDARD;
            }
        };
        
        public abstract Node findCapNode(Node n);
        public abstract State getInitialStateofChild();
        public abstract State getNextState();
    }
    
    /**
     * State represents this node's position (and next action) in the cap node locating algorithm
     */
    protected State state;
    
    /**
     * This node's webId
     */
    protected WebId webid;
    
    /**
     * All of this nodes relations to other nodes (neighbors, folds, surrogates, etc.)
     */
    protected Connections connections;
    
	public static final Node NULL_NODE = null;
	
	/**
	 * Constructor for a new node.
	 * It's webId is given by the parameter.
	 * @param i
	 * @pre none
	 * @post This node has the given webId, and is it's own fold.
	 */
	public Node(final int i) //Wait, how would we even know what it's webId is supposed to be?
	{
		webid = new WebId(i);
		connections = new Connections();
		connections.setFold(this);
		state = State.CAP;
	}
	
	/**
	 * @obvious
	 * @return the neighboring node with the largest id
	 */
	public Node getHighestNeighbor()
	{
        return connections.getHighestNeighbor();
    }
	
	/**
     * @obvious
     * @return the neighboring node with the smallest id
     */
    public Node getLowestNeighbor()
    {
        return connections.getLowestNeighbor();
    }
	
	/**
	 * @obvious
     * @return the surrogate neighbor node with the largest id
	 */
    public Node getHighestSurrogateNeighbor()
    {
        return connections.getHighestSurrogateNeighbor();
    }
    
    /**
     * Returns fully initialized SimplifiedNodeDomain needed for testing
     * 
     * @pre this node is initialized
     * @post no changes to this node
     * @return SimplifiedNodeDomain representing this node
     */
    public SimplifiedNodeDomain constructSimplifiedNodeDomain() 
	{
	    final HashSet<Integer> intNeighbors = new HashSet<Integer>();
	    final HashSet<Integer> intSurrogateNeighbors = new HashSet<Integer>();
	    final HashSet<Integer> intInverseSurrogateNeighbors = new HashSet<Integer>();
	    int tempFold = -1;
	    int tempSurrogateFold = -1;
	    int tempInverseSurrogateFold = -1;
	    
	    
	    //convert HashSets to integer hash sets using iteration
	    Iterator<Node> iter = connections.getNeighbors().iterator();
	    while(iter.hasNext())
	    {
	        final Node temp = iter.next();
	        intNeighbors.add(temp.webid.getValue());
	    }
	    
	    iter = connections.getSurrogateNeighbors().iterator();
	    while(iter.hasNext())
        {
            final Node temp = iter.next();
            intSurrogateNeighbors.add(temp.webid.getValue());
        }
	    
	    iter = connections.getInverseSurrogateNeighbors().iterator();
        while(iter.hasNext())
        {
            final Node temp = iter.next();
            intInverseSurrogateNeighbors.add(temp.webid.getValue());
        }
        
        final Node fold = connections.getFold();
        final Node surrogateFold = connections.getSurrogateFold();
        final Node inverseSurrogateFold = connections.getInverseSurrogateFold();
        
        if(fold != NULL_NODE) tempFold = fold.webid.getValue();
        if(surrogateFold != NULL_NODE) tempSurrogateFold = surrogateFold.webid.getValue();
        if(inverseSurrogateFold != NULL_NODE) tempInverseSurrogateFold = inverseSurrogateFold.webid.getValue();
	    
	    final SimplifiedNodeDomain simpleNode = new SimplifiedNodeDomain( webid.getValue(),
                                                        		        getHeight(),
                                                                        intNeighbors,
                                                                        intInverseSurrogateNeighbors, 
                                                                        intSurrogateNeighbors,
                                                                        tempFold, 
                                                                        tempSurrogateFold,
                                                                        tempInverseSurrogateFold);
		return simpleNode;
	}
	
	/**
	 * @obvious
	 * @param webId 
	 */
	public void setWebId(final WebId webId) 
	{
		webid = webId;
	}
	
	/**
	 * @obvious
	 * @param state 
	 */
	public void setState(final State state)
	{
	    this.state = state;
	}
	
	/**
	 * @obvious
	 * @param node 
	 */
	public void setFold(final Node node) 
	{
	    // if node WebId is fold of this.WebId
	    connections.setFold(node);
	}
	
	/**
	 * @obvious
	 * @param node 
	 */
	public void setSurrogateFold(final Node node) 
	{
	    // if node WebId is surrogate fold of this.WebId
		connections.setSurrogateFold(node);
	}
	
	/**
	 * @obvious
	 * @param node 
	 */
	public void setInverseSurrogateFold(final Node node) 
	{
	    connections.setInverseSurrogateFold(node);
	}
	
	/**
	 * @obvious
	 * @param node 
	 */
	public void addNeighbor(final Node node) 
	{
	    // if WebIds are neighbors - can't add itself as a neighbor
	    if(node != this)
	        connections.addNeighbor(node);
	}
	
	/**
	 * @obvious
	 * @param node 
	 */
	public void removeNeighbor(final Node node) 
	{
	    connections.removeNeighbor(node);
	}
	
	/**
	 * @obvious
	 * @return int representation of the node's web id
	 */
	public int getWebId()
	{
	    return webid.getValue();
	}
	
	/**
	 * @obvious
	 * @return the height of this node
	 */
	public int getHeight()
	{
	    return connections.getNeighbors().size() + connections.getSurrogateNeighbors().size();
	}
	
	
	/**
     * @obvious
     * @param node0 
     */
    public void addUpPointer(final Node node0)
    {
        if(node0 != this) connections.addInverseSurrogateNeighbor(node0);
    }
    
    /**
     * @obvious
     * @param node 
     */
    public void removeUpPointer(final Node node)
    {
        connections.removeInverseSurrogateNeighbor(node);
    }
    
    /**
     * @obvious
     * @param node 
     */
    public void addDownPointer(final Node node)
    {
        // Cannot add itself as a Down Pointer
        if(node != this)
            connections.addSurrogateNeighbor(node);
    }
    
    /**
     * @obvious
     * @param node 
     */
    public void removeDownPointer(final Node node)
    {
        connections.removeSurrogateNeighbor(node);
    }
    
    /**
     * @obvious
     * @return a set of the neighboring nodes ids
     */
    public HashSet<Integer> getNeighborsIds()
    {
        return connections.getNeighborsIds();
    }
    
    /**
     * @obvious
     * @return copy of surrogate neighbor set
     */
    public HashSet<Integer> getSurNeighborsIds()
    {
        return connections.getSurrogateNeighborsIds();
    }
    
    /**
     * @obviousNR
     * @return copy of inverse surrogate neighbor set
     */
    public HashSet<Integer> getInvSurNeighborsIds()
    {
        return connections.getInverseSurrogateNeighborsIds();
    }
    
    
    /**
     * insertSelf
     * This method is called on a new node that hasn't been put into the HyPeerWeb yet.
     * 
     * @pre start node is part of a valid HyPeerWeb. This node is not part of the HyPeerWeb.
     * @post This node will be part of the HyPeerWeb and all connections will be modified to match the project constraints
     * @param startNode 
     */
    public void insertSelf(final Node startNode)
    {
        final Node parent = findInsertionPoint(startNode);
        webid = new WebId((int) (parent.webid.getValue() + 
                Math.pow(2, parent.getHeight())));
        
        // Give child its' connections.
        connections = parent.connections.getChildConnections(parent);
        connections.addNeighbor(parent);
        
        // Update states
        setState(parent.state.getInitialStateofChild());
        parent.setState(parent.state.getNextState());
        
        // Child Notify
        connections.childNotify(this, parent);
        
        // Parent Notify
        parent.connections.parentNotify(parent);
    }
    
    /**
     * findInsertionPoint
     * @param startNode 
     * @return The node which is the insertion point
     */
    private Node findInsertionPoint(Node startNode)
    {
        Node currentNode = startNode.state.findCapNode(startNode);
        //This loop controls the stepping of the algorithm finding the cap node
        while(currentNode != startNode)
        {
            startNode = currentNode;
            currentNode = currentNode.state.findCapNode(currentNode);
        }//The cap node is now found (currentNode).
        
        do
        {
            startNode = currentNode;
            currentNode = currentNode.getLowestNeighborWithoutChild();
        }
        while(currentNode != startNode);
        return currentNode;
    }
    
    /**
     * Remove this node from the HypeerWeb
     */
    public void removeFromHyPeerWeb()
    {
        // find deletionPoint from this point
        final Node deletionPoint = findDeletionPoint(this);
        // Disconnect deletionPont
        deletionPoint.disconnectDeletionPoint();
        // Replace deleted node with deletionPoint node 
        Connections.replace(this, deletionPoint);
        
        // Delete node from HyPeerWeb - Garbage collection should take care of it
    }
    
    /**
     * Find the deletion point from startNode
     * @param startNode 
     * @return The node which is the deletion point
     */
    private Node findDeletionPoint(Node startNode)
    {
        Node currentNode = startNode.state.findCapNode(startNode);
        //This loop controls the stepping of the algorithm finding the cap node
        while(currentNode != startNode)
        {
            startNode = currentNode;
            currentNode = currentNode.state.findCapNode(currentNode);
        }//The cap node is now found (currentNode).
        
        // Node 0 
        currentNode = currentNode.connections.getFold();
        
        // Loop till you get to the deletionPoint - currentNode will then be set to its highest
        // neighbor which will have a WebID less than the deletion point's WebID.
        do
        {
            startNode = currentNode;
            currentNode = currentNode.getHighestNeighbor();
        }
        while(currentNode.getWebId() > startNode.getWebId()); 
        return startNode;
    }
    
    /**
     * Disconnect the deletion point from the HypeerWeb
     * 
     * Called from the deletionPoint node
     */
    private void disconnectDeletionPoint()
    {
        Connections.disconnect(this);
    }
    
    /**
     * @obviousNR
     * @return The lowest neighbor that does not have a child.
     */
    public Node getLowestNeighborWithoutChild()
    {
        final Node temp = connections.getLowestNeighborWithoutChild();
        if(temp == NULL_NODE) return this;
        else if(temp.compareTo(this) < 0 && temp.getHeight() <= getHeight()) return temp;
        else return this;
    }
    
    /**
     * @obvious
     * @return the fold's id
     */
    public int getFoldId()
    {
        return connections.getFoldId() ;
    }
    
    /**
     * @obvious
     * @return the surrogate fold's id
     */
    public int getSurrogateFoldId()
    {
        return connections.getSurrogateFoldId();
    }
    
    /**
     * @obvious
     * @return the inverse surrogate fold's id
     */
    public int getInverseSurrogateFoldId()
    {
        return connections.getInverseSurrogateFoldId();
    }


    @Override
    public int compareTo(final Node o)
    {
        return webid.compareTo(o.webid);
    }
}


/** 
 * Surrogate neighbors are called DownPointers.
 * Inverse surrogate neighbors are called UpPointers.
 * Refer to: http://students.cs.byu.edu/~cs340ta/spring2012/projects/hypeerwebdesc/ 
 * 
 * Node.java
 * @author Trevor Bentley, Brian Davis, Matthew
 * 
 * */

package dbPhase.hypeerweb;

import java.util.HashSet;
import java.util.Iterator;

public class Node
{
    /**
     * This represents the state of the node in the cap node finding algorithm.
     * The findCapNode method is called to produce the correct behavoir at each step.
     * @author Matthew, Brian, Trevor
     *
     */
    protected enum State
    {   
        CAP
        {
            @Override
            public Node findCapNode(Node n)
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
            public Node findCapNode(Node n)
            {
                return n.getHighestSurrogateNeighbor();
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
            public Node findCapNode(Node n)
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
    
    protected State state;
    private WebId webid;   
    private Connections connections;
    
	public static final Node NULL_NODE = null;
	
	/**
	 * Constructor for a new node.
	 * It's webId is given by the parameter.
	 * @param i
	 */
	public Node(final int i) //Wait, how would we even know what it's webId is supposed to be?
	{
		webid = new WebId(i);
	}

	public Node getHighestNeighbor() {
        return connections.getHighestNeighbor();
    }

    public Node getHighestSurrogateNeighbor() {
        return connections.getHighestSurrogateNeighbor();
    }

    public SimplifiedNodeDomain constructSimplifiedNodeDomain() 
	{
	    final HashSet<Integer> intNeighbors = new HashSet<Integer>();
	    final HashSet<Integer> intSurrogateNeighbors = new HashSet<Integer>();;
	    final HashSet<Integer> intInverseSurrogateNeighbors = new HashSet<Integer>();;
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
        
        Node fold = connections.getFold();
        Node surrogateFold = connections.getSurrogateFold();
        Node inverseSurrogateFold = connections.getInverseSurrogateFold();
        
        if(fold != NULL_NODE) tempFold = fold.webid.getValue();
        if(surrogateFold != NULL_NODE) tempSurrogateFold = surrogateFold.webid.getValue();
        if(inverseSurrogateFold != NULL_NODE) tempInverseSurrogateFold = inverseSurrogateFold.webid.getValue();
	    
	    final SimplifiedNodeDomain simpleNode = new SimplifiedNodeDomain( webid.getValue(),
                                                        		        webid.getHeight(),
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
	public void setState(State state)
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
	    // if WebIds are neighbors
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

	public int getWebId()
	{
	    return webid.getValue();
	}
	
	public int getHeight()
	{
	    return webid.getHeight();
	}
	
	
	/**
     * @obvious
     * @param node0
     */
    public void addUpPointer(final Node node0)
    {
        connections.addInverseSurrogateNeighbor(node0);
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
     * @param node
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
     * @obvious
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
    public void insertSelf(Node startNode)
    {
        Node currentNode;
        Node nextNode = startNode;
        //This loop controls the stepping of the algorithm finding the cap node
        do
        {
            currentNode = nextNode;
            nextNode = state.findCapNode(currentNode);
            
        } while (nextNode != currentNode);
        //The cap node is now found (currentNode).
        
        //Should we change our findCapNode to findInsertionPoint, and put this as a 4th state?
        while (currentNode.getLowestNeighborWithoutChild() != null)
        {
            currentNode = currentNode.getLowestNeighborWithoutChild();
        }
        
        // set connections for inserted Node
        setConnectionsWithInsertionPoint(currentNode);
        
        // set new Node's state
        setState(currentNode.state.getInitialStateofChild());
        
        // update parent node's state
        currentNode.setState(currentNode.state.getNextState());
    }
    
    public Node getLowestNeighborWithoutChild() {
        return connections.getLowestNeighborWithoutChild();
    }

    /**
     * setConnectionsWithInsertionPoint
     * This method uses the information from the insertion point to create all the needed connections for the 
     * new node.
     * It also adds the needed connections to the new node (this one). 
     * (This essentially adds this node to the HyPeerWeb)
     * 
     * @pre insertionPoint is the correct insertion point in the HyPeerWeb. That is, it is the lowest node on the cap node's layer without a child.
     * @post This node is inserted as the insertionPoint's child, and connections are adjusted such that all the project constraints are met.
     * 
     * @param insertionPoint
     */
    private void setConnectionsWithInsertionPoint(Node insertionPoint)
    {
        int parentWebId = insertionPoint.getWebId();
        int bitMask = 0x80000000;
        //scan for highest order bit
        for (;;)
        {
            if ((bitMask & parentWebId) != 0)
            {
                //if found, raise bit and add it to the insertions point webId
                bitMask = bitMask << 1;
                webid = new WebId(bitMask | parentWebId);
                break;
            }
            else
            {
                bitMask = bitMask >>> 1;
            }
        }
        
        // Set new Node's connections based off of parent's connections
        connections = insertionPoint.connections.extractChildConnections();
       
        // Notify new connections 
        connections.notify(webid);
    }
    

}

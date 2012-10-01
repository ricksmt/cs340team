
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
     * @author Mathew, Brian
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
        },
        DOWN
        {
            @Override
            public Node findCapNode(Node n)
            {
                return n.getHighestSurrogateNeighbor();
            }
        },
        STANDARD
        {
            @Override
            public Node findCapNode(Node n)
            {
                return n.getHighestNeighbor();//Or highest fold?
            }
        };
        
        public abstract Node findCapNode(Node n);
    }
    
    /**
     * state represents this node's position (and next action) in the cap node locating algorithm
     */
    protected State state;
    
    /**
     * This node's webId
     */
    private WebId webid;
    
    /**
     * All of this nodes relations to other nodes (neighbors, folds, surrogates, etc.)
     */
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
	
	/**
	 * @obvious
	 * @return
	 */
	public Node getHighestNeighbor() {
        return connections.getHighestNeighbor();
    }
	
	/**
	 * @obvious
	 * @return
	 */
    public Node getHighestSurrogateNeighbor() {
        return connections.getHighestSurrogateNeighbor();
    }
    
    /**
     * Returns fully initailized SimplifiedNodeDomain needed for testing
     * 
     * @pre this node is initailized
     * @post no changes to this node
     * 
     * @return SimplifiedNodeDomain representing this node
     */
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
	 * @return
	 */
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
        
        setConnectionsWithInsertionPoint(currentNode);
    }
    
    /** DEPRECATED, this will be handled elsewhere
     * changeState
     * This method changes the state of the find cap node sequence based on the next node found.
     * 
     * @pre nextNode is part of a valid HyPeerWeb
     * @post state will be change to accurately represent where the node is at looking for the cap node if currentNode is where we currently are.
     * 
     * @param nextNode
     *
    private void changeState(Node currentNode)
    {
        if (currentNode.hasHigherNeighbor())//or fold?
        {
            state = State.STANDARD;
        }
        else if (currentNode.hasHigherSurrogateNeighbor())
        {
            state = State.DOWN;
        }
        else
        {
            state = State.CAP;
        }
    }*/
    
    public Node getLowestNeighborWithoutChild() {
        return connections.getLowestNeighborWithoutChild();
    }

    /**
     * setConnectionsWithInsertionPoint
     * This method uses the information from the insertion point to change all the needed connections,
     * and add the needed connections to the new node (this one). This essentailly actually adds this node to the HyPeerWeb
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
        
        //somthing like...
        connections = insertionPoint.connections.extractChildConnections();
        connections.notify(this);
        
        
    }
    
    public int getFoldId()
    {
        return connections.getFoldId() ;
    }
    
    public int getSurrogateFoldId()
    {
        return connections.getSurrogateFoldId();
    }
    
    public int getInverseSurrogateFoldId()
    {
        return connections.getInverseSurrogateFoldId();
    }
    

}


/** 
 * Surrogate neighbors are called DownPointers.
 * Inverse surrogate neighbors are called UpPointers.
 * Refer to: http://students.cs.byu.edu/~cs340ta/spring2012/projects/hypeerwebdesc/ 
 * 
 * Node.java
 * Edited By: Trevor Bentley
 * 
 * */

package dbPhase.hypeerweb;

import java.util.HashSet;
import java.util.Iterator;

public class Node
{
    //Uhh, I'm not remembering which states are supposed to do what, so I'll guess
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
                return n.connections.getHighestSurrogateNeighbor();
            }
        },
        STANDARD
        {
            @Override
            public Node findCapNode(Node n)
            {
                return n.connections.getHighestNeighbor();//Or highest fold?
            }
        };
        
        public abstract Node findCapNode(Node n);
    }
    
    protected State state;
    private WebId webid;   
    private Connections connections;
    
	public static final Node NULL_NODE = null;

	public Node(final int i) 
	{
		webid = new WebId(i);
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

	public void setWebId(final WebId webId) 
	{
		webid = webId;
	}
	
	public void setFold(final Node node) 
	{
	    // if node WebId is fold of this.WebId
	    fold = node;
	}

	public void setSurrogateFold(final Node node) 
	{
	    // if node WebId is surrogate fold of this.WebId
	    connections.setSurrogateFold(node);
	}

	public void setInverseSurrogateFold(final Node node) 
	{
	    connections.setInverseSurrogateFold(node);
	}
	
	public void addNeighbor(final Node node) 
	{
	    // if WebIds are neighbors
	    connections.addNeighbor(node);
	}

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
	
    public void addUpPointer(final Node node0)
    {
        connections.addInverseSurrogateNeighbor(node0);
    }
    
    public void removeUpPointer(final Node node)
    {
        connections.removeInverseSurrogateNeighbor(node);
    }
    
    public void addDownPointer(final Node node)
    {
        connections.addSurrogateNeighbor(node);
    }
    
    public void removeDownPointer(final Node node)
    {
        connections.removeSurrogateNeighbor(node);
    }
    
    public HashSet<Integer> getNeighborsIds()
    {
        return connections.getNeighborsIds();
    }
    
    public HashSet<Integer> getSurNeighborsIds()
    {
        return connections.getSurrogateNeighborsIds();
    }
    
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
        //state = State.STANDARD;
        Node currentNode;
        Node nextNode = startNode;
        do
        {
            //changeState(nextNode);//this could be placed in the findCapNode methods, but its less code here. Cohesive?
            currentNode = nextNode;
            nextNode = currentNode.state.findCapNode();
            
        } while (nextNode != currentNode);
        
        //Should we change our findCapNode to findInsertionPoint, and put this as a 4th state?
        while (nextNode.connections.getLowestNeighborWithChild() != null)
        {
            nextNode = nextNode.connections.getLowestNeighborWithChild();
        }
        
        setConnectionsWithInsertionPoint(nextNode);
    }
    
    private void changeState(Node nextNode)
    {
        if (nextNode.connections.hasHigherNeighbor())//or fold?
        {
            state = State.STANDARD;
        }
        else if (nextNode.connections.hasHigherSurrogateNeighbor())
        {
            state = State.DOWN;
        }
        else
        {
            state = State.CAP;
        }
    }
    
    private void setConnectionsWithInsertionPoint(Node insertionPoint)
    {
        //TODO
    }
    
}

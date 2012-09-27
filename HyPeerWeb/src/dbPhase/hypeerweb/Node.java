
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
    private HashSet<Node> neighbors;
    private HashSet<Node> surrogateNeighbors;
    private HashSet<Node> inverseSurrogateNeighbors;
    private Node fold;
    private Node surrogateFold;
    private Node inverseSurrogateFold;
    private Connections connections;
    
	public static final Node NULL_NODE = null;

	public Node(final int i) 
	{
		webid = new WebId(i);
		neighbors = new HashSet<Node>();
		surrogateNeighbors = new HashSet<Node>();
		inverseSurrogateNeighbors = new HashSet<Node>();
		fold = NULL_NODE;
		surrogateFold = NULL_NODE;
		inverseSurrogateFold = NULL_NODE;
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
	    Iterator<Node> iter = neighbors.iterator();
	    while(iter.hasNext())
	    {
	        final Node temp = iter.next();
	        intNeighbors.add(temp.webid.getValue());
	    }
	    
	    iter = surrogateNeighbors.iterator();
	    while(iter.hasNext())
        {
            final Node temp = iter.next();
            intSurrogateNeighbors.add(temp.webid.getValue());
        }
	    
	    iter = inverseSurrogateNeighbors.iterator();
        while(iter.hasNext())
        {
            final Node temp = iter.next();
            intInverseSurrogateNeighbors.add(temp.webid.getValue());
        }
        
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
		surrogateFold = node;
	}

	public void setInverseSurrogateFold(final Node node) 
	{
	    inverseSurrogateFold = node;
	}
	
	public void addNeighbor(final Node node) 
	{
	    // if WebIds are neighbors
	    neighbors.add(node);
	}

	public void removeNeighbor(final Node node) 
	{
	    neighbors.remove(node);
	}

	public void addSurrogateNeighbor(final Node node) 
	{
	    // if node WebId is surrogate neighbor of
	    surrogateNeighbors.add(node);
	}

	public void removeSurrogateNeighbor(final Node node) 
	{
	    surrogateNeighbors.remove(node);
	}
	
	public void addInverseSurrogateNeighbor(final Node node)
	{
	    // if this.WebId is surrogate neighbor of node WebId 
	    inverseSurrogateNeighbors.add(node);
	}

	public void removeInverseSurrogateNeighbor(final Node node) 
	{
	    inverseSurrogateNeighbors.remove(node);
	}
	
	public int getWebId()
	{
	    return webid.getValue();
	}
	
	public int getHeight()
	{
	    return webid.getHeight();
	}
	
	public int getFoldId()
	{
	    return fold == NULL_NODE ? -1 : fold.getWebId();
	}
	
	public int getSurFoldId()
	{
	    return surrogateFold == NULL_NODE ? -1 : surrogateFold.getWebId();
	}
	
	public int getInvSurFoldId()
	{
	    return inverseSurrogateFold == NULL_NODE ? -1 : inverseSurrogateFold.getWebId();
	}

    public void addUpPointer(final Node node0)
    {
        addInverseSurrogateNeighbor(node0);
        
    }
    
    public void removeUpPointer(final Node node)
    {
        removeInverseSurrogateNeighbor(node);
    }
    
    public void addDownPointer(final Node node)
    {
        addSurrogateNeighbor(node);
    }
    
    public void removeDownPointer(final Node node)
    {
        removeSurrogateNeighbor(node);
    }
    
    public HashSet<Integer> getNeighborsIds()
    {
        final HashSet<Integer> neighborsIds = new HashSet<Integer>();
        for (Node neighbor : neighbors)
        {
            neighborsIds.add(neighbor.getWebId());
        }
        
        return neighborsIds;
    }
    
    public HashSet<Integer> getSurNeighborsIds()
    {
        final HashSet<Integer> neighborsIds = new HashSet<Integer>();
        for (Node neighbor : surrogateNeighbors)
        {
            neighborsIds.add(neighbor.getWebId());
        }
        
        return neighborsIds;
    }
    
    public HashSet<Integer> getInvSurNeighborsIds()
    {
        final HashSet<Integer> neighborsIds = new HashSet<Integer>();
        for (Node neighbor : inverseSurrogateNeighbors)
        {
            neighborsIds.add(neighbor.getWebId());
        }
        
        return neighborsIds;
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
            changeState(nextNode);//this could be placed in the findCapNode methods, but its less code here. Cohesive?
            currentNode = nextNode;
            nextNode = state.findCapNode(currentNode);
            
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

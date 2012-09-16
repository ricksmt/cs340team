
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

public class Node {
    
    private WebId webid;   
    private HashSet<Node> neighbors;
    private HashSet<Node> surrogateNeighbors;
    private HashSet<Node> inverseSurrogateNeighbors;
    private Node fold;
    private Node surrogateFold;
    private Node inverseSurrogateFold;
    
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
	    HashSet<Integer> intNeighbors = new HashSet<Integer>();
	    HashSet<Integer> intSurrogateNeighbors = new HashSet<Integer>();;
	    HashSet<Integer> intInverseSurrogateNeighbors = new HashSet<Integer>();;
	    int tempFold = -1;
	    int tempSurrogateFold = -1;
	    int tempInverseSurrogateFold = -1;
	    
	    
	    //convert HashSets to integer hash sets using iteration
	    Iterator<Node> iter = neighbors.iterator();
	    while(iter.hasNext())
	    {
	        Node temp = iter.next();
	        intNeighbors.add(temp.webid.getValue());
	    }
	    
	    iter = surrogateNeighbors.iterator();
	    while(iter.hasNext())
        {
            Node temp = iter.next();
            intSurrogateNeighbors.add(temp.webid.getValue());
        }
	    
	    iter = inverseSurrogateNeighbors.iterator();
        while(iter.hasNext())
        {
            Node temp = iter.next();
            intInverseSurrogateNeighbors.add(temp.webid.getValue());
        }
        
        if(fold != NULL_NODE)
            tempFold = fold.webid.getValue();
        
        if(surrogateFold != NULL_NODE)
            tempSurrogateFold = surrogateFold.webid.getValue();
        
        if(inverseSurrogateFold != NULL_NODE)
            tempInverseSurrogateFold = inverseSurrogateFold.webid.getValue();
	    
	    SimplifiedNodeDomain simpleNode = new SimplifiedNodeDomain( webid.getValue(),
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
}

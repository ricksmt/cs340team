package dbPhase.hypeerweb;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * This class is contained in a node, and makes it easier to access information
 * about it's owners connections.
 * 
 * <pre>
 * <b>Domain:</b>
 *      neighbors                  : Set<Node>
 *      surrogateNeighbors         : Set<Node>
 *      inversesurrogateNeighbors  : Set<Node>
 *      fold                       : Node
 *      surrogateFold              : Node
 *      inverseSurrogateFold       : Node
 *      
 * @author Trevor Bentley
 */

public class CopyOfConnections
{
    /**
     * Node's neighbors
     */
    private SortedSet<Node> neighbors;
    
    /**
     * Node's surrogate neighbors
     */
    private Set<Node> surrogateNeighbors;
    
    /**
     * Node's inverse surrogate neighbors
     */
    private Set<Node> inverseSurrogateNeighbors;
    
    /**
     * Node's fold
     */
    private Node fold;
    
    /**
     * Node's surrogate fold
     */
    private Node surrogateFold;
    
    /**
     * Node's inverse surrogate fold
     */
    private Node inverseSurrogateFold;
    
    /**
     * Node Singleton
     */
    public static final Node NULL_NODE = null;
    
    /**
     * The default constructor.
     * 
     * @pre <i>None</i>
     * @post neighbors, surrogateNeighbors, and inverseSurrogateNeighbors sets are instantiated
     * fold, surrogateFold, and inverseSurrogateFold set to NULL_NODE singleton
     */
    public CopyOfConnections() 
    {
        neighbors = new TreeSet<Node>();
        surrogateNeighbors = new HashSet<Node>();
        inverseSurrogateNeighbors = new HashSet<Node>();
        fold = NULL_NODE;
        surrogateFold = NULL_NODE;
        inverseSurrogateFold = NULL_NODE;
    }
    
    /**
     * Obvious
     * @param node
     */
    public void setFold(final Node node) 
    {
        // if node WebId is fold of this.WebId
        fold = node;
    }

    /**
     * Obvious
     * @param node
     */
    public void setSurrogateFold(final Node node) 
    {
        // if node WebId is surrogate fold of this.WebId
        surrogateFold = node;
    }

    /**
     * Obvious
     * @param node
     */
    public void setInverseSurrogateFold(final Node node) 
    {
        inverseSurrogateFold = node;
    }
    
    /**
     * Obvious
     * @param node
     */
    public void addNeighbor(final Node node) 
    {
        // if WebIds are neighbors
        neighbors.add(node);
        
    }

    /**
     * Obvious
     * @param node
     */
    public void removeNeighbor(final Node node) 
    {
        neighbors.remove(node);
    }

    /**
     * Obvious
     * @param node
     */
    public void addSurrogateNeighbor(final Node node) 
    {
        // if node WebId is surrogate neighbor of
        surrogateNeighbors.add(node);
    }

    /**
     * Obvious
     * @param node
     */
    public void removeSurrogateNeighbor(final Node node) 
    {
        surrogateNeighbors.remove(node);
    }
    
    /**
     * Obvious
     * @param node
     */
    public void addInverseSurrogateNeighbor(final Node node)
    {
        // if this.WebId is surrogate neighbor of node WebId 
        inverseSurrogateNeighbors.add(node);
    }

    /**
     * Obvious
     * @param node
     */
    public void removeInverseSurrogateNeighbor(final Node node) 
    {
        inverseSurrogateNeighbors.remove(node);
    }
    
    /**
     * @return obvious
     */
    public int getFoldId()
    {
        return fold == NULL_NODE ? -1 : fold.getWebId();
    }
    
    /**
     * @return obvious
     */
    public int getSurrogateFoldId()
    {
        return surrogateFold == NULL_NODE ? -1 : surrogateFold.getWebId();
    }
    
    /**
     * @return obvious
     */
    public int getInverseSurrogateFoldId()
    {
        return inverseSurrogateFold == NULL_NODE ? -1 : inverseSurrogateFold.getWebId();
    }
    
    /**
     * @return obvious
     */
    public HashSet<Integer> getNeighborsIds()
    {
        final HashSet<Integer> neighborsIds = new HashSet<Integer>();
        for (Node neighbor : neighbors)
        {
            neighborsIds.add(neighbor.getWebId());
        }
        
        return neighborsIds;
    }
    
    /**
     * @return obvious
     */
    public HashSet<Integer> getSurrogateNeighborsIds()
    {
        final HashSet<Integer> neighborsIds = new HashSet<Integer>();
        for (Node neighbor : surrogateNeighbors)
        {
            neighborsIds.add(neighbor.getWebId());
        }
        return neighborsIds;
    }
    
    /**
     * @return obvious
     */
    public HashSet<Integer> getInverseSurrogateNeighborsIds()
    {
        
        final HashSet<Integer> neighborsIds = new HashSet<Integer>();
        for (Node neighbor : inverseSurrogateNeighbors)
        {
            neighborsIds.add(neighbor.getWebId());
        }
        return neighborsIds;
    }
    
    /**
     * @return obvious
     */
    public Set<Node> getNeighbors()
    {
        return neighbors;
    }
    
    /**
     * @return obvious
     */
    public Set<Node> getSurrogateNeighbors()
    {
        return surrogateNeighbors;
    }
    
    /**
     * @return obvious
     */
    public Set<Node> getInverseSurrogateNeighbors()
    {
        return inverseSurrogateNeighbors;
    }
    
    /**
     * @return obvious
     */
    public Node getFold() 
    {
        return fold;
    }

    /**
     * @return obvious
     */
    public Node getSurrogateFold() 
    {
        return surrogateFold;
    }

    /**
     * @return obvious
     */
    public Node getInverseSurrogateFold() 
    {
        return inverseSurrogateFold;
    }
    
    /**
     * Returns a sorted set of all neighbors
     * 
     * @pre neighbors set exists
     * @post owner is added to end of neighbors set
     * @param owner
     * @return sorted set of neighbors
     */
    private SortedSet<Node> getLargerNeighbors(Node owner) {
        return neighbors.tailSet(owner);
    }

    /**
     * Returns the largest neighbor 
     * 
     * @pre neighbors set exists
     * @post largest neighbor is found and returned
     * @return largest neighbor
     */
    public Node getHighestNeighbor() {
        return neighbors.last();
    }

    /**
     * Returns the largest surrogate neighbor
     * 
     * @pre surrogateNeighbors set exists
     * @post largest surrogate neighbor is found and returned
     * @return largest surrogate neighbor
     */
    public Node getHighestSurrogateNeighbor() {
        return ((SortedSet<Node>)surrogateNeighbors).last();
    }

    /**
     * Returns the smallest neighbor who doesn't have a child
     * 
     * @pre lists of neighbors exists
     * @post smallest neighbor without a child is found and returned
     * @return smallest neighbor without a child
     */
    public Node getLowestNeighborWithoutChild() {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Returns a Connections object will all new connections of child Node
     * 
     * @pre method is called by Parent Node
     * @post childConnections = 
     *      - neighbors = parent's inverse surrogate neighbors
     *      - surrogate neighbors = parent's neighbors that are bigger than it
     *      - fold = parent's fold or inverse surrogate fold
     * @return child's connections
     */
    public CopyOfConnections getChildConnections() {
       CopyOfConnections childConnections = new CopyOfConnections();
       //Neighbors
       childConnections.neighbors = inverseSurrogateNeighbors;
       //
       
       childConnections.surrogateNeighbors = higherNeighbors();
       
       // Fold
       if (inverseSurrogateFold == null)
       {
           childConnections.fold = fold;
       }
       else
       {
           childConnections.fold = inverseSurrogateFold;
       }
        
       return childConnections;
	}
    
    /** Parent Notify */
    /**
     * Notify all of parent Node's inverse surrogate neighbors that it is no longer their 
     * surrogate neighbor. 
     * 
     * Clears all of parent's inverse surrogate neighbors 
     * 
     * Sets parent's fold
     * 
     * @pre parent Node calls parentNotify
     * @post all inverse surrogate neighbors are notified that parentNode is no longer their 
     * surrogate neighbor
     * 
     */
    public void parentNotify(Node parentNode)//called on parent.
    {
        for (Node inverseSurrogateNeighbor : inverseSurrogateNeighbors)
        {
            inverseSurrogateNeighbor.removeDownPointer(parentNode);
        }
        inverseSurrogateNeighbors.clear();
        
        // Change parent Fold
        if(inverseSurrogateFold == NULL_NODE)
        {
            surrogateFold = fold;
            fold = NULL_NODE;
        }
        else
        {
            inverseSurrogateFold = NULL_NODE;
        }
    }
    
    /** 
     * Child node notifies all of it's new connections how it is now
     * connected to them:
     *      - Fold
     *      - Neighbors
     *      - Surrogate Neighbors 
     *      
     * @pre: childNode has all of its Connections.
     * @post: all Connections of childNode are notified of new Connection between childNode
     * and Node.
     * @param: childNode
     */
    public void childNotify(Node childNode)
	{
        // Notify fold
        if(fold.connections.fold != NULL_NODE)
        {
            fold.setInverseSurrogateFold(fold.connections.fold);
            fold.setFold(childNode);
        }
        else
        {
            fold.setFold(childNode);
            fold.setSurrogateFold(NULL_NODE);
        }
        
        fold.setFold(childNode);
        
        
        // Notify other nodes of new connection
        for (Node neighbor : neighbors)
        {
            neighbor.addNeighbor(childNode);
        }
        
        for (Node surrogateNeighbor : surrogateNeighbors)
        {
            surrogateNeighbor.addUpPointer(childNode);
        }
	}
}


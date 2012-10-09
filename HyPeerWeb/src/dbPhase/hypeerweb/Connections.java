
/**
 * Connections.java
 * 
 * @author Huy, Matthew
 */

package dbPhase.hypeerweb;

import java.util.HashSet;
import java.util.NoSuchElementException;
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
public class Connections
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
     * The default constructor.
     * 
     * @pre <i>None</i>
     * @post neighbors, surrogateNeighbors, and inverseSurrogateNeighbors sets are instantiated
     * fold, surrogateFold, and inverseSurrogateFold set to NULL_NODE singleton
     */
    public Connections() 
    {
        neighbors = new TreeSet<Node>();
        surrogateNeighbors = new HashSet<Node>();
        inverseSurrogateNeighbors = new HashSet<Node>();
        fold = Node.NULL_NODE;
        surrogateFold = Node.NULL_NODE;
        inverseSurrogateFold = Node.NULL_NODE;
    }
    
    /**
     * Set Fold
     * @param node
     */
    public void setFold(final Node node) 
    {
        // if node WebId is fold of this.WebId
        fold = node;
    }

    /**
     * Set surrogate fold
     * @param node
     */
    public void setSurrogateFold(final Node node) 
    {
        // if node WebId is surrogate fold of this.WebId
        surrogateFold = node;
    }

    /**
     * Set inverse surrogate fold
     * @param node
     */
    public void setInverseSurrogateFold(final Node node) 
    {
        inverseSurrogateFold = node;
    }
    
    /**
     * Add node to set of neighbors
     * @param node
     */
    public void addNeighbor(final Node node) 
    {
        // if WebIds are neighbors
        neighbors.add(node);
        
    }

    /**
     * Remove node from set of neighbors
     * @param node
     */
    public void removeNeighbor(final Node node) 
    {
        neighbors.remove(node);
    }

    /**
     * Add node to set of surrogate neighbors
     * @param node
     */
    public void addSurrogateNeighbor(final Node node) 
    {
        // if node WebId is surrogate neighbor of
        surrogateNeighbors.add(node);
    }

    /** 
     * Remove node from set of surrogate neighbors
     * @param node
     */
    public void removeSurrogateNeighbor(final Node node) 
    {
        surrogateNeighbors.remove(node);
    }
    
    /**
     * Add node to set of inverse surrogate neighbors
     * @param node
     */
    public void addInverseSurrogateNeighbor(final Node node)
    {
        // if this.WebId is surrogate neighbor of node WebId 
        inverseSurrogateNeighbors.add(node);
    }

    /**
     * Remove node from set of inverse surrogate neighbors
     * @param node
     */
    public void removeInverseSurrogateNeighbor(final Node node) 
    {
        inverseSurrogateNeighbors.remove(node);
    }
    
    /**
     * Get fold ID
     * @return fold id
     */
    public int getFoldId()
    {
        return fold == Node.NULL_NODE ? -1 : fold.getWebId();
    }
    
    /**
     * Get surrogate fold ID
     * @return surrogate fold id
     */
    public int getSurrogateFoldId()
    {
        return surrogateFold == Node.NULL_NODE ? -1 : surrogateFold.getWebId();
    }
    
    /**
     * Get inverse surrogate fold ID
     * @return inverse surrogate fold ID
     */
    public int getInverseSurrogateFoldId()
    {
        return inverseSurrogateFold == Node.NULL_NODE ? -1 : inverseSurrogateFold.getWebId();
    }
    
    /**
     * Get neighbor's IDs
     * @return set of neighbor's IDs
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
     * Get surrogate neighbor's IDs
     * @return set of surrogate neighbor's IDs
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
     * Get inverse surrogate neighbor's IDs
     * @return set of inverse surrogate neighbor's IDs
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
     * Get all neighbors
     * @return set of all neighbors
     */
    public Set<Node> getNeighbors()
    {
        return neighbors;
    }
    
    /**
     * Get all surrogate neighbors
     * @return set of all surrogate neighbors
     */
    public Set<Node> getSurrogateNeighbors()
    {
        return surrogateNeighbors;
    }
    
    /**
     * Get all inverse surrogate neighbors
     * @return set of all inverse surrogate neighbors
     */
    public Set<Node> getInverseSurrogateNeighbors()
    {
        return inverseSurrogateNeighbors;
    }
    
    /**
     * Get fold
     * @return fold
     */
    public Node getFold() 
    {
        return fold;
    }

    /**
     * Get surrogate fold
     * @return surrogate fold
     */
    public Node getSurrogateFold() 
    {
        return surrogateFold;
    }

    /**
     * Get inverse surrogate fold
     * @return inverse surrogate fold
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
    private static SortedSet<Node> getLargerNeighbors(final Node owner)
    {
        return new TreeSet<Node>(owner.connections.neighbors.tailSet(owner));
    }

    /**
     * Returns the largest neighbor 
     * 
     * @pre neighbors set exists
     * @post largest neighbor is found and returned
     * @return largest neighbor
     */
    public Node getHighestNeighbor()
    {
        return neighbors.last();
    }
    
    /**
     * Returns the smallest neighbor 
     * 
     * @pre neighbors set exists
     * @post smallest neighbor is found and returned
     * @return smallest neighbor
     */
    public Node getLowestNeighbor()
    {
        return neighbors.first();
    }

    /**
     * Returns the largest surrogate neighbor
     * 
     * @pre surrogateNeighbors set exists
     * @post largest surrogate neighbor is found and returned
     * @return largest surrogate neighbor
     */
    public Node getHighestSurrogateNeighbor()
    {
        return ((SortedSet<Node>)surrogateNeighbors).last();
    }

    /**
     * Returns the smallest neighbor who doesn't have a child
     * 
     * @pre lists of neighbors exists
     * @post smallest neighbor without a child is found and returned
     * @return smallest neighbor without a child
     */
    public Node getLowestNeighborWithoutChild()
    {
        try
        {
            final int size = neighbors.first().getHeight();
            for(Node neighbor: neighbors) if(neighbor.getHeight() != size) return neighbor;
            return neighbors.first();
        }
        catch(final NoSuchElementException e)
        {
            return Node.NULL_NODE;
        }
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
    public Connections getChildConnections(final Node parent)
    {
       final Connections childConnections = new Connections();
       //Neighbors
       childConnections.neighbors = new TreeSet<Node>(inverseSurrogateNeighbors);
       childConnections.addNeighbor(parent);
       
       childConnections.surrogateNeighbors = getLargerNeighbors(parent);
       
       // Fold
       if (inverseSurrogateFold == Node.NULL_NODE)
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
    public void parentNotify(final Node selfNode)//called on parent.
    {
        for (Node inverseSurrogateNeighbor : inverseSurrogateNeighbors)
        {
            inverseSurrogateNeighbor.removeDownPointer(selfNode);
        }
        inverseSurrogateNeighbors.clear();
        
        // Change parent Fold
        if(inverseSurrogateFold == Node.NULL_NODE)
        {
            surrogateFold = fold;
            fold = Node.NULL_NODE;
        }
        else
        {
            inverseSurrogateFold = Node.NULL_NODE;
        }
    }
    
    /** 
     * Child node notifies all of it's new connections how it is now
     * connected to them:
     *      - Fold
     *      - Neighbors
     *      - Surrogate Neighbors 
     * @pre childNode has all of its Connections.
     * @post all Connections of childNode are notified of new Connection between childNode
     * and Node.
     * @param childNode
     */
    public void childNotify(final Node childNode, final Node parentNode)
	{
        // Notify fold
        if(fold.connections.fold != Node.NULL_NODE)
        {
            fold.setInverseSurrogateFold(fold.connections.fold);
            fold.setFold(childNode);
        }
        else
        {
            fold.setFold(childNode);
            fold.setSurrogateFold(Node.NULL_NODE);
        }
        
        fold.setFold(childNode);
        
        
        // Notify other nodes of new connection
        for (Node neighbor : neighbors)
        {
            neighbor.addNeighbor(childNode);
            neighbor.removeDownPointer(parentNode);
        }
        
        for (Node surrogateNeighbor : surrogateNeighbors)
        {
            surrogateNeighbor.addUpPointer(childNode);
        }
	}
}


package dbPhase.hypeerweb;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/*
 * This class is contained in a node,
 * it makes easier access to information
 * about it's owners connections, such as
 * it's highest neighbor or surrogate neighbor.
 */

public class Connections
{
    private SortedSet<Node> neighbors;
    private Set<Node> surrogateNeighbors;
    private Set<Node> inverseSurrogateNeighbors;
    private Node fold;
    private Node surrogateFold;
    private Node inverseSurrogateFold;
    
    public static final Node NULL_NODE = null;
    
    public Connections() 
    {
        neighbors = new TreeSet<Node>();
        surrogateNeighbors = new HashSet<Node>();
        inverseSurrogateNeighbors = new HashSet<Node>();
        fold = NULL_NODE;
        surrogateFold = NULL_NODE;
        inverseSurrogateFold = NULL_NODE;
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
    
    public int getFoldId()
    {
        return fold == NULL_NODE ? -1 : fold.getWebId();
    }
    
    public int getSurrogateFoldId()
    {
        return surrogateFold == NULL_NODE ? -1 : surrogateFold.getWebId();
    }
    
    public int getInverseSurrogateFoldId()
    {
        return inverseSurrogateFold == NULL_NODE ? -1 : inverseSurrogateFold.getWebId();
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
    
    public HashSet<Integer> getSurrogateNeighborsIds()
    {
        final HashSet<Integer> neighborsIds = new HashSet<Integer>();
        for (Node neighbor : surrogateNeighbors)
        {
            neighborsIds.add(neighbor.getWebId());
        }
        return neighborsIds;
    }
    
    public HashSet<Integer> getInverseSurrogateNeighborsIds()
    {
        
        final HashSet<Integer> neighborsIds = new HashSet<Integer>();
        for (Node neighbor : inverseSurrogateNeighbors)
        {
            neighborsIds.add(neighbor.getWebId());
        }
        return neighborsIds;
    }
    
    public Set<Node> getNeighbors()
    {
        return neighbors;
    }
    
    public Set<Node> getSurrogateNeighbors()
    {
        return surrogateNeighbors;
    }
    
    public Set<Node> getInverseSurrogateNeighbors()
    {
        return inverseSurrogateNeighbors;
    }
    
    public Node getFold() 
    {
        return fold;
    }

    public Node getSurrogateFold() 
    {
        return surrogateFold;
    }

    public Node getInverseSurrogateFold() 
    {
        return inverseSurrogateFold;
    }
    
    
    public static Connections extractChildConnections(Node parent)
    {
        Connections conn = new Connections();
        if(conn.getInverseSurrogateFold() != Node.NULL_NODE){
            conn.setFold(conn.getInverseSurrogateFold());
            parent.setFold(Node.NULL_NODE);
        }
        else{
            conn.setFold(conn.getFold());
            parent.setSurrogateFold(parent.connections.getFold());
            parent.setFold(Node.NULL_NODE);
        }
        conn.neighbors = (SortedSet<Node>) parent.connections.getInverseSurrogateNeighbors();
        parent.connections.inverseSurrogateNeighbors.clear();
        conn.surrogateNeighbors = parent.connections.getLargerNeighbors(parent);
        return conn;
    }
    
    private SortedSet<Node> getLargerNeighbors(Node owner) {
        return neighbors.tailSet(owner);
    }

    public Node getHighestNeighbor() {
        return neighbors.last();
    }

    public Node getHighestSurrogateNeighbor() {
        return ((SortedSet<Node>)surrogateNeighbors).last();
    }

    public Node getLowestNeighborWithoutChild() {
        throw new UnsupportedOperationException();
    }
    
    public Connections getChildConnections() {
        Connections childConnections = new Connections();
      //Neighbors
       childConnections.neighbors = inverseSurrogateNeighbors;
       //
       
       childConnections.surrogateNeighbors = higherNeighbors();
       
       //Fold
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
    
    public void notifyAndRemoveInverseSurrogateNeighbors(Node selfNode)//called on parent.
    {
        for (Node inverseSurrogateNeighbor : inverseSurrogateNeighbors)
        {
            inverseSurrogateNeighbor.setSurrogateNeighbor(null);
        }
        inverseSurrogateNeighbors = null;
        
        if (inverseSurrogateFold == null)
        {
            surrogateFold = fold;
            fold = null;
            surrogateFold.addInverseSurrogateFold(selfNode);
        }
        else
        {
            inverseSurrogateFold.setSurrogateFold(null);
            inverseSurrogateFold = null;
        }
    }
    
    public void notify(Node selfNode)//called on new node
	{
        // Notify other nodes of new connection
        for (Node neighbor : neighbors)
        {
            neighbor.addNeighbor(selfNode);
        }
        
        for (Node surrogateNeighbor : surrogateNeighbors)
        {
            surrogateNeighbor.addInverseSurrogateNeighbor(selfNode);
        }
	}
}


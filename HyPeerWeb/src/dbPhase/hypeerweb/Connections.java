package dbPhase.hypeerweb;

import java.util.HashSet;

/*
 * This class is contained in a node,
 * it makes easier access to information
 * about it's owners connections, such as
 * it's highest neighbor or surrogate neighbor.
 */

public class Connections
{
    private HashSet<Node> neighbors;
    private HashSet<Node> surrogateNeighbors;
    private HashSet<Node> inverseSurrogateNeighbors;
    private Node fold;
    private Node surrogateFold;
    private Node inverseSurrogateFold;
    
    public static final Node NULL_NODE = null;
    
    public Connections() 
    {
        neighbors = new HashSet<Node>();
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
    
    public HashSet<Node> getNeighbors()
    {
        return neighbors;
    }
    
    public HashSet<Node> getSurrogateNeighbors()
    {
        return surrogateNeighbors;
    }
    
    public HashSet<Node> getInverseSurrogateNeighbors()
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
    
    /**
     * Initailizes a nodes connections that is being inserted
     * based on its parent's connections.
     * 
     * @pre parentConnections is not null, and is the connections object from the correct insertion point in a valid HyPeerWeb.
     * @post This connections object will be initailized with the data meeting the poject constraints. The nodes associated with these connections will be modified to match the project constraint.
     * 
     * @param parentConnections of the insertion point
     */
    public void initWithParentsConnections(Connections parentConnections)
    {//PROBLEM: we need some way of refrencing the parent node and the new node
        //First establish connections here
        //Neighbors
        
        neighbors = parentConnections.removeSurrogateNeighbors();
        surrogatNeighbors = parentConnectios.removeHigherNeighbors();
        /*OLD copied from Node class
        //Neighbors
        for (Node inverseSurrogateNeighbor : insertionPoint.inverseSurrogateNeighbors)
        {
            inverseSurrogateNeighbor.removeSurrogateNeighbor(insertionPoint);
            insertionPoint.removeInverseSurrogateNeighbor(inverseSurrogateNeighbor);
            inverseSurrogateNeighbor.addNeighbor(this);
        }
        
        for (Node mySurrogateNeighbor : insertionPoint.neighbors)
        {
            this.addSurrogateNeighbor(mySurrogateNeighbor);
            mySurrogateNeighbor.addInverseSurrogateNeighbor(this);
        }
        
        //Folds
        //...
         */
        
        //Second signal all the connected nodes to update their connections
    }
    
    public Node getHighestNeighbor() {
        return null;
    }

    public Node getHighestSurrogateNeighbor() {
        return null;
    }

    public Node getLowestNeighborWithoutChild() {
        return null;
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
    
    public void notifyAndRemoveInverseSurrogateNeighbors(Node selfNode)//called on parent
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


package dbPhase.hypeerweb;

import java.util.HashSet;
import java.util.Iterator;

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
    
    public Connections(final int i) 
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

    public void initWithParentsConnections(Connections connections) {
        // TODO Auto-generated method stub
        
    }

    public Node getHighestNeighbor() {
        Iterator<Node> iter = neighbors.iterator();
        Node returnNode = null;
        int curId = -1;
        
        while(iter.hasNext())
        {
            final Node temp = iter.next();
            if(temp.getWebId() > curId){
                returnNode = temp;
                curId = returnNode.getWebId();
            }
            
        }
        
        return returnNode;
    }

    public Node getHighestSurrogateNeighbor() {
        Iterator<Node> iter = surrogateNeighbors.iterator();
        Node returnNode = null;
        int curId = -1;
        
        while(iter.hasNext())
        {
            final Node temp = iter.next();
            if(temp.getWebId() > curId){
                returnNode = temp;
                curId = returnNode.getWebId();
            }
            
        }
        
        return returnNode;
    }

    public Node getLowestNeighborWithoutChild() {
        
        Iterator<Node> iter = neighbors.iterator();
        Node returnNode = null;
        int curId = -1;
        
        while(iter.hasNext())
        {
            final Node temp = iter.next();
            if((temp.getWebId() < curId || curId==-1)
                    && !temp.hasChild()){
                returnNode = temp;
                curId = returnNode.getWebId();
            }
            
        }
        
        return returnNode;
    }

    
}


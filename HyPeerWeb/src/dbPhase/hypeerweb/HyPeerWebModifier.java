package dbPhase.hypeerweb;

/*
 * This class is contained by the HyPeerWeb object.
 * It will contain the main algorithm for navigating through the nodes to the insertion point
 * and adding the new node.
 * Later this will also have delete node.
 */

public class HyPeerWebModifier
{
    /**
     * insertNode
     * Inserts the given node into the HyPeerWeb of which startNode is a part.
     * 
     * 
     * @pre newNode is not null and not already in the HyPeerWeb, startNode is a node in a valid HyPeerWeb
     * @post newNode will be added to the HyPeerWeb and its values and neighboring values will be updated to match the project constraints
     * 
     * @param newNode : the node to be inserted
     * @param startNode : the staring point in the HyPeerWeb
     * @return whether successful
     */
    public boolean insertNode(Node newNode, Node startNode)
    {
        Node insertionPoint = getInsertionPoint(startNode);
        if (insertionPoint != null)
        {
            //Brian:    I think we wanted to do this here, right?
            //          Will this class have access to all the nodes that need updated?
            addAsChild(newNode, insertionPoint);
        }
        return false;
    }
    
    /**
     * getInsertionPoint
     * Locates and returns the insertion point.
     * The algorithm locates the cap node, then traces back to the insertion point
     * (lowest neighbor with no child).
     * 
     * @pre startNode is part of a valid HyPeerWeb
     * @post HyPeerWeb is unchanged. The correct parent-to-be is returned
     * 
     * @param startNode the node from which to navigate from
     * @return the node that is the insertion point
     */
    private Node getInsertionPoint(Node startNode)
    {
        Node capNode = getCapNode(startNode);
        Node nextLowestNeighbor = capNode;
        while (nextLowestNeighbor.connections.getLowestNeighborWithoutChild() != null)
        {
            nextLowestNeighbor = nextLowestNeighbor.connections.getLowestNeighborWithoutChild();
        }
        assert nextLowestNeighbor.connections.getChildren().size() == 0;
        return nextLowestNeighbor;
        
    }
    
    //I figured this might be use later on as a method itself
    /**
     * getCapNode
     * Locates and returns the cap node.
     * The algorithm it uses first gets the origin and then steps up to its child and
     * then steps along the largest neighbor or surrogate neighbor
     * @param startNode
     * @return
     */
    private Node getCapNode(Node startNode)
    {
        Node origin = getOrigin(startNode);
        Node edgeWalk = origin.connections.getHighestChild();//isn't 10, 100, 1000... all children of 0? or am I wrong
        Node highestNeighbor;
        do
        {
            if (edgeWalk.connections.getHigherNeighbor() != null)
            {
                highestNeighbor = edgeWalk.connections.getHigherNeighbor();
                edgeWalk = highestNeighbor;
            }
            else if (edgeWalk.connections.getHigherSurrogateNeighbor() != null)
            {
                highestNeighbor = edgeWalk.connections.getHigherSurrogateNeighbor();
                edgeWalk = highestNeighbor;
            }
            else
            {
                highestNeighbor = null;
            }
        }
        while (highestNeighbor != null);
        
        return edgeWalk;
    }
    
    private void addAsChild(Node child, Node parent)
    {
        
    }
}

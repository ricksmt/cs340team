package white;

import hypeerweb.Connections;
import hypeerweb.Contents;
import hypeerweb.Node;
import hypeerweb.Parameters;
import hypeerweb.SimplifiedNodeDomain;
import hypeerweb.Visitor;
import hypeerweb.WebId;
import hypeerweb.Node.State;
import hypeerweb.HyPeerWeb;

import java.util.HashSet;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;


import junit.framework.TestCase;

public class NodeTest2 extends TestCase 
{

    Node node0;
    Node node1;
    
    @Before
    public void setUp() throws Exception 
    {
       node0 = new Node(0);
       node1 = new Node(0);
    }

    @After
    public void tearDown() throws Exception 
    {
        node0 = new Node(0);
        node1 = new Node(0);
    }
    
    public void testAddNeighbor() {
        node0.addNeighbor(node1);
        HashSet<Integer> a = node0.getNeighborsIds();
        assert(!a.isEmpty());
    }
    
    public void testRemoveNeighbor() {
        node0.removeNeighbor(node1);
        HashSet<Integer> a = node0.getNeighborsIds();
        assert(a.isEmpty());
     }

    public void testAddUpPointer() {
       node0.addUpPointer(node1);
       HashSet<Integer> a = node0.getInvSurNeighborsIds();
       assert(!a.isEmpty());
    }
    
    public void testRemoveUpPointer() {
        node0.removeUpPointer(node1);
        HashSet<Integer> a = node0.getInvSurNeighborsIds();
        assert(a.isEmpty());
     }
    
    public void testAddDownPointer() {
        node0.addUpPointer(node1);
        HashSet<Integer> a = node0.getSurNeighborsIds();
        assert(!a.isEmpty());
     }
     
     public void testRemoveDownPointer() {
         node0.removeUpPointer(node1);
         HashSet<Integer> a = node0.getSurNeighborsIds();
         assert(a.isEmpty());
      }
    
    
    public void  testConstructSimplifiedNodeDomain() 
    {
    
    }
    
    public void testInsertSelf()
    {
        Node node2 = new Node(0);
        node1.insertSelf(node0);
        assert(node0.getHighestNeighbor().compareTo(node1) == 0);
    } 
    
    public void testFindCapNode()
    {
       
    }
    
    /**
     * findInsertionPoint
     * @param startNode 
     * @pre All nodes in the HyPeerWeb are of the correct state and have the correct connections.
     *      startNode is in the HyPeerWeb
     * @post No change to HyPeerWeb.
     * @return The node which is the insertion point
     */
    private Node findInsertionPoint(Node startNode)
    {
        Node currentNode = startNode.findCapNode(startNode);
       //The cap node is now found (currentNode).
        
        do
        {
            startNode = currentNode;
            currentNode = currentNode.getLowestNeighborWithoutChild();
        }
        while(currentNode != startNode);
        return currentNode;
    }
    
    /**
     * Remove this node from the HypeerWeb
     * 
     * @pre this is part of a valid HyPeerWeb and the nodes have the correct state.
     * @post The deletionPoint node will be removed from the end of the HyPeerWeb and given this node's connections.
     *       The nodes in this node's connections are updated to point to the just moved deletionPoint node.
     *       The HyPeerWeb still meets all constraints at the end of the removal.
     */
    public void removeFromHyPeerWeb()
    {
        // find deletionPoint from this point
        final Node deletionPoint = findDeletionPoint(this);
        // Disconnect deletionPont
        deletionPoint.disconnectDeletionPoint();
        // Replace deleted node with deletionPoint node 
        if(getWebId() != deletionPoint.getWebId())
            Connections.replace(this, deletionPoint);
        // Delete node from HyPeerWeb - Garbage collection should take care of it
    }
    
    /**
     * Find the deletion point from startNode
     * @param startNode 
     * @pre All nodes in the HyPeerWeb are of the correct state and have the correct connections.
     *      startNode is in the HyPeerWeb
     * @post No change to HyPeerWeb.
     * @return The node which is the deletion point
     */
    private Node findDeletionPoint(Node startNode)
    {
        Node currentNode = startNode.state.findCapNode(startNode);
        //This loop controls the stepping of the algorithm finding the cap node
        while(currentNode != startNode)
        {
            startNode = currentNode;
            currentNode = currentNode.state.findCapNode(currentNode);
        }//The cap node is now found (currentNode).
        
        // Node 0 
        currentNode = currentNode.connections.getFold();
        
        // Loop till you get to the deletionPoint - currentNode will then be set to its highest
        // neighbor which will have a WebID less than the deletion point's WebID.
        do
        {
            startNode = currentNode;
            currentNode = currentNode.getHighestNeighbor();
            if(currentNode == Node.NULL_NODE) currentNode = startNode;
        }
        while(currentNode.getWebId() > startNode.getWebId()); 
        return startNode;
    }
    
    /**
     * Disconnect the deletion point from the HypeerWeb
     * 
     * Called from the deletionPoint node
     */
    private void disconnectDeletionPoint()
    {
        Connections.disconnect(this);
    }
    
    /**
     * @obviousNR
     * @return The lowest neighbor that does not have a child.
     */
    public Node getLowestNeighborWithoutChild()
    {
        final Node temp = connections.getLowestNeighborWithoutChild();
        if(temp == NULL_NODE) return this;
        else if(temp.compareTo(this) < 0 && temp.getHeight() <= getHeight()) return temp;
        else return this;
    }
    
    /**
     * @obvious
     * @return the fold's id
     */
    public int getFoldId()
    {
        return connections.getFoldId() ;
    }
    
    /**
     * @obvious
     * @return the surrogate fold's id
     */
    public int getSurrogateFoldId()
    {
        return connections.getSurrogateFoldId();
    }
    
    /**
     * @obvious
     * @return the inverse surrogate fold's id
     */
    public int getInverseSurrogateFoldId()
    {
        return connections.getInverseSurrogateFoldId();
    }
    
    /**
     * The accept method for the visitor Pattern.
     * 
     * @param visitor 
     * @param parameters 
     * @pre visitor ≠ null AND parameters ≠ null AND visitor.visit(this, parameters).pre-condition
     * @post visitor.visit(this, parameters).post-condition
     */
    public void accept(final Visitor visitor, final Parameters parameters)
    {
        // TODO Implement this. Who accepts? (Broadcast vs Send)
        visitor.visit(this, parameters);
    }

    @Override
    public int compareTo(final Node o)
    {
        return webid.compareTo(o.webid);
    }
    /**
     * 
     * @return
     */
    public Node getFold() {
        Node fold = connections.getFold();
        return fold;
    }
}

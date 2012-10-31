package black;

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

public class Node2 extends TestCase 
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
        node1.insertSelf(node0);
        assert(node0.getHighestNeighbor().compareTo(node1) == 0);
        
        for (int i = 2; i < 15; i++)
        { 
            Node node = new Node(i);
            node.insertSelf(node0);
        }
        assert(node0.getHighestNeighbor().getWebId() == 8);
        assert(node0.getSurrogateFoldId() == 8);
    } 
    
    public void testFindCapNode()
    {
        
    }
    
   
    public void testRemoveFromHyPeerWeb()
    {
        
    }
     
    
    public void testGetLowestNeighborWithoutChild()
    {
        
    }
    

}

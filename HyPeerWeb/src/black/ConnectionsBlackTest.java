package black;


import hypeerweb.Connections;
import hypeerweb.Node;
import hypeerweb.HyPeerWeb;
import hypeerweb.SimplifiedNodeDomain;
import java.util.ArrayList;

import junit.framework.TestCase;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConnectionsBlackTest extends TestCase {

    Connections testSet;
    ArrayList<Node> nodes;
    HyPeerWeb web;
    SimplifiedNodeDomain simple;
    
    @Before
    public void setUp() throws Exception 
    {
        testSet = new Connections();
        nodes = new ArrayList<Node>();
        // Create a list of nodes
        for(int i=0; i<100; i++)
        {
            nodes.add(new Node(i));
        }
        web = HyPeerWeb.getSingleton();
    }

    @After
    public void tearDown() throws Exception 
    {
        web.clear();
    }

    @Test
    public void testgetLowestNeighbor()
    {
        // empty connections
        assert testSet.getLowestNeighbor() == Node.NULL_NODE;
        
        // small connections
        for(int i=0; i<4; i++)
        {
            testSet.addNeighbor(nodes.get(i));
        }
        assert testSet.getLowestNeighbor() == nodes.get(0);
        
        // medium connections
        for(int i=4; i<10; i++)
        {
            testSet.addNeighbor(nodes.get(i));
        }
        assert testSet.getLowestNeighbor() == nodes.get(0);
        
        testSet = new Connections();
        
        // large connections
        for(int i=5; i<30; i++)
        {
            testSet.addNeighbor(nodes.get(i));
        }
        assert testSet.getLowestNeighbor() == nodes.get(5);
        
        for(int i=30; i<100; i++)
        {
            testSet.addNeighbor(nodes.get(i));
        }
        assert testSet.getLowestNeighbor() == nodes.get(5);
    }
    
    @Test    
    public void testgetHighestNeighbor()
    {
     // empty connections
        assert testSet.getHighestNeighbor() == Node.NULL_NODE;
        
        // small connections
        for(int i=0; i<4; i++)
            testSet.addNeighbor(nodes.get(i));
        assert testSet.getHighestNeighbor() == nodes.get(3);
        
        // medium connections
        for(int i=4; i<10; i++)
        {
            testSet.addNeighbor(nodes.get(i));
        }
        
        assert testSet.getHighestNeighbor() == nodes.get(9);
        
        testSet = new Connections();
        
        // large connections
        for(int i=5; i<30; i++)
        {
            testSet.addNeighbor(nodes.get(i));
        }
        assert testSet.getHighestNeighbor() == nodes.get(29);
        
        for(int i=30; i<100; i++)
        {
            testSet.addNeighbor(nodes.get(i));
        }
        assert testSet.getHighestNeighbor() == nodes.get(99);
    }
    
    @Test
    public void testgetLowestNeighborWithoutChild()
    {
        // empty connections - should return Null
        assert testSet.getLowestNeighborWithoutChild() == Node.NULL_NODE;
        
        // small connections - 2 nodes - 
        // NODE 1 is Cap Node
        web.addNode(nodes.get(0));
        web.addToHyPeerWeb(nodes.get(1),nodes.get(0));
        
        testSet = nodes.get(1).getConnections();
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(0);
        
        // small connections - 3 nodes - 1 less than 4 (perfect HyPeerCube)
        web.addToHyPeerWeb(nodes.get(2),nodes.get(0));
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(0);
        
        // small connections - perfect HyPeerCube
        // NODE 3 is Cap Node
        web.addToHyPeerWeb(nodes.get(3), nodes.get(0));
        testSet = nodes.get(3).getConnections();
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(1);
        
        // medium connections - 6 Nodes
        // Node 3 is Cap Node
        for(int i=4; i<6; i++)
        {
            web.addToHyPeerWeb(nodes.get(i), nodes.get(0));
        }
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(2);
        
        // medium connections - 7 nodes - 1 less than 8 (perfect HyPeerCube) 
        // Node 3 is Cap Node
        web.addToHyPeerWeb(nodes.get(6),nodes.get(0));
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(1);
        
        // large connections - 16 nodes - perfect HyPeerCube
        // Node 15 is Cap Node
        
        for(int i=7; i<16; i++)
        {
            web.addToHyPeerWeb(nodes.get(i),nodes.get(0));
        }
        testSet = nodes.get(15).getConnections();
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(7);
        
        testSet = nodes.get(7).getConnections();
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(3);
        
        testSet = nodes.get(3).getConnections();
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(1);
        
        testSet = nodes.get(1).getConnections();
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(0);
        
        // large connections - 17 nodes 
        // Node 15 is Cap Node
        web.addToHyPeerWeb(nodes.get(16), nodes.get(0));
        testSet = nodes.get(15).getConnections();
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(7);
        
        testSet = nodes.get(7).getConnections();
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(3);
        
        testSet = nodes.get(3).getConnections();
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(1);
        
        testSet = nodes.get(1).getConnections();
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(3);
        
        //  large connections - 18 nodes
        // Node 15 is Cap Node
        web.addToHyPeerWeb(nodes.get(17), nodes.get(0));
        testSet = nodes.get(15).getConnections();
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(7);
        
        testSet = nodes.get(7).getConnections();
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(3);
        
        testSet = nodes.get(3).getConnections();
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(2);
        
        // large connections - 19 nodes
        // Node 15 is Cap Node
        web.addToHyPeerWeb(nodes.get(18), nodes.get(0));
        testSet = nodes.get(15).getConnections();
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(7);
        
        testSet = nodes.get(7).getConnections();
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(3);
        
        testSet = nodes.get(3).getConnections();
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(7);
        
        // large connections - 20 nodes 
        // Node 15 is Cap Node
        web.addToHyPeerWeb(nodes.get(19), nodes.get(0));
        testSet = nodes.get(15).getConnections();
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(7);
        
        testSet = nodes.get(7).getConnections();
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(5);
        
        testSet = nodes.get(5).getConnections();
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(4);
        
        // large connections - 21 nodes
        // Node 15 is Cap Node
        web.addToHyPeerWeb(nodes.get(20), nodes.get(0));
        testSet = nodes.get(15).getConnections();
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(7);
        
        testSet = nodes.get(7).getConnections();
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(5);
        
        testSet = nodes.get(5).getConnections();
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(7);
        
        // large connections - 26 nodes
        // Node 15 is Cap Node
        for(int i=21; i<26; i++)
        {
            web.addToHyPeerWeb(nodes.get(i),nodes.get(0));
        }
        
        testSet = nodes.get(15).getConnections();
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(11);
        
        testSet = nodes.get(11).getConnections();
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(10);
        
        testSet = nodes.get(10).getConnections();
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(11);
        
        // large connections - 31 nodes 
        // Node 15 is Cap Node
        for(int i=26; i<31; i++)
        {
            web.addToHyPeerWeb(nodes.get(i), nodes.get(0));
        }
        
        testSet = nodes.get(15).getConnections();
        assert testSet.getLowestNeighborWithoutChild() == nodes.get(7);
    }
    
    /**
     * Returns a Connections object will all new connections of child Node
     * 
     * @param parent 
     * @pre method is called by Parent Node
     * @post childConnections = 
     *      - neighbors = parent's inverse surrogate neighbors
     *      - surrogate neighbors = parent's neighbors that are bigger than it
     *      - fold = parent's fold or inverse surrogate fold
     * @return child's connections
     */
    @Test
    public void testgetChildConnections()
    {
        // Small - complete the HyPeerWeb
        web.addNode(nodes.get(0));
        for(int i=1; i<3; i++)
        {
            web.addToHyPeerWeb(nodes.get(i),nodes.get(0));
        }
        testSet = nodes.get(1).getConnections();
        testSet = testSet.getChildConnections(nodes.get(1));
        
        assert testSet.getSurrogateNeighbors().isEmpty();
        assert testSet.getInverseSurrogateNeighbors().isEmpty();
        assert testSet.getNeighbors().contains(nodes.get(2));
        assert testSet.getNeighbors().contains(nodes.get(1));
        assert testSet.getNeighbors().size() == 2;
        assert testSet.getFold() == nodes.get(0);
        assert testSet.getInverseSurrogateFold() == Node.NULL_NODE;
        assert testSet.getSurrogateFold() == Node.NULL_NODE;
        
        // medium - first insert after complete HyPeerCube
        for(int i=3; i<8; i++)
        {
            web.addToHyPeerWeb(nodes.get(i),nodes.get(0));
        }
        testSet = nodes.get(0).getConnections();
        testSet = testSet.getChildConnections(nodes.get(0));
        
        assert !testSet.getSurrogateNeighbors().isEmpty();
        assert testSet.getSurrogateNeighbors().contains(nodes.get(1));
        assert testSet.getSurrogateNeighbors().contains(nodes.get(2));
        assert testSet.getSurrogateNeighbors().contains(nodes.get(4));
        assert testSet.getInverseSurrogateNeighbors().isEmpty();
        assert testSet.getNeighbors().contains(nodes.get(0));
        assert testSet.getNeighbors().size() == 1;
        assert testSet.getFold() == nodes.get(7);
        assert testSet.getInverseSurrogateFold() == Node.NULL_NODE;
        assert testSet.getSurrogateFold() == Node.NULL_NODE;
        
        // medium - middle insert
        for(int i=8; i<12; i++)
        {
            web.addToHyPeerWeb(nodes.get(i),nodes.get(0));
        }
        testSet = nodes.get(4).getConnections();
        testSet = testSet.getChildConnections(nodes.get(4));
        
        assert !testSet.getSurrogateNeighbors().isEmpty();
        assert testSet.getSurrogateNeighbors().contains(nodes.get(5));
        assert testSet.getSurrogateNeighbors().contains(nodes.get(6));
        assert testSet.getInverseSurrogateNeighbors().isEmpty();
        assert testSet.getNeighbors().contains(nodes.get(8));
        assert testSet.getNeighbors().contains(nodes.get(4));
        assert testSet.getNeighbors().size() == 2;
        assert testSet.getFold() == nodes.get(3);
        assert testSet.getInverseSurrogateFold() == Node.NULL_NODE;
        assert testSet.getSurrogateFold() == Node.NULL_NODE;
        
        // large - child will complete HyPeerCube
        for(int i=12; i<15; i++)
        {
            web.addToHyPeerWeb(nodes.get(i), nodes.get(0));
        }
        testSet = nodes.get(7).getConnections();
        testSet = testSet.getChildConnections(nodes.get(7));
        
        assert testSet.getSurrogateNeighbors().isEmpty();
        assert testSet.getInverseSurrogateNeighbors().isEmpty();
        assert testSet.getNeighbors().contains(nodes.get(14));
        assert testSet.getNeighbors().contains(nodes.get(13));
        assert testSet.getNeighbors().contains(nodes.get(11));
        assert testSet.getNeighbors().contains(nodes.get(7));
        assert testSet.getNeighbors().size() == 4;
        assert testSet.getFold() == nodes.get(0);
        assert testSet.getInverseSurrogateFold() == Node.NULL_NODE;
        assert testSet.getSurrogateFold() == Node.NULL_NODE;
    }
    
    @Test
    public void testparentNotify()
    {
        
    }
    
    @Test
    public void testchildNotify()
    {
        
    }
    
    @Test
    public void testdisconnect()
    {
        
    }
    
    @Test
    public void testreplace()
    {
        
    }
    
    @Test
    public void testgetParent()
    {
        
    }
}

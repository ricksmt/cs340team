package black;


import static org.junit.Assert.*;
import hypeerweb.Connections;
import hypeerweb.Node;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;

public class ConnectionsBlackTest {

    Connections testSet;
    ArrayList<Node> nodes;
    
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
    }

    @After
    public void tearDown() throws Exception 
    {
    }
    
    public void testgetLowestNeighbor()
    {
        // empty connections
        assert testSet.getLowestNeighbor() == Node.NULL_NODE;
        
        // small connections
        for(int i=0; i<4; i++)
            testSet.addNeighbor(nodes.get(i));
        assert testSet.getLowestNeighbor() == nodes.get(0);
        
        // medium connections
        for(int i=4; i<10; i++)
            testSet.addNeighbor(nodes.get(i));
        assert testSet.getLowestNeighbor() == nodes.get(0);
        
        testSet = new Connections();
        
        // large connections
        for(int i=5; i<30; i++)
            testSet.addNeighbor(nodes.get(i));
        assert testSet.getLowestNeighbor() == nodes.get(5);
        
    }
    
    public void testgetHighestNeighbor()
    {
        
    }
    
    public void testgetLowestNeighborWithoutChild()
    {
        // empty connections
        assert testSet.getLowestNeighborWithoutChild() == Node.NULL_NODE;
        
        // small connections
        
    }

    public void testgetChildConnections()
    {
        
    }
    
    public void testparentNotify()
    {
        
    }
    
    public void testchildNotify()
    {
        
    }
    
    public void testdisconnect()
    {
        
    }
    
    public void testreplace()
    {
        
    }
    
    public void testgetParent()
    {
        
    }
}

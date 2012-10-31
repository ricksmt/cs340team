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
    
}

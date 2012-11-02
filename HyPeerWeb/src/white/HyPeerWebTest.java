package white;

import junit.framework.TestCase;
import hypeerweb.HyPeerWeb;
import hypeerweb.HyPeerWebDatabase;
import hypeerweb.Node;

import org.junit.Before;
import org.junit.Test;

public class HyPeerWebTest extends TestCase{
    
    HyPeerWeb hypeerweb;

    @Before
    public void setUp() {
        try{
            HyPeerWebDatabase.initHyPeerWebDatabase();
            HyPeerWebDatabase.getSingleton().clear();
            HyPeerWeb.getSingleton().clear();
            HyPeerWeb.getSingleton().saveToDatabase();
            hypeerweb = HyPeerWeb.getSingleton();
        }
        catch(Exception e){
            hypeerweb = null;
        }
    }

    @Test
    public void testHyPeerWeb() {
        assertTrue(hypeerweb.size() == 0);
    }

    @Test
    public void testGetSingleton() {
        assertTrue(hypeerweb != null);
    }

    @Test
    public void testClear() {
       
        
        hypeerweb.clear();
        assertTrue(hypeerweb.size() == 0);
        hypeerweb.addNode(new Node(0));
        hypeerweb.clear();
        assertTrue(hypeerweb.size() == 0);
    }

    @Test
    public void testSize() {
        assertTrue(hypeerweb.size() == 0);
        hypeerweb.addNode(new Node(0));
        assertTrue(hypeerweb.size() == 1);
    }

    @Test
    public void testReloadString() {
        hypeerweb.reload(HyPeerWebDatabase.DEFAULT_DATABASE_NAME);
        hypeerweb.clear();
        hypeerweb.saveToDatabase();
        assertTrue(hypeerweb.size() == 0);
        hypeerweb.reload(HyPeerWebDatabase.DEFAULT_DATABASE_NAME);
        assertTrue(hypeerweb.size() == 0);
        hypeerweb.addNode(new Node(0));
        hypeerweb.saveToDatabase();
        hypeerweb.clear();
        hypeerweb.reload(HyPeerWebDatabase.DEFAULT_DATABASE_NAME);
        assertTrue(hypeerweb.size() == 1);
    }

    @Test
    public void testGetNode() {
        Node node0 = new Node(0);
        hypeerweb.addNode(node0);
        assertTrue(hypeerweb.getNode(0) == node0);
        Node node1 = new Node(1);
        hypeerweb.addNode(node1);
        assertTrue(hypeerweb.getNode(1) == node1);
        assertTrue(hypeerweb.getNode(0) == node0);
    }

    @Test
    public void testReload() {
        hypeerweb.reload();
        assertTrue(hypeerweb.size() == 0);
        hypeerweb.addNode(new Node(0));
        hypeerweb.reload();
        assertTrue(hypeerweb.size() == 0);
        hypeerweb.addNode(new Node(0));
        hypeerweb.saveToDatabase();
        hypeerweb.reload();
        assertTrue(hypeerweb.size() == 1);
    }

    @Test
    public void testSaveToDatabase() {
        
        
        hypeerweb.clear();
        hypeerweb.saveToDatabase();
        assertTrue(hypeerweb.size() == 0);
        hypeerweb.addNode(new Node(0));
        hypeerweb.saveToDatabase();
        hypeerweb.reload();
        assertTrue(hypeerweb.size() == 1);
    }

    @Test
    public void testAddNode() {
        Node node0 = new Node(0);
        hypeerweb.addNode(node0);
        assertTrue(hypeerweb.getNode(0) == node0);
        Node node1 = new Node(1);
        hypeerweb.addNode(node1);
        assertTrue(hypeerweb.getNode(1) == node1);
    }

    @Test
    public void testContains() {
        Node node0 = new Node(0);
        hypeerweb.addNode(node0);
        assertTrue(hypeerweb.contains(node0));
        Node node1 = new Node(1);
        hypeerweb.addNode(node1);
        assertTrue(hypeerweb.contains(node1));
    }

    @Test
    public void testAddToHyPeerWeb() {
        Node node0 = new Node(0);
        hypeerweb.addNode(node0);
        Node node1 = new Node(0);
        hypeerweb.addToHyPeerWeb(node1, node0);
        assertTrue(hypeerweb.size() == 2);
        assertTrue(hypeerweb.getNode(0) == node0);
        assertTrue(hypeerweb.getNode(1) == node1);
    }

    // Assert tests

    @Test
    public void testGetNodeAsserts() {
        HyPeerWeb web = HyPeerWeb.getSingleton();
        try{
            web.getNode(-1);
            fail("HyPeerWeb.getNode accepted -1 on size 0.");
        }
        catch(AssertionError e) { }
        try{
            web.getNode(0);
            fail("HyPeerWeb.getNode accepted 0 on size 0.");
        }
        catch(AssertionError e) { }
        try{
            web.getNode(1);
            fail("HyPeerWeb.getNode accepted 1 on size 0.");
        }
        catch(AssertionError e) { }
        Node node = new Node(0);
        web.addNode(node);
        assertTrue(node == web.getNode(0));
        try{
            web.getNode(-1);
            fail("HyPeerWeb.getNode accepted -1 on size 1.");
        }
        catch(AssertionError e) { }
        try{
            web.getNode(1);
            fail("HyPeerWeb.getNode accepted 1 on size 1.");
        }
        catch(AssertionError e) { }
    }

    @Test
    public void testAddNodeAsserts() {
        HyPeerWeb web = HyPeerWeb.getSingleton();
        web.addNode(new Node(0));
        try{
            web.addNode(new Node(0));
            fail("HyPeerWeb.addNode accepted 0 when 0 is in the HyPeerWeb.");
        }
        catch(AssertionError e) { }
    }

    @Test
    public void testAddToHyPeerWebAsserts() {
        HyPeerWeb web = HyPeerWeb.getSingleton();
        try{
            web.addToHyPeerWeb(null, new Node(0));
            fail("HyPeerWeb.addToHyPeerWeb accepted null for newNode. Size = 0");
        }
        catch(AssertionError e) { }
        try{
            web.addToHyPeerWeb(Node.NULL_NODE, new Node(0));
            fail("HyPeerWeb.addToHyPeerWeb accepted NULL_NODE for newNode. Size = 0");
        }
        catch(AssertionError e) { }
        web.addNode(new Node(0));
        try{
            web.addToHyPeerWeb(null, web.getNode(0));
            fail("HyPeerWeb.addToHyPeerWeb accepted null for newNode. Size = 1");
        }
        catch(AssertionError e) { }
        try{
            web.addToHyPeerWeb(Node.NULL_NODE, web.getNode(0));
            fail("HyPeerWeb.addToHyPeerWeb accepted NULL_NODE for newNode. Size = 1");
        }
        catch(AssertionError e) { }
        try{
            web.addToHyPeerWeb(new Node(-1), null);
            fail("HyPeerWeb.addToHyPeerWeb accepted null for startNode.");
        }
        catch(AssertionError e) { }
        try{
            web.addToHyPeerWeb(new Node(-1), Node.NULL_NODE);
            fail("HyPeerWeb.addToHyPeerWeb accepted NULL_NODE for startNode.");
        }
        catch(AssertionError e) { }
    }

    @Test
    public void testRemoveFromHyPeerWebIntAsserts() {
        HyPeerWeb web = HyPeerWeb.getSingleton();
        try{
            web.removeFromHyPeerWeb(-1);
            fail("HyPeerWeb.removeFromHyPeerWeb accepted -1 for id.");
        }
        catch(AssertionError e) { }
        try{
            web.removeFromHyPeerWeb(-1);
            fail("HyPeerWeb.removeFromHyPeerWeb accepted 0 for id. Size = 0");
        }
        catch(AssertionError e) { }
    }
}

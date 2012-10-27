package white;

import junit.framework.TestCase;
import hypeerweb.HyPeerWeb;
import hypeerweb.HyPeerWebDatabase;
import hypeerweb.Node;

import org.junit.Before;
import org.junit.BeforeClass;
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

}

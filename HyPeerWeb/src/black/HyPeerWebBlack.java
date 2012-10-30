package black;

import java.util.Random;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import hypeerweb.HyPeerWeb;
import hypeerweb.Node;

public class HyPeerWebBlack extends TestCase{

    private static final int MAX_SIZE = 32;

    @Before
    public void setUp() throws Exception {
        HyPeerWeb web = HyPeerWeb.getSingleton();
        web.clear();
        web.saveToDatabase();
    }

    @After
    public void tearDown() throws Exception {
        HyPeerWeb web = HyPeerWeb.getSingleton();
        web.clear();
        web.saveToDatabase();
    }

    @Test
    public void testGetSingleton() {
        HyPeerWeb web = HyPeerWeb.getSingleton();
        assertTrue(web.size() == 0);
        web.addNode(new Node(0));
        web = HyPeerWeb.getSingleton();
        assertTrue(web.size() == 1);
        web.removeFromHyPeerWeb();
        web = HyPeerWeb.getSingleton();
        assertTrue(web.size() == 1);
        web.addNode(new Node(1));
        web.clear();
        web = HyPeerWeb.getSingleton();
        assertTrue(web.size() == 1);
    }

    @Test
    public void testClear() {
        HyPeerWeb web = HyPeerWeb.getSingleton();
        assertTrue(web.size() == 0);
        web.clear();
        assertTrue(web.size() == 0);
        for(int i = 0; i < MAX_SIZE; i++){
            assertTrue(web.size() == i);
            web.addNode(new Node(i));
        }
        assertTrue(web.size() == MAX_SIZE);
        web.clear();
        assertTrue(web.size() == 0);
        for(int i = 0; i < MAX_SIZE; i++){
            assertTrue(web.size() == i);
            web.addNode(new Node(i));
        }
        assertTrue(web.size() == MAX_SIZE);
        for(int i = MAX_SIZE; web.size() >= 2; i--){// Two comes from the precondition
            assertTrue(web.size() == i);
            web.removeFromHyPeerWeb();
        }
        web.clear();
        assertTrue(web.size() == 0);
        for(Random rand = new Random(); web.size() < MAX_SIZE; web.addNode(new Node(0))){
            int size = web.size();
            if(rand.nextBoolean()){
                web.addNode(new Node(0));
                assertTrue(web.size() == size + 1);
            }
            else if(web.size() >= 2){// Two comes from the precondition.
                web.removeFromHyPeerWeb();
                assertTrue(web.size() == size - 1);
            }
        }
        web.clear();
        assertTrue(web.size() == 0);
    }

    @Test
    public void testSize() {
        HyPeerWeb web = HyPeerWeb.getSingleton();
        for(int i = 0; i < MAX_SIZE; i++){
            assertTrue(web.size() == i);
            web.addNode(new Node(i));
        }
        assertTrue(web.size() == MAX_SIZE);
        for(int i = MAX_SIZE - 1; web.size() >= 2; i--){// Two comes from the precondition
            web.removeFromHyPeerWeb();
            assertTrue(web.size() == i);
        }
        assertTrue(web.size() == 1);
        for(Random rand = new Random(); web.size() < MAX_SIZE; web.addNode(new Node(0))){
            int size = web.size();
            if(rand.nextBoolean()){
                web.addNode(new Node(0));
                assertTrue(web.size() == size + 1);
            }
            else if(web.size() >= 2){// Two comes from the precondition.
                web.removeFromHyPeerWeb();
                assertTrue(web.size() == size - 1);
            }
        }
    }

    @Test
    public void testReloadString() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetNode() {
        HyPeerWeb web = HyPeerWeb.getSingleton();
        Random rand = new Random();
        Node node = new Node(0);
        web.addNode(node);
        assertTrue(node == web.getNode(0));
        for(int i = 1; i < MAX_SIZE; i++){
            assertTrue(web.size() == i);
            node = new Node(rand.nextInt());
            web.addToHyPeerWeb(node, web.getNode(rand.nextInt(web.size())));
            assertTrue(node == web.getNode(i));
        }
        assertTrue(web.size() == MAX_SIZE);
    }

    @Test
    public void testReload() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testSaveToDatabase() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testAddNode() {
        HyPeerWeb web = HyPeerWeb.getSingleton();
        Random rand = new Random();
        for(int i = 0; i < MAX_SIZE; i++){
            assertTrue(web.size() == i);
            web.addNode(new Node(rand.nextInt()));
        }
        assertTrue(web.size() == MAX_SIZE);
    }

    @Test
    public void testContains() {
        HyPeerWeb web = HyPeerWeb.getSingleton();
        Random rand = new Random();
        for(int i = 0; i < MAX_SIZE; i++){
            assertTrue(web.size() == i);
            Node node = new Node(rand.nextInt());
            web.addNode(node);
            assertTrue(web.contains(node));
        }
        assertTrue(web.size() == MAX_SIZE);
    }

    @Test
    public void testAddToHyPeerWeb() {
        HyPeerWeb web = HyPeerWeb.getSingleton();
        Random rand = new Random();
        web.addNode(new Node(0));
        for(int i = 1; i < MAX_SIZE; i++){
            assertTrue(web.size() == i);
            web.addToHyPeerWeb(new Node(rand.nextInt()), 
                    web.getNode(rand.nextInt(web.size())));
        }
        assertTrue(web.size() == MAX_SIZE);
    }

    @Test
    public void testRemoveFromHyPeerWeb() {
        HyPeerWeb web = HyPeerWeb.getSingleton();
        Random rand = new Random();
        web.addNode(new Node(0));
        for(int i = 1; i < MAX_SIZE; i++){
            assertTrue(web.size() == i);
            web.addToHyPeerWeb(new Node(rand.nextInt()), 
                    web.getNode(rand.nextInt(web.size())));
        }
        assertTrue(web.size() == MAX_SIZE);
        for(int i = web.size() - 1; web.size() >= 2; i--){
            web.removeFromHyPeerWeb();
            assertTrue(web.size() == i);
        }
    }

    @Test
    public void testRemoveFromHyPeerWebInt() {
        fail("Not yet implemented"); // TODO
    }

}

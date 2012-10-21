package black;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hypeerweb.HyPeerWeb;
import hypeerweb.Node;

public class HyPeerWebBlack {

    private static final int MAX_SIZE = 32;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        HyPeerWeb web = HyPeerWeb.getSingleton();
        web.clear();
        web.saveToDatabase();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        HyPeerWeb web = HyPeerWeb.getSingleton();
        web.clear();
        web.saveToDatabase();
    }

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
        assertTrue(web.size() == 0);
        web.addNode(new Node(1));
        web.clear();
        web = HyPeerWeb.getSingleton();
        assertTrue(web.size() == 0);
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
        for(int i = MAX_SIZE; web.size() >= 2; i--){// Two comes from the precondition
            assertTrue(web.size() == i);
            web.removeFromHyPeerWeb();
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
        fail("Not yet implemented"); // TODO
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
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testContains() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testAddToHyPeerWeb() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testRemoveFromHyPeerWeb() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testRemoveFromHyPeerWebInt() {
        fail("Not yet implemented"); // TODO
    }

}

package black;

import junit.framework.TestCase;
import hypeerweb.Contents;
import hypeerweb.HyPeerWeb;
import hypeerweb.Node;
import hypeerweb.Parameters;
import hypeerweb.SendVisitor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SendTest extends TestCase {

    private static final int MAX_SIZE = 32;
    HyPeerWeb hypeerweb;
    
    @Before
    public void setUp() throws Exception {
        hypeerweb = HyPeerWeb.getSingleton();
        hypeerweb.clear();
    }

    @After
    public void tearDown() throws Exception {
        hypeerweb = HyPeerWeb.getSingleton();
        hypeerweb.clear();       
    }

    @Test
    public void testBroadcast() {        
        
        
        //create hyperweb with size=MAX_SIZE
        hypeerweb.clear();
        Node node0 = new Node(0);
        hypeerweb.addToHyPeerWeb(node0, null);

        for (int i = 1; i < MAX_SIZE; i++) {
            Node node = new Node(0);
            hypeerweb.addToHyPeerWeb(node, node0);
        }
        
        //send from every node in the web to different nodes
        Node sendNode;
        SendVisitor sv;
        Parameters params;
        String key = "message";
        
        for(int i = 0; i < MAX_SIZE; i++)
        {
            
            //send to node zero
            sendNode = hypeerweb.getNode(i);
            sv = new SendTestVisitor();
            String curMessage = "from node " + i + " to node 0";
            params = SendVisitor.createInitialParameters(0);
            params.set(key, curMessage);            
            sendNode.accept(sv, params);
            Contents curContents = hypeerweb.getNode(0).getContents();
            assertTrue(curContents.containsKey(key));
            String message = (String) curContents.get(key);
            assertTrue(message.equals(curMessage));    
            
            //send to node(current+1)
            if(i + 1 < MAX_SIZE){
                sendNode = hypeerweb.getNode(i);
                sv = new SendTestVisitor();
                curMessage = "from node " + i + " to node " + (i + 1);
                params = SendVisitor.createInitialParameters(i + 1);
                params.set(key, curMessage);            
                sendNode.accept(sv, params);
                curContents = hypeerweb.getNode(i + 1).getContents();
                assertTrue(curContents.containsKey(key));
                message = (String) curContents.get(key);
                assertTrue(message.equals(curMessage));    
            }
            
          //send to node(current-1)
            if(i > 0){
                sendNode = hypeerweb.getNode(i);
                sv = new SendTestVisitor();
                curMessage = "from node "+i + " to node " + (i - 1);
                params = SendVisitor.createInitialParameters(i - 1);
                params.set(key, curMessage);            
                sendNode.accept(sv, params);
                curContents = hypeerweb.getNode(i - 1).getContents();
                assertTrue(curContents.containsKey(key));
                message = (String) curContents.get(key);
                assertTrue(message.equals(curMessage));    
            }
            
          //send to the middle
            if(i != MAX_SIZE / 2){
                sendNode = hypeerweb.getNode(i);
                sv = new SendTestVisitor();
                curMessage = "from node " + i + " to node " + (MAX_SIZE / 2);
                params = SendVisitor.createInitialParameters(MAX_SIZE / 2);
                params.set(key, curMessage);            
                sendNode.accept(sv, params);
                curContents = hypeerweb.getNode(MAX_SIZE / 2).getContents();
                assertTrue(curContents.containsKey(key));
                message = (String) curContents.get(key);
                assertTrue(message.equals(curMessage));    
            }
            
          //send before the middle
            if(i != MAX_SIZE/2-1){
                sendNode = hypeerweb.getNode(i);
                sv = new SendTestVisitor();
                curMessage = "from node " + i + " to node " + (MAX_SIZE / 2 - 1);
                params = SendVisitor.createInitialParameters(MAX_SIZE / 2 - 1);
                params.set(key, curMessage);            
                sendNode.accept(sv, params);
                curContents = hypeerweb.getNode(MAX_SIZE / 2 - 1).getContents();
                assertTrue(curContents.containsKey(key));
                message = (String) curContents.get(key);
                assertTrue(message.equals(curMessage));    
            }
            
          //send to the last
            if(i != MAX_SIZE-1){
                sendNode = hypeerweb.getNode(i);
                sv = new SendTestVisitor();
                curMessage = "from node " + i + " to node " + (MAX_SIZE - 1);
                params = SendVisitor.createInitialParameters(MAX_SIZE - 1);
                params.set(key, curMessage);            
                sendNode.accept(sv, params);
                curContents = hypeerweb.getNode(MAX_SIZE - 1).getContents();
                assertTrue(curContents.containsKey(key));
                message = (String) curContents.get(key);
                assertTrue(message.equals(curMessage));    
            }
        }
    }
}

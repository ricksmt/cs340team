package dbPhase.testing;

import junit.framework.TestCase;
import dbPhase.hypeerweb.Node;

public class NodeTest extends TestCase
{
   //specifically tests that it is being inserted at the right spot. The connections class is assumed to be working.
    public void testInsertSelf()
    {
        Node firstNode = new Node(0);
        Node newNode = new Node(-1);
        newNode.insertSelf(firstNode);
        assert newNode.getWebId() == 1;
        
        Node newNode2 = new Node(-1);
        newNode2.insertSelf(firstNode);
        assert newNode.getWebId() == 2;
        
        Node newNode3 = new Node(-1);
        newNode3.insertSelf(newNode2);
        assert newNode3.getWebId() == 3;
        
        Node newNode4 = new Node(-1);
        newNode3.insertSelf(firstNode);
        assert newNode4.getWebId() == 4;
        
        Node newNode5 = new Node(-1);
        newNode3.insertSelf(newNode4);
        assert newNode5.getWebId() == 5;
        
        Node newNode6 = new Node(-1);
        newNode3.insertSelf(newNode3);
        assert newNode6.getWebId() == 6;
        
        Node newNode7 = new Node(-1);
        newNode3.insertSelf(newNode2);
        assert newNode7.getWebId() == 7;
        
        Node newNode8 = new Node(-1);
        newNode3.insertSelf(newNode6);
        assert newNode8.getWebId() == 8;
        
        Node newNode9 = new Node(-1);
        newNode9.insertSelf(firstNode);
        assert newNode9.getWebId() == 9;
    }
}

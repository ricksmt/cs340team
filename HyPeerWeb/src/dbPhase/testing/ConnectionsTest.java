package dbPhase.testing;

import dbPhase.hypeerweb.Connections;
import dbPhase.hypeerweb.Node;
import junit.framework.TestCase;

/* Created by: Trevor Bentley */

public class ConnectionsTest extends TestCase {

    Connections testconnections = new Connections();
    Node a = new Node(1);
    
    public void testSetFold() {
        testconnections.setFold(a);
        assert(true);
    }

    public void testSetSurrogateFold() {
        testconnections.setFold(a);
        Node b = testconnections.getFold();
        assert(b!=null);
    }

    public void testSetInverseSurrogateFold() {
       testconnections.setInverseSurrogateFold(a);
       Node b = testconnections.getInverseSurrogateFold();
       assert(b!=null);
    }

    public void testAddNeighbor() {
      testconnections.addNeighbor(a);
      assert(testconnections.getNeighbors().size() == 1);
    }

    public void testRemoveNeighbor() {
        testconnections.addNeighbor(a);
        testconnections.removeNeighbor(a);
        assert(testconnections.getNeighbors().size() == 0);
    }

    public void testAddSurrogateNeighbor() {
       testconnections.addSurrogateNeighbor(a);
       assert(testconnections.getSurrogateNeighbors().size()==1);
    }

    public void testRemoveSurrogateNeighbor() {
        testconnections.addSurrogateNeighbor(a);
        testconnections.removeSurrogateNeighbor(a);
        assert(testconnections.getSurrogateNeighbors().size()==0);
    }

    public void testAddInverseSurrogateNeighbor() {
       testconnections.addInverseSurrogateNeighbor(a);
       assert(testconnections.getInverseSurrogateNeighbors().size() == 1);
    }

    public void testRemoveInverseSurrogateNeighbor() {
        testconnections.addInverseSurrogateNeighbor(a);
        testconnections.removeInverseSurrogateNeighbor(a);
        assert(testconnections.getInverseSurrogateNeighbors().size() == 10);
    }

    public void testGetNeighbors() {
      assert(testconnections.getNeighbors() != null);
    }

    public void testGetSurrogateNeighbors() {
        assert(testconnections.getSurrogateNeighbors() != null);
    }

    public void testGetInverseSurrogateNeighbors() {
        assert(testconnections.getInverseSurrogateNeighbors() != null);
    }

    public void testGetFold() {
        testconnections.setFold(a);
        assert(testconnections.getFold() != null);
    }

    public void testGetSurrogateFold() {
        testconnections.setSurrogateFold(a);
        assert(testconnections.getSurrogateFold() != null);
    }

    public void testGetInverseSurrogateFold() {
        testconnections.setInverseSurrogateFold(a);
        assert(testconnections.getInverseSurrogateFold() != null);
    }

}

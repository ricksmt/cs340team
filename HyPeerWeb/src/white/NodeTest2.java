package white;

import hypeerweb.Node;

import java.util.HashSet;


import junit.framework.TestCase;

public class NodeTest2 extends TestCase {

    Node node0 = new Node(0);
    Node node1 = new Node(1);
   
    public void testAddNeighbor() {
        node0.addNeighbor(node1);
        HashSet<Integer> a = node0.getNeighborsIds();
        assert(!a.isEmpty());
    }
    
    public void testRemoveNeighbor() {
        node0.removeNeighbor(node1);
        HashSet<Integer> a = node0.getNeighborsIds();
        assert(a.isEmpty());
     }

    public void testAddUpPointer() {
       node0.addUpPointer(node1);
       HashSet<Integer> a = node0.getInvSurNeighborsIds();
       assert(!a.isEmpty());
    }
    
    public void testRemoveUpPointer() {
        node0.removeUpPointer(node1);
        HashSet<Integer> a = node0.getInvSurNeighborsIds();
        assert(a.isEmpty());
     }
}

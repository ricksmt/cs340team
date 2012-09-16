package dbPhase.hypeerweb;

import java.util.HashSet;
import java.util.Iterator;

public class HyPeerWeb {
    private HashSet<Node> nodes;
    
    HyPeerWeb()
    {
        nodes = new HashSet<Node>();
        HyPeerWebDatabase hypeerwebDB;
    }
	
    public static HyPeerWeb getSingleton() {
		// TODO Auto-generated method stub
        
		return null;
	}

	public void clear() {
		// TODO Auto-generated method stub
	    nodes.clear();
	}

	public int size() {
		// TODO Auto-generated method stub
	    return nodes.size();
	}

	public void reload(final String string) {
		// TODO Auto-generated method stub
		
	}

	public Node getNode(final int i) {
	    Node node = null;
	    Iterator<Node> it = nodes.iterator();  
	    while (it.hasNext())  
	    {
	        node = it.next();
	        if (node.getWebId().getValue() == i)
	            break;
	    }  
		// TODO Auto-generated method stub
		return node;
	}

	public void reload() {
		// TODO Auto-generated method stub
		
	}

	public void saveToDatabase() {
		// TODO Auto-generated method stub
		
	}

	public void addNode(final Node node0) {
		// TODO Auto-generated method stub
	    nodes.add(node0);
		
	}

	public boolean contains(final Node node0) {
		// TODO Auto-generated method stub
	    nodes.contains(node0);
		return false;
	}

}

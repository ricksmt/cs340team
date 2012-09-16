package dbPhase.hypeerweb;

import java.util.HashSet;

public class HyPeerWeb {
    private HashSet<Node> nodes;
    
    HyPeerWeb()
    {
        nodes = new HashSet<Node>();
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
		return 0;
	}

	public void reload(final String string) {
		// TODO Auto-generated method stub
		
	}

	public Node getNode(final int i) {
		// TODO Auto-generated method stub
		return null;
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
		return false;
	}

}

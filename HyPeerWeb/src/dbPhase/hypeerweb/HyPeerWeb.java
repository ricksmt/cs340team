package dbPhase.hypeerweb;

import java.util.HashMap;
import java.util.Iterator;

public class HyPeerWeb {
    private HashMap<Integer,Node> nodes;
    private int size;
    
    HyPeerWeb()
    {
        nodes = new HashMap<Integer,Node>();
        HyPeerWebDatabase hypeerwebDB;
        size = 0;
    }
	
    public static HyPeerWeb getSingleton() {
		// TODO Auto-generated method stub
        HyPeerWeb hyPeerWeb = new HyPeerWeb();
		return hyPeerWeb;
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
	 // TODO Auto-generated method stub
	    Node node = nodes.get(i);
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
	    nodes.put(size, node0);
		size++;
	}

	public boolean contains(final Node node0) {
		// TODO Auto-generated method stub
	    return nodes.containsValue(node0);
	}

}

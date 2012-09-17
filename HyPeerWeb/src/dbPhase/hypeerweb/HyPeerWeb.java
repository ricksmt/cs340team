package dbPhase.hypeerweb;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

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
	    return size;
	}

	public void reload(final String dbName) {
		
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
		//iterate over nodes
	          //add neights SN to db
	    HyPeerWebDatabase database = HyPeerWebDatabase.getSingleton();
	    database.clearNeighbors();
        database.clearSurNeighbors();
		for (Entry<Integer, Node> nodeEntry : nodes.entrySet())
		{
		    
		    database.saveNeighbors(node);//these are private methods now
		    database.saveSurNeighbors(node);
		}
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

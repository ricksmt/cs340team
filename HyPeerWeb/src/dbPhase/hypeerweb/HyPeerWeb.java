package dbPhase.hypeerweb;

import java.util.HashMap;
import java.util.Map.Entry;

public class HyPeerWeb {
    
    private HashMap<Integer, Node> nodes;

	public static HyPeerWeb getSingleton() {
		// TODO Auto-generated method stub
		return null;
	}

	public void clear() {
		// TODO Auto-generated method stub
		
	}

	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void reload(final String dbName) {
		
	}

	public Node getNode(final int i) {
		// TODO Auto-generated method stub
		return null;
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
		
	}

	public boolean contains(final Node node0) {
		// TODO Auto-generated method stub
		return false;
	}

}

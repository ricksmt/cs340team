package dbPhase.hypeerweb;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;



public class HyPeerWeb {
    private HashMap<Integer,Node> nodes;
    private int size;
    
    private static HyPeerWeb singleton;
    private HyPeerWebDatabase database;

    
    HyPeerWeb()
    {
        nodes = new HashMap<Integer,Node>();
        database = HyPeerWebDatabase.getSingleton();

        size = 0;
    }
	
    public static HyPeerWeb getSingleton() {
        if(singleton == null)
            singleton = new HyPeerWeb();
        return singleton;

	}

	public void clear() {
		// TODO Auto-generated method stub
	    nodes.clear();
	}

	public int size() {
		// TODO Auto-generated method stub
	    return nodes.size();
	}

	public void reload(final String string) throws ClassNotFoundException, SQLException, IOException {
	    HyPeerWebDatabase.initHyPeerWebDatabase(string);
        database = HyPeerWebDatabase.getSingleton();
        
       
        nodes.clear(); 
        nodes.putAll(database.loadNodeSet());
		
	}

	public Node getNode(final int i) {
	 // TODO Auto-generated method stub
	    Node node = nodes.get(i);
	    return node;
	}

	public void reload() throws ClassNotFoundException, SQLException, IOException {
		reload(null);
		
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

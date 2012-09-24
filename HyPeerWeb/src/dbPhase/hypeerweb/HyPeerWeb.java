package dbPhase.hypeerweb;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;


public class HyPeerWeb
{
    private HashMap<Integer,Node> nodes;
    @SuppressWarnings("unused")
    private int size;
    
    private static HyPeerWeb singleton;
    private HyPeerWebDatabase database;

    
    HyPeerWeb() throws ClassNotFoundException, SQLException
    {
        nodes = new HashMap<Integer,Node>();
        database = HyPeerWebDatabase.getSingleton();

        size = 0;
    }
	
    public static HyPeerWeb getSingleton() throws ClassNotFoundException, SQLException 
    {
        if(singleton == null)
            singleton = new HyPeerWeb();
        return singleton;

	}

	public void clear()
	{
		// TODO Auto-generated method stub
	    nodes.clear();
	}

	public int size()
	{
		// TODO Auto-generated method stub
	    return nodes.size();
	}

	public void reload(final String string) throws SQLException
	{   
	    try
	    {
            HyPeerWebDatabase.initHyPeerWebDatabase(string);
            database = HyPeerWebDatabase.getSingleton();
        }
	    catch (final ClassNotFoundException e)
	        { e.printStackTrace(); } 
	    catch (final SQLException e)
	        { e.printStackTrace(); }
	    
        nodes.clear(); 
        nodes.putAll(database.loadNodeSet());
	}

	public Node getNode(final int i)
	{
	 // TODO Auto-generated method stub
	    final Node node = nodes.get(i);
	    return node;
	}

	public void reload() throws ClassNotFoundException, SQLException, IOException
	{
		reload(null);	
	}

	public void saveToDatabase() throws SQLException
	{
	    database.save(nodes.values());
	}

	public void addNode(final Node node0)
	{
		// TODO Auto-generated method stub
	    nodes.put(node0.getWebId(), node0);
	    //nodes.put(size, node0);
		//size++;
	}

	public boolean contains(final Node node0)
	{
		// TODO Auto-generated method stub
	    return nodes.containsValue(node0);
	}
	
	/**
	 * 
	 * @param newNode
	 * @param startNode
	 */
	public void addToHyPeerWeb(Node newNode, Node startNode)
	{
	    assert newNode != null;
	    if ( nodes.size()> 0)
	    {
	        assert startNode != null;
	        //TODO
	    }
	    else
	    {
	        //TODO
	    }
	}
}

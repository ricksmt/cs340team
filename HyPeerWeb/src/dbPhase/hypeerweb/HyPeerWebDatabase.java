package dbPhase.hypeerweb;

import java.sql.*;
import java.util.*;

public class HyPeerWebDatabase {

    public static String DATABASE_DIRECTORY;
    public static String DEFAULT_DATABASE_NAME = "HyPeerWeb.db";
    private static HyPeerWebDatabase singleton = null;
    private static Connection connection;


    
    private HyPeerWebDatabase(String dbName) throws ClassNotFoundException, SQLException{
        initHyPeerWebDatabase(dbName);
    }


    public static HyPeerWebDatabase getSingleton() throws ClassNotFoundException, SQLException{
        if (singleton == null)
                singleton = new HyPeerWebDatabase(DEFAULT_DATABASE_NAME);
        return singleton;
    }

    
    public static void initHyPeerWebDatabase(java.lang.String dbName) throws SQLException, ClassNotFoundException{
        
        if (connection != null)
        {    
            try 
            {
                connection.close();
            } 
            catch (SQLException e) {
                e.printStackTrace();
            }
        }    
        
        if (dbName==null)
            dbName=DEFAULT_DATABASE_NAME;
        
        
        Class.forName("org.sqlite.JDBC");
        
        
        try 
        {
            connection = DriverManager.getConnection("jdbc:sqlite:"+dbName);
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        
        createTables();

    }
    
    
    
    public void save(Collection<Node> nodes) throws SQLException
    {
            
            dropTables();
            createTables();
        
            for (Node node : nodes)
            {
                    saveNode(node);
                    saveNeighbors(node);
                    saveSurNeighbors(node);
            }
            
    }
    
    private static void createTables() throws SQLException
    {
            Statement createTables = connection.createStatement();
            createTables.addBatch(
                            "CREATE TABLE IF NOT EXISTS Nodes (" +
                            "WebId int primary key not null unique," +
                            "Height int not null," +
                            "Fold int," +
                            "SurFold int," +
                            "InvSurFold int)"
            );
            createTables.addBatch(
                    "CREATE TABLE IF NOT EXISTS Neighbors (" +
                    "Id int primary key asc," +
                    "Node int not null," +
                    "Neighbor int not null)"
            );
            createTables.addBatch(
                            "CREATE TABLE IF NOT EXISTS SurNeighbors (" +
                            "Id int primary key asc," +
                            "InvSurNeighbor int not null," +
                            "SurNeighbor int not null)"
            );
            
            createTables.executeBatch();
            createTables.close();
           
            
    }
    
    private void dropTables() throws SQLException{
        
        Statement dropTables = connection.createStatement();
        dropTables.addBatch("DROP TABLE IF EXISTS Nodes");
        dropTables.addBatch("DROP TABLE IF EXISTS SurNeighbors");
        dropTables.addBatch("DROP TABLE IF EXISTS Neighbors");
        dropTables.executeBatch();
        dropTables.close();
        
    }
    
    public HashMap<Integer,Node> loadNodeSet() throws SQLException
    {
            HashMap<Integer,Node> nodes = new HashMap<Integer,Node>();
            Statement stat = connection.createStatement();
            
            //load the nodes
            ResultSet rs = stat.executeQuery("select WebId from Nodes;");
            while (rs.next()){
                int webId = rs.getInt("WebId");
                Node newNode = new Node(webId);
                nodes.put(webId, newNode);                               
            }
                    
            rs.close();
            Set<Integer> keySet = nodes.keySet();
            Node currNode;
            for(int id: keySet){
                
                currNode = nodes.get(id);
                PreparedStatement nodeStat = connection.prepareStatement("SELECT * FROM Nodes WHERE WebId = ?");
                nodeStat.setInt(1, id);
                ResultSet nodeSet = nodeStat.executeQuery();
                
                if(nodeSet.next()){                    
                    if(nodeSet.getInt("Fold")>=0) currNode.setFold(nodes.get(nodeSet.getInt("Fold")));
                    if(nodeSet.getInt("InvSurFold")>=0) currNode.setInverseSurrogateFold(nodes.get(nodeSet.getInt("InvSurFold")));
                    if(nodeSet.getInt("SurFold")>=0) currNode.setSurrogateFold(nodes.get(nodeSet.getInt("SurFold")));
                }
                
                HashSet<Integer> neighbors = loadNeighbors(id);
                for(int neighborId: neighbors)
                    currNode.addNeighbor(nodes.get(neighborId));
                
                HashSet<Integer> surNeighbors = loadSurNeighbors(id);
                for(int neighborId: surNeighbors)
                    currNode.addSurrogateNeighbor(nodes.get(neighborId));
                
                HashSet<Integer> invSurNeighbors = loadInvSurNeighbors(id);
                for(int neighborId: invSurNeighbors)
                    currNode.addInverseSurrogateNeighbor(nodes.get(neighborId));           
            
            }       
            
            return nodes;
    }

    
    public SimplifiedNodeDomain getNode(int webId) throws SQLException {
        
        PreparedStatement getNode = connection.prepareStatement("SELECT * FROM Nodes WHERE WebId = ?");
        getNode.setInt(1, webId);
        ResultSet nodeSet = getNode.executeQuery();
        SimplifiedNodeDomain nodeToReturn = null;
        
        if (nodeSet.next()){
            webId = nodeSet.getInt("WebId");
            int height = nodeSet.getInt("Height");           
            int fold = nodeSet.getInt("Fold");
            int surFold = nodeSet.getInt("SurFold");
            int invSurFold = nodeSet.getInt("InvSurFold");

            HashSet<Integer> neighbors = loadNeighbors(webId);
            HashSet<Integer> surNeighbors = loadSurNeighbors(webId);
            HashSet<Integer> invSurNeighbors = loadInvSurNeighbors(webId);
            
            nodeToReturn = new SimplifiedNodeDomain(webId, height, neighbors, 
                    invSurNeighbors, surNeighbors, fold, surFold, invSurFold);

        }
        getNode.close(); // also closes the ResultSet
        return nodeToReturn;

    }
    
    private HashSet<Integer> loadNeighbors(int webId) throws SQLException {
            PreparedStatement loadNeighbors = connection.prepareStatement("SELECT Neighbor FROM Neighbors WHERE Node = ?");
            loadNeighbors.setInt(1, webId);
            ResultSet neighborsSet = loadNeighbors.executeQuery();
            HashSet<Integer> neighbors = new HashSet<Integer>();
           
            while (neighborsSet.next())
                    neighbors.add(neighborsSet.getInt("Neighbor"));
           
            loadNeighbors.close();
            return neighbors;
    }
    
    private HashSet<Integer> loadSurNeighbors(int webId) throws SQLException {
        PreparedStatement loadSurNeighbors = connection.prepareStatement("SELECT SurNeighbor FROM SurNeighbors WHERE InvSurNeighbor = ?");
        loadSurNeighbors.setInt(1, webId);
        ResultSet surNeighborsSet = loadSurNeighbors.executeQuery();
        HashSet<Integer> surNeighbors = new HashSet<Integer>();
       
        while (surNeighborsSet.next())
                surNeighbors.add(surNeighborsSet.getInt("SurNeighbor"));
       
        loadSurNeighbors.close();
        return surNeighbors;
    }
    
    private HashSet<Integer> loadInvSurNeighbors(int webId) throws SQLException {
        PreparedStatement loadInvSurNeighbors = connection.prepareStatement("SELECT InvSurNeighbor FROM SurNeighbors WHERE SurNeighbor = ?");
        loadInvSurNeighbors.setInt(1, webId);
        ResultSet invSurNeighborsSet = loadInvSurNeighbors.executeQuery();
        HashSet<Integer> invSurNeighbors = new HashSet<Integer>();
       
        while (invSurNeighborsSet.next())
            invSurNeighbors.add(invSurNeighborsSet.getInt("InvSurNeighbor"));
       
        loadInvSurNeighbors.close();
        return invSurNeighbors;
    }

    
    private void saveNode(Node node) throws SQLException{
        PreparedStatement saveNode = connection.prepareStatement("INSERT INTO Nodes VALUES (?, ?, ?, ?, ?)");
        
        saveNode.setInt(1, node.getWebId()); //change when node class is ready
        saveNode.setInt(2, node.getHeight());
        saveNode.setInt(3, node.getFoldId());
        saveNode.setInt(4, node.getSurFoldId());
        saveNode.setInt(5, node.getInvSurFoldId());
        saveNode.addBatch();
        
        connection.setAutoCommit(false);
        saveNode.executeBatch();
        connection.setAutoCommit(true);
        saveNode.close();

    }
    
    private void saveNeighbors(Node node) throws SQLException
    {
            PreparedStatement saveNeighbors = connection.prepareStatement("INSERT INTO Neighbors(Node, Neighbor) VALUES (?, ?)");
          //change when node class is ready
            int webId = node.getWebId();
            
            for (int neighborId : node.getNeighborsIds())
            {
                saveNeighbors.setInt(1, webId);
                saveNeighbors.setInt(2, neighborId);
                saveNeighbors.addBatch();
            }
            connection.setAutoCommit(false);
            saveNeighbors.executeBatch();
            connection.setAutoCommit(true);
            saveNeighbors.close();
        
    }
    
    private void saveSurNeighbors(Node node) throws SQLException
    {
        
            PreparedStatement saveSurNeighbors = connection.prepareStatement(
                    "INSERT INTO SurNeighbors(InvSurNeighbor, SurNeighbor) VALUES (?, ?)");
          //change when node class is ready
            int webId = node.getWebId();
            
            for (int surNeighborId : node.getSurNeighborsIds())
            {
                saveSurNeighbors.setInt(1, webId);
                saveSurNeighbors.setInt(2, surNeighborId);
                saveSurNeighbors.addBatch();
            }
            connection.setAutoCommit(false);
            saveSurNeighbors.executeBatch();
            connection.setAutoCommit(true);
            saveSurNeighbors.close();
        
    }
    
    


}

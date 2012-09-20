import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Creates an empty database for use in the HyPeerWeb
 * 
 * @author Jared
 * 
 */
public class DatabaseCreator {
	/**
	 * Creates an empty database
	 * 
	 * @param dbName
	 *            The name of the database to be created
	 * @throws Exception
	 * 
	 * @pre dbName != null
	 * @post dbName exists as a database with Node, Neighbor, and
	 *       SurrogateNeighbor tables
	 */
	public static void createDatabase(String dbName) throws Exception {
		assert dbName != null;
		// Creates and populates a database
		Class.forName("org.sqlite.JDBC");
		// Given path indicates table created one level above project folder
		// (repo root)
		Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbName);
		Statement stat = conn.createStatement();
		createNodeTable(stat);
		createNeighborTable(stat);
		createSurrogateNeighborTable(stat);
		conn.close();
	}

	/**
	 * Creates the Node table in the database
	 * 
	 * @param stat
	 *            The statement being used to access the database
	 * @throws SQLException
	 * 
	 * @pre stat != null
	 * @post the database contains a new table called Node
	 */
	private static void createNodeTable(Statement stat) throws SQLException {
		stat.executeUpdate("drop table if exists Node;");
		stat.executeUpdate("create table Node(" + "WebId INT PRIMARY KEY,"
				+ "Height INT," + "Fold INT," + "SurrogateFold INT);");
	}

	/**
	 * Creates the Neighbor table in the database
	 * 
	 * @param stat
	 *            The statement being used to access the database
	 * @throws SQLException
	 * 
	 * @pre stat != null
	 * @post the database contains a new table called Neighbor
	 */
	private static void createNeighborTable(Statement stat) throws SQLException {
		// Create neighbor table
		stat.executeUpdate("drop table if exists Neighbor;");
		stat.executeUpdate("create table Neighbor(" + "Node1 INT,"
				+ "Node2 INT," + "FOREIGN KEY(Node1) REFERENCES Node(WebId),"
				+ "FOREIGN KEY(Node2) REFERENCES Node(WebId));");
	}

	/**
	 * Creates the SurrogateNeighbor table in the database
	 * 
	 * @param stat
	 *            The statement being used to access the database
	 * @throws SQLException
	 * 
	 * @pre stat != null
	 * @post the database contains a new table called SurrogateNeighbor
	 */
	private static void createSurrogateNeighborTable(Statement stat)
			throws SQLException {
		// Create neighbor table
		stat.executeUpdate("drop table if exists SurrogateNeighbor;");
		stat.executeUpdate("create table SurrogateNeighbor(" + "Node1 INT,"
				+ "Node2 INT," + "FOREIGN KEY(Node1) REFERENCES Node(WebId),"
				+ "FOREIGN KEY(Node2) REFERENCES Node(WebId));");
	}
}

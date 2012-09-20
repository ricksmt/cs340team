import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

/**
 * The HyPeerWeb Database Domain: DATABASE_DIRECTORY : String
 * DEFAULT_DATABASE_NAME : String HyPeerWebDatabase : String dbName : String
 * 
 * @author Brian
 * 
 */
public class HyPeerWebDatabase {
	/**
	 * the database directory
	 */
	public static String DATABASE_DIRECTORY = "db";
	/**
	 * the default database name
	 */
	public static String DEFAULT_DATABASE_NAME = "HyPeerWeb.db";
	/**
	 * the singleton
	 */
	private static HyPeerWebDatabase singleton;
	/**
	 * the database name
	 */
	private String dbName;

	/**
	 * This makes a new database with the given name.
	 * 
	 * @param String
	 *            dbName
	 */
	private HyPeerWebDatabase(String dbName) {
		assert dbName != null;
		this.dbName = dbName;
	}

	/**
	 * This initializes the database in the singleton.
	 * 
	 * @param String
	 *            dbName
	 */
	static void initHyPeerWebDatabase(String dbName) {
		if (dbName == null) {
			singleton = new HyPeerWebDatabase(DEFAULT_DATABASE_NAME);
		} else {
			singleton = new HyPeerWebDatabase(dbName);
		}
	}

	/**
	 * Gets the single HyPeerWebDatabase.
	 * 
	 * @return HyPeerWebDatabase
	 */
	public static HyPeerWebDatabase getSingleton() {
		assert singleton != null;
		return singleton;
	}

	/**
	 * Gets the connection to the database
	 * 
	 * @return Connection
	 */
	public Connection getConnection() {
		Connection conn = null;

		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return conn;
	}

	/**
	 * Attempts to close the given connection
	 * 
	 * @param Connection
	 *            conn
	 */
	private void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Retrieves all nodes in the database
	 * 
	 * @return Iterable<SimplifiedNodeDomain>
	 */
	public Iterable<SimplifiedNodeDomain> getNodes() {
		HashSet<SimplifiedNodeDomain> simpNodes = new HashSet<SimplifiedNodeDomain>();
		HashSet<Integer> webIds = new HashSet<Integer>();
		Connection conn = getConnection();

		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("SELECT webid FROM node;");

			while (rs.next()) {
				webIds.add(rs.getInt("webid"));
			}
		} catch (Exception ex) {
			// System.out.println("Error accessing database: " +
			// ex.getMessage());
		} finally {
			closeConnection(conn);
		}

		for (int webId : webIds) {
			simpNodes.add(getNode(webId));
		}

		return simpNodes;
	}

	/**
	 * Creates a SimplifiedNodeDomain representing the node with indicated webId
	 * 
	 * @param int webId
	 * @return SimplifiedNodeDomain
	 */
	public SimplifiedNodeDomain getNode(int webId) {
		assert webId >= 0;
		int height, fold, surrogateFold, inverseSurrogateFold;
		HashSet<Integer> neighbors, upPointers, downPointers;

		height = 0;
		fold = -1;
		surrogateFold = -1;
		inverseSurrogateFold = -1;

		neighbors = new HashSet<Integer>();
		upPointers = new HashSet<Integer>();
		downPointers = new HashSet<Integer>();

		Connection conn = getConnection();

		try {
			Statement stat = conn.createStatement();

			// query db for height, fold, and surrogateFold
			ResultSet resultNode = stat
					.executeQuery("SELECT * FROM node WHERE webid = " + webId
							+ ";");
			if (resultNode.next()) {
				height = resultNode.getInt("height");
				fold = resultNode.getInt("fold");
				surrogateFold = resultNode.getInt("surrogateFold");

				// query db for inverseSurrogateFold
				ResultSet resultInverseSurrogateFoldNode = stat
						.executeQuery("SELECT * FROM node WHERE surrogateFold = "
								+ webId + ";");
				if (resultInverseSurrogateFoldNode.next()) {
					inverseSurrogateFold = resultInverseSurrogateFoldNode
							.getInt("webId");
				}

				neighbors = getNeighbors(webId, stat);

				downPointers = getSurrogateNeighbors(webId, stat);

				upPointers = getInverseSurrogateNeighbors(webId, stat);
			} else {
				System.out.println("Node " + webId + " not found.");
			}
		} catch (Exception ex) {
			// System.out.println("Error accessing database: " +
			// ex.getMessage());
		} finally {
			closeConnection(conn);
		}

		SimplifiedNodeDomain toReturn = new SimplifiedNodeDomain(webId, height,
				neighbors, upPointers, downPointers, fold, surrogateFold,
				inverseSurrogateFold);
		return toReturn;
	}

	/**
	 * Query Database for neighbors in both directions.
	 * 
	 * @param int webId
	 * @param Statement
	 *            stat
	 * @return HashSet<Ingeger>
	 * @throws SQLException
	 */
	private HashSet<Integer> getNeighbors(int webId, Statement stat)
			throws SQLException {
		assert webId >= 0 && stat != null;

		HashSet<Integer> neighbors = new HashSet<Integer>();

		ResultSet resultNeighbors = stat
				.executeQuery("SELECT * FROM neighbor WHERE node1 = " + webId);
		while (resultNeighbors.next()) {
			neighbors.add(resultNeighbors.getInt("node2"));
		}

		resultNeighbors = stat
				.executeQuery("SELECT * FROM neighbor WHERE node2 = " + webId);
		while (resultNeighbors.next()) {
			neighbors.add(resultNeighbors.getInt("node1"));
		}

		return neighbors;
	}

	/**
	 * Query the database for surrogate neighbors
	 * 
	 * @param int webId
	 * @param Statement
	 *            stat
	 * @return HashSet<Integer>
	 * @throws SQLException
	 */
	private HashSet<Integer> getSurrogateNeighbors(int webId, Statement stat)
			throws SQLException {
		assert webId >= 0 && stat != null;

		HashSet<Integer> surrogateNeighbors = new HashSet<Integer>();

		ResultSet resultSurrogateNeighbors = stat
				.executeQuery("SELECT * FROM surrogateNeighbor WHERE node1 = "
						+ webId);
		while (resultSurrogateNeighbors.next()) {
			surrogateNeighbors.add(resultSurrogateNeighbors.getInt("node2"));
		}
		return surrogateNeighbors;
	}

	/**
	 * Query the Database for inverse surrogate neighbors
	 * 
	 * @param int webId
	 * @param Statement
	 *            stat
	 * @return HashSet<Integer>
	 * @throws SQLException
	 */
	private HashSet<Integer> getInverseSurrogateNeighbors(int webId,
			Statement stat) throws SQLException {
		assert webId >= 0 && stat != null;

		HashSet<Integer> inverseSurrogateNeighbors = new HashSet<Integer>();

		ResultSet resultInverseSurrogateNeighbors = stat
				.executeQuery("SELECT * FROM surrogateNeighbor WHERE node2 = "
						+ webId);
		while (resultInverseSurrogateNeighbors.next()) {
			inverseSurrogateNeighbors.add(resultInverseSurrogateNeighbors
					.getInt("node1"));
		}
		return inverseSurrogateNeighbors;
	}

	/**
	 * Resets the database with the given nodes
	 * 
	 * @param Iterable
	 *            <Node> nodes
	 * @throws Exception
	 */
	void resetDatabase(Iterable<Node> nodes) throws Exception {
		Connection conn = getConnection();

		try {
			Statement stat = conn.createStatement();
			stat.execute("DELETE FROM node;");
			stat.execute("DELETE FROM neighbor;");
			stat.execute("DELETE FROM surrogateNeighbor;");

			for (Node n : nodes) {
				stat.execute("INSERT INTO node VALUES ("
						+ n.getWebId().getValue() + "," + n.getHeight() + ","
						+ n.getFold().getWebId().getValue() + ","
						+ n.getSurrogateFold().getWebId().getValue() + ");");
				for (Node neighbor : n.getNeighbors()) {
					stat.execute("INSERT INTO neighbor VALUES ("
							+ n.getWebId().getValue() + ","
							+ neighbor.getWebId().getValue() + ");");
				}
				for (Node surrogateNeighbor : n.getDownPointers()) {
					stat.execute("INSERT INTO surrogateNeighbor VALUES ("
							+ n.getWebId().getValue() + ","
							+ surrogateNeighbor.getWebId().getValue() + ");");
				}
			}
		} catch (Exception ex) {
			// System.out.println("Error accessing database: " +
			// ex.getMessage());
		} finally {
			closeConnection(conn);
		}
	}
}

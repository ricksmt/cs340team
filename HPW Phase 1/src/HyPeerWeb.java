import java.util.HashSet;

/**
 * The object model of the HyPeerWeb, implemented as a singleton
 * 
 * Domain: nodes : HashSet<Node>
 * 
 * @author Thomas
 * 
 */
public class HyPeerWeb {

	/**
	 * The single instance of this class
	 */
	private static HyPeerWeb singleton;

	/**
	 * The set of all Nodes in the HyPeerWeb
	 */
	private HashSet<Node> nodes;

	/**
	 * The private constructor, used to generate the singleton.
	 * 
	 * @pre <i>None</i>
	 * @post nodes != null AND the HyPeerWeb is an accurate reflection of the
	 *       objects in the database
	 */
	private HyPeerWeb() {
		nodes = new HashSet<Node>();
		reload();
	}

	/**
	 * Addes a Node to the HyPeerWeb
	 * 
	 * @param node
	 *            The Node to be added
	 * 
	 * @pre node != null
	 * @post node is a member of nodes
	 */
	public void addNode(Node node) {
		assert node != null;
		nodes.add(node);
		saveToDatabase();
	}

	/**
	 * Clears the HyPeerWeb
	 * 
	 * @pre none
	 * @post nodes.size() == 0
	 */
	public void clear() {
		nodes.clear();
		saveToDatabase();
	}

	/**
	 * Determines whether the indicated node is in the HyPeerWeb.
	 * 
	 * @param node
	 *            The indicated Node
	 * @return True if node is contained in nodes, false otherwise
	 * 
	 * @pre node != null
	 * @post The correct value is returned
	 */
	public boolean contains(Node node) {
		return nodes.contains(node);
	}

	/**
	 * Returns the single HyPeerWeb Database.
	 * 
	 * @return The singleton HyPeerWeb
	 * 
	 * @pre none
	 * @post result == HyPeerWeb.singleton
	 */
	public HyPeerWebDatabase getHyPeerWebDatabase() {
		return HyPeerWebDatabase.getSingleton();
	}

	/**
	 * Gets the node with the id i.
	 * 
	 * @param i
	 *            The id of the Node you are retrieving
	 * @return The Node whose id == i
	 * 
	 * @pre i >= 0
	 * @post The Node whose id == i if it exists, otherwise null
	 */
	public Node getNode(int i) {
		assert i >= -1;
		for (Node node : nodes) {
			if (node.getWebId().getValue() == i) {
				return node;
			}
		}
		return null;
	}

	/**
	 * Gets the single HyPeerWeb, if one doesn't exist then one is created.
	 * 
	 * @return HyPeerWeb.singleton
	 * 
	 * @pre none
	 * @post HypeerWeb.singleton != null
	 */
	public static HyPeerWeb getSingleton() {
		if (singleton == null) {
			singleton = new HyPeerWeb();
		}
		return singleton;
	}

	/**
	 * Reloads the HyPeerWeb from the database using the default database name.
	 * 
	 * @pre preconditions of reload(String dbName)
	 * @post postconditions of reload(String dbName)
	 * 
	 * @author Jared
	 */
	public void reload() {
		reload(null);
	}

	/**
	 * Reloads the HyPeerWeb from the database using the default dbName. If the
	 * dbName is null then the results are loaded from the default database.
	 * 
	 * @param dbName
	 *            The name of the database to be read, or null if the default
	 *            name is to be used
	 * 
	 * @pre none
	 * @post The HyPeerWeb is an accurate representation of the contents of the
	 *       database entitled dbName, or the default database if dbname == null
	 * 
	 * @author Jared
	 */
	public void reload(String dbName) {
		HyPeerWebDatabase.initHyPeerWebDatabase(dbName);

		Iterable<SimplifiedNodeDomain> simpNodes = HyPeerWebDatabase
				.getSingleton().getNodes();

		// Populate nodes
		for (SimplifiedNodeDomain simpNode : simpNodes) {
			nodes.add(new Node(simpNode.getWebId(), simpNode.getHeight()));
		}

		// Establish associations
		for (SimplifiedNodeDomain simpNode : simpNodes) {
			Node node = getNode(simpNode.getWebId());
			node.setFold(getNode(simpNode.getFold()));
			node.setSurrogateFold(getNode(simpNode.getSurrogateFold()));

			for (int neighbor : simpNode.getNeighbors()) {
				node.addNeighbor(getNode(neighbor));
			}

			for (int downPointer : simpNode.getDownPointers()) {
				node.addDownPointer(getNode(downPointer));
			}

			for (int upPointer : simpNode.getUpPointers()) {
				node.addUpPointer(getNode(upPointer));
			}
		}
	}

	/**
	 * Removes a node from the HyPeerWeb
	 * 
	 * @param node
	 *            The Node to be removed
	 * 
	 * @pre node != null
	 * @post nodes does not contain node
	 */
	public void removeNode(Node node) {
		assert node != null;
		nodes.remove(node);
		saveToDatabase();
	}

	/**
	 * Saves all the nodes in the HyPeerWeb and their corresponding information
	 * to the database.
	 * 
	 * @pre none
	 * @post The database contains only nodes and information contained in the
	 *       HyPeerWeb
	 */
	public void saveToDatabase() {
		try {
			HyPeerWebDatabase.getSingleton().resetDatabase(nodes);
		} catch (Exception ex) {
			// System.out.println("Error saving to database: " +
			// ex.getMessage());
		}
	}

	/**
	 * Gets the number of Nodes in the HyPeerWeb
	 * 
	 * @return The number of Nodes in the HyPeerWeb
	 * 
	 * @pre none
	 * @post result == nodes.size()
	 */
	public int size() {
		return nodes.size();
	}
}
import java.util.HashSet;

/**
 * A node in the HyPeerWeb.
 * 
 * Domain: webId : WebId neighbors : SetOf<Node> upPointers : SetOf<Node>
 * downPointers : SetOf<Node> fold : Node surrogateFold : Node
 * inverseSurrogateFold : Node;
 * 
 * ValidNodeRequirement: webId.value == -1 AND webId.value = -1 ? (webId.height
 * = -1 AND |neighbors| = 0 AND |upPointers| = 0 AND |downPointers| = 0 AND fold
 * = NULL_NODE AND surrogateFole = NULL_NODE ) AND webId.value = 0 ? (? Node(n)
 * (n ? neighbors ? webId.distanceTo(n.webId) = 1) AND ? Node(n) (n ? upPointers
 * ? n.parent.webId + 1 = webId) AND ? Node(n) (n ? downPointers ? parent.webId
 * + 1 = n) AND |neighbors| + |downPointers| = height AND --Constraint on the
 * fold (height = fold.height ? (~(-1 << height) & id) = (~(-1 << fold.height) &
 * ~fold.id) AND
 * 
 * height + 1 = fold.height + 1 ? (~(-1 << height) & id) = (~(-1 << height) &
 * ~fold) AND
 * 
 * height = fold.height + 1 ? (~(-1 << fold.height) & id) = (~(-1 <<
 * fold.height) & ~fold.fold) AND
 * 
 * NOT (height ? fold.height && height + 1 ? fold.height && height &ne fold.id +
 * 1) ) AND --Constraint on the surrogateFold (surrogateFold = NULL_NODE OR
 * (height + 1 = surrogateFold.height AND id < surrogateFold.id AND (~(-1 <<
 * height) & surrogateFold.id = ~(~(-1 << height) & id) ) ) AND --Constraint on
 * the InverseSurrogateFold (inverseSurrogateFold == NULL_NODE) || (height =
 * inverseSurrogateFold.height + 1 AND id < inverseSurrogateFold.id AND (~(-1 <<
 * surrogateFold.height) & id = ~(~(-1 << surrogateFold.height) &
 * inverseSurrogateFold.id )
 * 
 * @author Thomas, Krista
 */
public class Node {

	/**
	 * the unique identifier for a node
	 */
	private WebId webId;
	/**
	 * a set of nodes neighboring this node each neighbor differs by 1 bit in
	 * the WebId
	 */
	private HashSet<Node> neighbors;
	/**
	 * a set of surrogate neighbors to the node
	 */
	private HashSet<Node> upPointers;
	/**
	 * a set of inverse surrogate neighbors to the node
	 */
	private HashSet<Node> downPointers;
	/**
	 * a node with a WebId that is the exact inverse of its own WebId
	 */
	private Node fold;
	/**
	 * A surrogate fold is found in the same fashion as is a surrogate neighbor:
	 * by flipping the most significant bit of the non-existent fold’s WebID.
	 */
	private Node surrogateFold;
	/**
	 * The inverse of the regular surrogateFold.
	 */
	private Node inverseSurrogateFold;
	/**
	 * the representation of the NULL_NODE uses the null object pattern
	 */
	public static final Node NULL_NODE = new Node();

	/**
	 * The constructor for NULL_NODE.
	 * 
	 * @pre None
	 * @post webId = NULL_WEB_ID AND neighbors = new Neighbors() AND upPointers
	 *       = new UpPointers() AND downPointers = new DownPointers() AND fold =
	 *       this AND surrogateFold = NULL_NODE AND inverseSurrogateFold =
	 *       NULL_NODE
	 */
	private Node() {
		webId = WebId.NULL_WEB_ID;
		neighbors = new HashSet<Node>();
		upPointers = new HashSet<Node>();
		downPointers = new HashSet<Node>();
		fold = this;
		surrogateFold = NULL_NODE;
		inverseSurrogateFold = NULL_NODE;
	}

	/**
	 * The constructor for a node given only the new id.
	 * 
	 * @param id
	 *            - the new id of the webId for this node.
	 * @pre id = 0
	 * @post webId = new WebId(id) AND neighbors = new Neighbors() AND
	 *       upPointers = new UpPointers() AND downPointers = new DownPointers()
	 *       AND fold = this AND surrogateFold = NULL_NODE AND
	 *       inverseSurrogateFold = NULL_NODE
	 */
	public Node(int id) {
		assert id >= 0;
		webId = new WebId(id);
		neighbors = new HashSet<Node>();
		upPointers = new HashSet<Node>();
		downPointers = new HashSet<Node>();
		fold = this;
		surrogateFold = NULL_NODE;
		inverseSurrogateFold = NULL_NODE;
	}

	/**
	 * The constructor for a node given the new id and height.
	 * 
	 * @param id
	 *            - the new id of the webId for this node.
	 * @param height
	 *            - the height of the webId for this node
	 * 
	 * @pre id = 0 AND height = 31 AND height =
	 *      WebId.locationOfMostSignificantOneBit(id) + 1
	 * @post webId = new WebId(id, height) AND neighbors = new Neighbors() AND
	 *       upPointers = new UpPointers() AND downPointers = new DownPointers()
	 *       AND fold = this AND surrogateFold = NULL_NODE AND
	 *       inverseSurrogateFold = NULL_NODE
	 */
	public Node(int id, int height) {
		assert id >= 0
				&& height >= (WebId.locationOfMostSignificantOneBit(id) + 1)
				&& height <= 31;
		webId = new WebId(id, height);
		neighbors = new HashSet<Node>();
		upPointers = new HashSet<Node>();
		downPointers = new HashSet<Node>();
		fold = this;
		surrogateFold = NULL_NODE;
		inverseSurrogateFold = NULL_NODE;
	}

	/**
	 * adds a downPointer to this node
	 * 
	 * @param Node
	 *            downPointer
	 */

	public void addDownPointer(Node downPointer) {
		if (downPointer != null && !downPointers.contains(downPointer)) {
			downPointers.add(downPointer);
			downPointer.addUpPointer(this);
		}
	}

	/**
	 * adds a neighbor to this node
	 * 
	 * @param Node
	 *            neighbor
	 */
	public void addNeighbor(Node neighbor) {
		if (neighbor != null && !neighbors.contains(neighbor)) {
			neighbors.add(neighbor);
			neighbor.addNeighbor(this);
		}
	}

	/**
	 * adds an up pointer to this node
	 * 
	 * @param Node
	 *            upPointer
	 */
	public void addUpPointer(Node upPointer) {
		if (upPointer != null && !upPointers.contains(upPointer)) {
			upPointers.add(upPointer);
			upPointer.addDownPointer(this);
		}
	}

	/**
	 * Creates a simplified node domain to be used in testing
	 * 
	 * @return SimplifiedNodeDomain
	 */
	public SimplifiedNodeDomain constructSimplifiedNodeDomain() {
		SimplifiedNodeDomain temp = new SimplifiedNodeDomain(webId.getValue(),
				webId.getHeight(), getIds(neighbors), getIds(upPointers),
				getIds(downPointers), fold.getWebId().getValue(), surrogateFold
						.getWebId().getValue(), inverseSurrogateFold.getWebId()
						.getValue());
		return temp;
	}

	// dbPhase.spec.Connections getConnections() {}

	/**
	 * returns the set of surrogate neighbors
	 * 
	 * @return HashSet<Node>
	 */
	public HashSet<Node> getDownPointers() {
		return downPointers;
	}

	/**
	 * returns the fold of this node
	 * 
	 * @return Node
	 */
	public Node getFold() {
		return fold;
	}

	/**
	 * returns the height of this node
	 * 
	 * @return int
	 */
	public int getHeight() {
		return webId.getHeight();
	}

	/**
	 * returns a set representing the WebIds present among the set of Nodes
	 * given
	 * 
	 * @param HashSet
	 *            <Node> nodeSet
	 * @return HashSet<Integer>
	 */
	private HashSet<Integer> getIds(HashSet<Node> nodeSet) {
		assert nodeSet != null;
		HashSet<Integer> intSet = new HashSet<Integer>();
		for (Node node : nodeSet) {
			intSet.add(node.getWebId().getValue());
		}
		return intSet;
	}

	/**
	 * returns a set of neighbors
	 * 
	 * @return HashSet<Node>
	 */

	public HashSet<Node> getNeighbors() {
		return neighbors;
	}

	/**
	 * returns the surrogate fold of this node
	 * 
	 * @return Node
	 */
	public Node getSurrogateFold() {
		return surrogateFold;
	}

	/**
	 * returns the inverse surrogate neighbors for this node
	 * 
	 * @return HashSet<Node>
	 */
	public HashSet<Node> getUpPointers() {
		return upPointers;
	}

	/**
	 * returns the WebdId representing this Node
	 * 
	 * @return WebID
	 */

	public WebId getWebId() {
		return webId;
	}

	/**
	 * removes a Node from the set of downPointers if that node exists in the
	 * set
	 * 
	 * @param Node
	 *            downPointer
	 */
	public void removeDownPointer(Node downPointer) {
		assert downPointer != null;
		downPointers.remove(downPointer);

	}

	/**
	 * removes a Node from the set of neighbors if that node exists in the set
	 * 
	 * @param Node
	 *            neighbor
	 */
	public void removeNeighbor(Node neighbor) {
		assert neighbor != null;
		neighbors.remove(neighbor);
	}

	/**
	 * removes a Node from the set of upPointers if that node exists in the set
	 * 
	 * @param Node
	 *            upPointer
	 */
	public void removeUpPointer(Node upPointer) {
		assert upPointer != null;
		upPointers.remove(upPointer);
	}

	// void setConnections(dbPhase.spec.Connections connections) {}
	/**
	 * changes the node's fold to be the Node given
	 * 
	 * @param Node
	 *            newFold
	 */
	public void setFold(Node newFold) {
		fold = (newFold != null) ? newFold : NULL_NODE;
	}

	/**
	 * changes the node's inverse surrogate fold to be the Node given
	 * 
	 * @param Node
	 *            newInverseSurrogateFold
	 */
	public void setInverseSurrogateFold(Node newInverseSurrogateFold) {
		inverseSurrogateFold = (newInverseSurrogateFold != null) ? newInverseSurrogateFold
				: NULL_NODE;
	}

	/**
	 * changes the node's surrogate fold to be the Node given
	 * 
	 * @param Node
	 *            newSurrogateFold
	 */
	public void setSurrogateFold(Node newSurrogateFold) {
		surrogateFold = (newSurrogateFold != null) ? newSurrogateFold
				: NULL_NODE;
	}

	/**
	 * changes the node's webId to be the one given
	 * 
	 * @param WebId
	 *            webId
	 */
	public void setWebId(WebId webId) {
		assert webId != null;
		this.webId = webId;
	}

	/**
	 * two nodes are equal if their WebId's are equal
	 * 
	 * @param Object
	 *            object
	 * @return boolean
	 */

	public boolean equals(Object object) {
		boolean result = (object != null) && (object instanceof Node);
		if (result) {
			Node otherNode = (Node) object;
			result = webId.equals(otherNode.webId);
		}
		return result;
	}
}

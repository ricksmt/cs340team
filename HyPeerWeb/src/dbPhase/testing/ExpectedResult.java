
<!-- saved from url=(0111)http://students.cs.byu.edu/~cs340ta/fall2012/woodfield/projects/phase2/codeAndDocumentation/ExpectedResult.java -->
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></head><body><pre style="word-wrap: break-word; white-space: pre-wrap;">package phase2Specification.testing.hypeerweb;

import java.util.HashSet;
import java.util.Iterator;

import phase2Specification.hypeerweb.SimplifiedNodeDomain;
import phase2Specification.states.DownPointingNodeState;
import phase2Specification.states.HypercubeCapState;
import phase2Specification.states.StandardNodeState;
import phase2Specification.states.TerminalNodeState;
import phase2Specification.states.UpPointingNodeState;



/**
 * Given integer values for the hyPeerWebSize and the webId we can compute integer values
 * representing the height, neighbors, upPointers, downPointers, fold, surrogateFold,
 * inverseSurrogateFold, and state of a node.  The constructor for this class does just that
 * representing the result as a SimplifiedNodeDomain.
 * 
 * &lt;pre&gt;
 * Domain:
 *     Inherited from SimplifiedNodeDomain
 * &lt;/pre&gt;
 * 
 * @author Scott
 */
public class ExpectedResult extends SimplifiedNodeDomain {
    // Domain Implementation
    // Inherited from SimplifiedNodeDomain

    // Constructors
    /**
     * Constructs an SimplifiedNodeDomain representation of the expected webId, neighbors, upPointers,
     * downPointers, fold, surrogateFold, inverseSurrogateFold, and state of a node given integer values
     * for the hyPeerWebSize and webId value.
     * 
     * @param hyPeerWebSize the number of nodes in the hypothetical hyPeerWeb
     * @param webId the value of the webId for a hypothetical node in the hyPeerWeb
     * @pre hyPeerWebSize &amp;ge; 1 AND 0 &amp;le; webId &amp;lt; hyPeerWebSize
     * @post this.webId = webId AND &lt;br&gt;
     * height = the expected height of this hypothetical node AND &lt;br&gt;
     * neighbors = a hash set of integers representing all the neighbors of the hypothetical node AND &lt;br&gt;
     * upPointers = a hash set of integers representing all the upPointers of the hypothetical node AND &lt;br&gt;
     * downPointers = a hash set of integers representing all the downPointers of the hypothetical node AND &lt;br&gt;
     * fold = expected integer value of the webId for the fold AND &lt;br&gt;
     * surrogateFold = expected integer value of the webId for the surrogateFold AND &lt;br&gt;
     * inverseSurrogateFold = expected integer value of the webId of the surrogateFold AND &lt;br&gt;
     * state = expected state's STATE_ID
     */
    public ExpectedResult(int hyPeerWebSize, int webId) {
        super();
        assert hyPeerWebSize &gt;= 1;
        assert webId &gt;= 0 &amp;&amp; webId &lt; hyPeerWebSize;

        cubeHeight = (int) Math.ceil((Math.log(hyPeerWebSize) / log2));
        ExpectedResult.hyPeerWebSize = hyPeerWebSize;
        
        this.webId = webId;
        height = computeExpectedHeight();
        ExpectedResult.lastIdOfEmbeddedHyperCube = ((int) Math.pow(2.0d, Math.floor(Math.log(hyPeerWebSize) / log2))) - 1;
        ExpectedResult.idMask = cubeHeight == 0 ? 1 : -1 &gt;&gt;&gt; (32 - cubeHeight);
        neighbors = expectedNeighbors();
        upPointers = expectedUpPointers();
        downPointers = expectedDownPointers();
        fold = expectedFold();
        surrogateFold = expectedSurrogateFold();
        inverseSurrogateFold = expectedInverseSurrogateFold(webId);
        state = expectedState();
    }

    // Queries

    // Commands

    // Local Information
    /**
     * The number of nodes in the hypothetical HyPeerWeb.
     */
    private static int hyPeerWebSize;
    
    /**
     * The height of the smallest perfect hypercube that would contain the hyperweb of size &lt;i&gt;hyPeerWebSize&lt;/i&gt;.
     */
    private static int cubeHeight;
    
    /**
     * The webId of the cap node of the largest hypercube embedded in the hypothetical HyPeerWeb with &lt;i&gt;hyPeerWebSize&lt;i&gt; nodes.
     */
    private static int lastIdOfEmbeddedHyperCube;
    
    /**
     * An integer where the rightmost &lt;i&gt;cubeHight&lt;/i&gt; bits are ones.  The other bits are zero.  If the &lt;i&gt;cubeHeight&lt;/i&gt; is zero
     * then the idMask is 1.
     */
    private static int idMask;
    
    /**
     * Computes the expected height of this hypothetical node given the HyPeerWeb size and webId.
     * 
     * @pre &lt;i&gt;None&lt;/i&gt;
     * @post If the hyPeerWebSize = cubeHeight then result = cubeHeight
     *       else if the webId is greater than the webId of the cap node of the largest embedded hypercube then result = cubeHeight
     *       else if the webId has a child webId less than the hyPeerWebSize then result = cubeHeight
     *       else result = cubeHeight - 1
     */
    private int computeExpectedHeight(){
        int result = cubeHeight;
        if((hyPeerWebSize - 1) &gt; lastIdOfEmbeddedHyperCube &amp;&amp; webId &lt;= lastIdOfEmbeddedHyperCube &amp;&amp; childOf(webId) == -1){
            result--;
        }
        return result;
    }

    /**
     * Returns a hash set of integers representing all the neighbors of the hypothetical node.
     * 
     * @pre &lt;i&gt;None&lt;/i&gt;
     * @post &amp;forall; 0 &amp;le; i &amp;lt; hyPeerWebSize (i &amp;isin;  result &amp;hArr; i is a neighboring webId of webId)
     */
    private HashSet&lt;Integer&gt; expectedNeighbors() {
        HashSet&lt;Integer&gt; result = new HashSet&lt;Integer&gt;();

        for (int digit = 0; digit &lt; cubeHeight; digit++) {
            int neighbor = flipBit(digit);
            if (exists(neighbor)) {
                result.add(neighbor);
            }
        }
        return result;
    }

    /**
     * Returns a hash set of integers representing all the neighbors of the hypothetical node in the enclosing perfect hypercube.
     * 
     * @pre &lt;i&gt;None&lt;/i&gt;
     * @post &amp;forall; 0 &amp;le; i &amp;lt; cubeSize (i &amp;isin;  result &amp;hArr; i is a neighboring webId of webId)
     */
    private HashSet&lt;Integer&gt; allPossibleNeighbors() {
        HashSet&lt;Integer&gt; result = new HashSet&lt;Integer&gt;();

        for (int digit = 0; digit &lt; cubeHeight; digit++) {
            int neighbor = flipBit(digit);
            result.add(neighbor);
        }
        return result;
    }


    /**
     * Returns a hash set of integers representing all the upPointers of the hypothetical node.
     * 
     * @pre &lt;i&gt;None&lt;/i&gt;
     * @post &amp;forall; largestEmbeddedHypercube.capNode.webId  &amp;lt; i &amp;lt; hyPeerWebSize (i &amp;isin;  result &amp;hArr; parent(i) is a neighboring webId of webId)
     */
    private HashSet&lt;Integer&gt; expectedUpPointers() {
        HashSet&lt;Integer&gt; result = new HashSet&lt;Integer&gt;();
        if (webId &lt;= lastIdOfEmbeddedHyperCube &amp;&amp; childOf(webId) == -1) {
            HashSet&lt;Integer&gt; neighbors = expectedNeighbors();
            for (int neighbor : neighbors) {
                int child = childOf(neighbor);
                if (child &gt; -1 &amp;&amp; child != webId) {
                    result.add(child);
                }
            }
        }
        return result;
    }

    /**
     * Returns a hash set of integers representing all the downPointers of the hypothetical node.
     * 
     * @pre &lt;i&gt;None&lt;/i&gt;
     * @post &amp;forall; hyPeerWebSize &amp;le; i &amp;lt; cubeSize (parent(i) &amp;isin;  result &amp;hArr; i is a neighboring webId of webId)
     */
    private HashSet&lt;Integer&gt; expectedDownPointers() {
        HashSet&lt;Integer&gt; result = new HashSet&lt;Integer&gt;();
        if (webId &gt; lastIdOfEmbeddedHyperCube) {
            HashSet&lt;Integer&gt; neighbors = allPossibleNeighbors();
            for (int neighbor : neighbors) {
                if (!exists(neighbor)) {
                    result.add(parentOf(neighbor));
                }
            }
        }
        return result;
    }

    /**
     * Returns the expected fold of the given webId.
     * 
     * @pre &lt;i&gt;None&lt;/i&gt;
     * @post ~webId &amp; idMask &lt; hyPeerWebSize &amp;rArr; result = ~webId &amp; idMask AND
     *  ~webId &amp; idMask &amp;ge; hyPeerWebSize &amp;rArr; result = -1
     */
    private int expectedFold() {
        int result =  ~webId &amp; idMask;
        if(height == 0) {
            result = 0;
        } else {
            result =  ~webId &amp; idMask;
            if(height == cubeHeight) {
                if(!exists(result)) {
                    result = -1;
                }
            } else { //height &lt; cubeHeight
                if(!exists(result)) {
                    result = ~webId &amp; (idMask &gt;&gt; 1);
                }
            }
        }
        
        return result;
    }
    
    /**
     * Returns whether or not the webId has an existing parent.
     * 
     * @param webId the webId we are going to test.
     * @pre 0 &amp;lt; webId &amp;lt; hyPeerWebSize
     * @post result = &amp;exists 0 &amp;le; i &amp;lt; hyPeerWebSize (i = webId | 1 &lt;&lt; cubeHeight -1)
     */
    private boolean hasParent(int webId) {
        boolean result = exists(webId | (1 &lt;&lt; cubeHeight-1));
        return result;
    }

    /**
     * Computes the expectedSurrogateFold for this webId.
     * 
     * @pre &lt;i&gt;None&lt;/i&gt;
     * @post expectedFold() &gt; -1 &amp;rArr; result = -1 AND&lt;br&gt;
     *       expectedFold() = -1 &amp;rArr; result = ~webId AND ~(-1 &lt;&lt; cubeHeight - 1)
     */
    private int expectedSurrogateFold() {
        int expectedFold = expectedFold();
        int result = -1;
        if(expectedFold == -1) {
            result = ~webId &amp; ~(-1 &lt;&lt; cubeHeight - 1);
        }
        return result;
    }


    /**
     * Computes the expectedInverseSurrogateFold for this webId.
     * 
     * @pre &lt;i&gt;None&lt;/i&gt;
     * @post (height &lt; cubeHeight OR height = 0) AND 0 &amp;le; (~webId &amp; idMask) &amp;lt; hyPeerWebSize AND NOT hasParent(webId) &amp;rArr; result = parentOf(~webId &amp; idMask) &lt;br&gt;
     * ELSE result = -1
     */
    private int expectedInverseSurrogateFold(int webId) {
        int result = -1;
        if(height &lt; cubeHeight || height == 0) {
            int fold = ~webId &amp; idMask;
            if(exists(fold) &amp;&amp; !hasParent(webId)) {
                result = parentOf(fold);
            }
        }
        return result;
    }

    /**
     * Computes the expected State.
     * 
     * @pre &lt;i&gt;None&lt;/i&gt;
     * @post webId = 0 AND height = 0 &amp;rArr; result = HypercubeCapState.STATE_ID AND&lt;br&gt;
     * webId &gt; lastIdOfEmbeddedHyperCube &amp;&amp; everyNeighborIsSmallerThan(webId) &amp;rArr; result = TerminalNodeState.STATE_ID AND&lt;br&gt;
     * |downPointers| &gt; 0 &amp;rArr; result = DownPointingNodeState.STATE_ID AND&lt;br&gt;
     * |upPointers| &gt; 0 &amp;rArr; result = UpPointingNodeState.STATE_ID AND&lt;br&gt;
     * webId = hyPeerWebSize - 1 AND hyPeerWebSize = cubeSize &amp;rArr; result = HypercubeCapState.STATE_ID
     */
    private int expectedState() {
        int result = StandardNodeState.STATE_ID;
        
        if(webId == 0 &amp;&amp; height == 0) {
            result = HypercubeCapState.STATE_ID;
        } else if (webId &gt; lastIdOfEmbeddedHyperCube &amp;&amp; everyNeighborIsSmallerThan(webId)) {
            result = TerminalNodeState.STATE_ID;
        } else if(downPointers.size() &gt; 0) {
            result = DownPointingNodeState.STATE_ID;
        } else if(upPointers.size() &gt; 0) {
            result = UpPointingNodeState.STATE_ID;
        } else if (webId == hyPeerWebSize-1) {
            result = HypercubeCapState.STATE_ID;
        }
        return result;
    }
    
    /**
     * Determines whether all of the neighbors are smaller the the given webId.
     * 
     * @param webId the webId we are comparing against.
     * 
     * @pre &lt;i&gt;None&lt;/i&gt;
     * @post result = &amp;forall; i &amp;isin; neighbors (i &lt; webId)
     */
    private boolean everyNeighborIsSmallerThan(int webId) {
        boolean result = true;
        Iterator&lt;Integer&gt; iterator = neighbors.iterator();
        while(result &amp;&amp; iterator.hasNext()) {
            result = iterator.next() &lt; webId;
        }
        return result;
    }

    /**
     * Flips the bit at location "digit" in the webId
     * 
     * @param digit the digit we are going to flip
     * 
     * @pre 0 &amp;le; digit &amp;lt; 32
     * @post result.binaryRepresentation = ~webId.binaryRepresentation[digit]
     */
    private int flipBit(int digit) {
        int bitMask = 1 &lt;&lt; digit;
        int bit = webId &amp; bitMask;
        int result = 0;
        if (bit == 0) {
            result = webId | bitMask;
        } else {
            result = webId &amp; ~bitMask;
        }
        return result;
    }

    /**
     * Returns the parent id of the provided id.
     * @param id the id we 
     * 
     * @pre &lt;i&gt;None&lt;/i&gt;
     * @post id &amp;le; 0 &amp;rArr; result = -1 AND&lt;br&gt;
     *  id &amp;gt; 0 &amp;rArr; result = id &amp; (1 &lt;&lt; locationOfHighestOneBit(id))
     */
    private int parentOf(int id) {
        int result = -1;
        if (id &gt; 0) {
            int mask = 1 &lt;&lt; locationOfHighestOneBit(id);
            result = id &amp; ~mask;
        }
        return result;
    }

    /**
     * Computes the child of the given id.
     * 
     * @param id the webId we are trying to compute the child id for.
     *
     * @pre id &amp;ge; 0
     * @post (cubeHeight &amp;le; 0) OR ((id | (1 &lt;&lt; (cubeHeight - 1))) = id OR 0 &amp;le; (id | (1 &lt;&lt; (cubeHeight - 1))) &amp;lt; hyPeerWebSize) &amp;rArr; result = -1 ELSE&lt;br&gt;
     * result = id | (cubeHeight -1)
     */
    private int childOf(int id) {
        int result = -1;
        if (cubeHeight &gt; 0) {
            result = id | (1 &lt;&lt; (cubeHeight - 1));
            if (result == id || !exists(result)) {
                result = -1;
            }
        }
        return result;
    }

    /**
     * Returns the location of the most significant one bit in the given id.
     * 
     * @param id the id we are going to examine.
     * 
     * @pre id &amp;ge; 0
     * @post id.binaryRepresentation[result] = 1 AND &amp;forall; result &amp;le; i &amp;lt; 32 (id.binaryRepresentation[i] = 0)
     */
    public int locationOfHighestOneBit(int id) {
        assert id &gt;= 0;

        int result = -1;
        int tempId = id;
        while (tempId &gt; 0) {
            result++;
            tempId &gt;&gt;&gt;= 1;
        }
        return result;
    }

    /**
     * Determines whether the node is a node in the hyPeerWeb.
     * 
     * @param node the node we wish to examine.
     * 
     * @pre &lt;i&gt;None&lt;/i&gt;
     * @post result = 0 &amp;le; node &amp;lt; hyPeerWebSize
     */
    private boolean exists(int node) {
        return node &gt;= 0 &amp;&amp; node &lt; hyPeerWebSize;
    }

    /**
     * The log of 2.
     */
    private double log2 = Math.log(2.0d);
}
</pre><embed id="IDVpluginObj" type="application/x-idvault" width="0" height="0" tabid="142"></body></html>
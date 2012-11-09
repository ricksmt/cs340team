package gui;

import java.util.Set;
import java.util.TreeSet;

import hypeerweb.BroadcastVisitor;
import hypeerweb.Contents;
import hypeerweb.Node;
import hypeerweb.Parameters;

/**
 * Broadcasts a message from the start node (the first node to receive this visitor) to all other nodes in the HyPeerWeb.
 * Every time the message reaches node the indicated node and message are printed in the "TraceInformation" section of
 * the GUI.
 * 
 * <pre>
 * 		<b>Domain:</b> <i>None</i>
 * </pre>
 */
public class Broadcaster extends BroadcastVisitor {
	/**
	 * The default constructor. It does nothing but call the superclass's default constructor.
	 * 
	 * @pre <i>None</i>
	 * @post super.post-condition
	 */
    private boolean startFromZero;
    private static final String MESSAGE_KEY = "message";
    
	public Broadcaster(){
	    startFromZero=true;
	}

	
	/**
	 * Creates the parameters needed to send the Broadcaster visitor to the first Node.
     *
	 * @param message  the message to be broadcast to all node.
	 * 
	 * @pre <i>None</i>
	 * @post result &ne; null AND result.contains(MESSAGE_KEY) AND result.get(MESSAGE_KEY) = message
	 */
	public static Parameters createInitialParameters(String message) {
		//TODO Phase 5 -- replace the next line with one or more lines implementing the initialization of the parameters.
	    Parameters params = new Parameters();
	    params.set(MESSAGE_KEY, message);
		return params;
	}
	
	@Override
	/**
	 * Prints a string in the TracePanel of the GUI.  The string should contain the message and the
	 * labeled webId of the current node.
	 * 
	 * @pre node &ne; null AND node &isin; HyPeerWeb AND parameters &ne; null AND parameters.contains(MESSAGE_KEY)
	 * @post A string with the message and current node's id should be printed on the tracePanel of the GUI.<br>
	 * Required format: "Broadcasting '" parameters.get(MESSAGE_ID) "' to node " node.getWebId() ".\n"
	 */
	protected void operation(Node node, Parameters parameters) {
		//TODO Phase 5 -- implement this method so that it satisfies the post condition.
	    
	    if (node.getWebId() == 0) startFromZero = false;
	    
        if (startFromZero) broadcastFromZero(node, parameters);
        else
        {
            gui.Main.GUI.getSingleton(null).printToTracePanel(
                    "Broadcasting '" + parameters.get(MESSAGE_KEY) + "' to node " + node.getWebId() + ".\n");
            Contents contents = node.getContents();
            
            if(parameters.containsKey(MESSAGE_KEY)){
                contents.set(MESSAGE_KEY, parameters.get(MESSAGE_KEY));
            }
            
               
            for(Node nodeToBroadcast: getNeighborsToBroadcast(node)) nodeToBroadcast.accept(this, parameters);
        }
	    
	}
	
	private Set<Node> getNeighborsToBroadcast(Node node)
    {
        int nodeId = node.getWebId();
        int height = node.getHeight();
        Set<Node> neighborsToBroadcast = new TreeSet<Node>();
        Set<Node> neighbors = node.getConnections().getNeighbors();
        
        double limit = Math.pow(2, height);
        
        for(int bit=1; bit<limit; bit*=2){
            int idToBroadcast = nodeId | bit;
            
            if(idToBroadcast == nodeId) break; //reached the first '1'
            else{
                for(Node neighbor: neighbors){
                    if(neighbor.getWebId()==idToBroadcast){
                        neighborsToBroadcast.add(neighbor);
                        break;
                    }
                }
            }
        }
        
        return neighborsToBroadcast;
        
        
    }
    
    /**
     * broadcastFromZero
     * @param node
     * @param parameters
     */
    private void broadcastFromZero(Node node, Parameters parameters)
    {
        Node cap = node.findCapNode(node);
        Node zero = cap.getFold();
        if(zero.getWebId()!=0) zero = zero.getLowestNeighbor();
        
        zero.accept(this, parameters);
    }
	
	
	/**
	 * The message parameter identifier to be used to add messages to the parameter list.
	 */
	


}

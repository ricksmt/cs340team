/**
 * 
 */
package black;

import java.util.Set;
import java.util.TreeSet;

import hypeerweb.BroadcastVisitor;
import hypeerweb.Contents;
import hypeerweb.Node;
import hypeerweb.Parameters;

/**
 * @author ricksmt
 *
 */
public class BroadcastTestVisitor extends BroadcastVisitor {
    
    /** The flag that tells whether the broadcast should be started from node zero
     *  or continued from the current node*/
    private boolean startFromZero;
    
    public BroadcastTestVisitor()
    { 
        startFromZero = true;
    }
    
    /**
     * 
     * 
     * @return
     */
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
    
    @Override
    protected void operation(Node node, Parameters parameters){
        if (node.getWebId() == 0) startFromZero = false;
    
        if (startFromZero) broadcastFromZero(node, parameters);
        else
        {
            Contents contents = node.getContents();
            Set<String> keySet = parameters.getKeys();
            
            for(String key : keySet){
                if (contents.containsKey(key)) System.err.println("Node " + node.getWebId() + " has already been visited ");
                contents.set(key, parameters.get(key));
            }
    
            for(Node nodeToBroadcast: getNeighborsToBroadcast(node)) nodeToBroadcast.accept(this, parameters);
        }
    }
}

/**
 * 
 */
package black;

import java.util.Set;

import hypeerweb.Contents;
import hypeerweb.Node;
import hypeerweb.Parameters;
import hypeerweb.SendVisitor;
import hypeerweb.SimplifiedNodeDomain;

/**
 * @author ricksmt
 *
 */
public class SendTestVisitor extends SendVisitor {
    
    public SendTestVisitor()
    {
        TARGET_KEY = "target";
    }
    
    @Override
    protected void intermediateOperation(final Node node, final Parameters parameters)
    {
        assert node != null && parameters != null && node != Node.NULL_NODE;
        
        Contents contents = node.getContents();
        for(String key : parameters.getKeys()) contents.set(key, parameters.get(key));

        Node nextIntermediateNode = getIntermediateNode(node, (Integer) parameters.get(TARGET_KEY));
        nextIntermediateNode.accept(this, parameters);
        
    }

    /**
     * The abstract operation to be performed on the targetNode.
     * Must be overridden in any concrete subclass.
     * 
     * @param node The target node this operation is to be performed on.
     * @param parameters The list of parameters to be passed to the target operation.
     * @pre node is not null and parameters is not null.
     * @post True
     */
    @Override
    protected void targetOperation(Node node, Parameters parameters)
    {
        Contents contents = node.getContents();
        Set<String> keySet = parameters.getKeys();
        
        for(String key : keySet){
            if (contents.containsKey(key)) System.err.println("Node " + node.getWebId() + " has already been visited ");
            contents.set(key, parameters.get(key));
        }
        contents.set(null, TARGET_KEY);
    }

    private Node getIntermediateNode(Node node, int targetId) {
        Set<Node> nodeSet = node.getConnections().getNeighbors();
        int distance = SimplifiedNodeDomain.distanceTo(node.getWebId(), targetId);
        Node closestNode = Node.NULL_NODE;
        int closestDistance = distance;
        
        for(Node neighbor : nodeSet){
            int curDistance = SimplifiedNodeDomain.distanceTo(neighbor.getWebId(), targetId);
            if(curDistance < closestDistance){
                closestNode = neighbor;
                closestDistance = curDistance;
                break;
            }
        }        
        nodeSet = node.getConnections().getSurrogateNeighbors();
        for(Node surNeighbor : nodeSet){
            int curDistance = SimplifiedNodeDomain.distanceTo(surNeighbor.getWebId(), targetId);
            
            if(curDistance < closestDistance){
                closestNode = surNeighbor;
                closestDistance = curDistance;
            }
        }        
        nodeSet = node.getConnections().getInverseSurrogateNeighbors();
        for(Node invSurNeighbor : nodeSet){
            int curDistance = SimplifiedNodeDomain.distanceTo(invSurNeighbor.getWebId(), targetId);
            
            if(curDistance < closestDistance){
                closestNode = invSurNeighbor;
                closestDistance = curDistance;
            }
        }        
        Node fold = node.getConnections().getFold();
        if(fold != Node.NULL_NODE){
            int curDistance = SimplifiedNodeDomain.distanceTo(fold.getWebId(), targetId);
            if(curDistance < closestDistance){
                closestNode = fold;
                closestDistance = curDistance;
            }
        }        
        Node surFold = node.getConnections().getSurrogateFold();
        if(surFold != Node.NULL_NODE){
            int curDistance = SimplifiedNodeDomain.distanceTo(surFold.getWebId(), targetId);
            if(curDistance < closestDistance){
                closestNode = surFold;
                closestDistance = curDistance;
            }
        }        
        Node invSurFold = node.getConnections().getInverseSurrogateFold();
        if(invSurFold != Node.NULL_NODE){
            int curDistance = SimplifiedNodeDomain.distanceTo(invSurFold.getWebId(), targetId);
            if(curDistance < closestDistance){
                closestNode = invSurFold;
                closestDistance = curDistance;
            }
        }        
        return closestNode;        
    }
}

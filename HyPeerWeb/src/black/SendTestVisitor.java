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
            contents.set(key, parameters.get(key));
        }
        contents.set(null, TARGET_KEY);
    }

    
}
